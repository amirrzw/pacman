package view.game;

import controller.MapController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Player;
import model.User;
import view.EntranceMenuView;

import java.net.URL;

public class ChooseMapMenuView extends Application {
    public static Stage stage;
    public static char[][] randomMap = null;
    public int standingMapCounter = 1;
    public GridPane gridPane;
    public Button backButton;
    public Button generateRandomButton;
    public Button nextMapButton;
    public Button selectButton;
    public Button saveMapButton;
    public Label errorLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/game/chooseMapMenu.fxml");
        Pane pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        Image image = new Image(getClass().getResource("/images/cursor1.png").toExternalForm());
        scene.setCursor(new ImageCursor(image));
        primaryStage.setScene(scene);
    }

    @FXML
    public void initialize() {
        addButtonToList();
        EntranceMenuView.setCursorImageOnButtons();
        loadMap(MapController.standingMaps.get(0));
    }

    private void addButtonToList() {
        EntranceMenuView.buttons.add(selectButton);
        EntranceMenuView.buttons.add(generateRandomButton);
        EntranceMenuView.buttons.add(nextMapButton);
        EntranceMenuView.buttons.add(saveMapButton);
        EntranceMenuView.buttons.add(backButton);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        new SetHealthMenuView().start(stage);
    }

    public void generateRandomMapClicked(MouseEvent mouseEvent) {
        randomMap = MapController.getInstance().generateMap();
        loadMap(randomMap);
    }

    public void nextMapClicked(MouseEvent mouseEvent) {
        int numberOfMaps;
        if (Player.getCurrentPlayer().getUser() != null) numberOfMaps = 3 + Player.getCurrentPlayer().getUser().getSavedMap().size();
        else numberOfMaps = 3;
        if (standingMapCounter < numberOfMaps) {
            if (standingMapCounter < 3) {
                loadMap(MapController.standingMaps.get(standingMapCounter));
            } else {
                loadMap(Player.getCurrentPlayer().getUser().getSavedMap().get(standingMapCounter - 3));
            }
            standingMapCounter++;
        } else {
            errorLabel.setText("there is no other map");
        }
    }

    public void selectClicked(MouseEvent mouseEvent) throws Exception {
        int gameCounter = 0;
        for (User user : User.getAllUsers()) {
            if (user.getNumberOfGameScore() > gameCounter) gameCounter = user.getNumberOfGameScore();
        }
        GameController.gameCounter = gameCounter + 1;
        GameController.coins.clear();
        new GameController().start(stage);
    }

    public void saveMapClicked(MouseEvent mouseEvent) {
        if (randomMap == null) {
            errorLabel.setText("please generate random map first");
        } else if (Player.getCurrentPlayer().getUser() == null) {
            errorLabel.setText("please login first");
        } else {
            Player.getCurrentPlayer().getUser().getSavedMap().add(randomMap);
            errorLabel.setText("map saved");
        }
    }

    private void loadMap(char[][] map) {
        Player.getCurrentPlayer().setMap(map);
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                Pane pane = new Pane();
                pane.setPrefWidth(30);
                pane.setPrefHeight(30);
                if (map[i][j] == '1') {
                    pane.setStyle("-fx-background-color: black");
                } else {
                    pane.setStyle("-fx-background-color: #ababa2");
                }
                gridPane.add(pane, j, i);
            }
        }
    }
}

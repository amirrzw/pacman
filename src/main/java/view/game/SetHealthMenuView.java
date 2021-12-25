package view.game;

import controller.GameController;
import controller.MapController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Player;
import view.EntranceMenuView;
import view.MainMenuView;

import java.net.URL;

public class SetHealthMenuView extends Application {
    public static Stage stage;
    public Button nextButton;
    public Button backButton;
    public Label healthLabel;
    public Button increaseButton;
    public Button decreaseButton;
    public Label healthInfoLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/game/setHealthMenu.fxml");
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
    }

    public void addButtonToList() {
        EntranceMenuView.buttons.add(nextButton);
        EntranceMenuView.buttons.add(backButton);
        EntranceMenuView.buttons.add(increaseButton);
        EntranceMenuView.buttons.add(decreaseButton);
    }

    public void increaseHealthClicked(MouseEvent mouseEvent) {
        int health = Integer.parseInt(healthLabel.getText());
        if (health == 2) {
            healthLabel.setText("3");
            healthInfoLabel.setText("");
        }
        if (health == 3) {
            healthLabel.setText("4");
        }
        if (health == 4) {
            healthLabel.setText("5");
            healthInfoLabel.setText("(Maximum health)");
        }
    }

    public void decreaseHealthClicked(MouseEvent mouseEvent) {
        int health = Integer.parseInt(healthLabel.getText());
        if (health == 5) {
            healthLabel.setText("4");
            healthInfoLabel.setText("");
        }
        if (health == 4) {
            healthLabel.setText("3");
        }
        if (health == 3) {
            healthLabel.setText("2");
            healthInfoLabel.setText("(Minimum health)");
        }
    }

    public void nextClicked(MouseEvent mouseEvent) throws Exception {
        GameController.getInstance().setHealth(Integer.parseInt(healthLabel.getText()));
        MapController.getStandingMapsFromJson();
        new ChooseMapMenuView().start(stage);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        Player.setCurrentPlayer(null);
        new MainMenuView().start(stage);
    }
}

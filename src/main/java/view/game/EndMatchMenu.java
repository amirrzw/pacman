package view.game;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Player;
import view.EntranceMenuView;
import view.MainMenuView;

import java.net.URL;

public class EndMatchMenu extends Application {
    public static MediaPlayer musicPlayer;
    public static Stage stage;
    public Label scoreLabel;
    public Button exitButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/game/endMatchMenu.fxml");
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
        scoreLabel.setText(String.valueOf(Player.getCurrentPlayer().getScore()));
    }

    private void addButtonToList() {
        EntranceMenuView.buttons.add(exitButton);
    }

    public void exitClicked(MouseEvent mouseEvent) throws Exception {
        new MainMenuView().start(stage);
    }
}

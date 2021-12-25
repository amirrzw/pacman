package view;

import controller.GameController;
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
import model.User;
import view.account.AccountMenuView;
import view.game.SetHealthMenuView;

import java.net.URL;

public class MainMenuView extends Application {
    public static Stage stage;
    public Button newGameButton;
    public Button accountButton;
    public Button scoreBoardButton;
    public Button exitButton;
    public Label errorLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/mainmenu/mainMenu.fxml");
        Pane pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        Image image = new Image(getClass().getResource("/images/cursor1.png").toExternalForm());
        scene.setCursor(new ImageCursor(image));
        primaryStage.setScene(scene);
    }

    @FXML
    public void initialize() {
        addButtonsToList();
        EntranceMenuView.setCursorImageOnButtons();
    }

    public void addButtonsToList() {
        EntranceMenuView.buttons.add(newGameButton);
        EntranceMenuView.buttons.add(scoreBoardButton);
        EntranceMenuView.buttons.add(accountButton);
        EntranceMenuView.buttons.add(exitButton);
    }

    public void scoreBoardClicked(MouseEvent mouseEvent) throws Exception {
        new ScoreBoardView().start(stage);
    }

    public void accountClicked(MouseEvent mouseEvent) throws Exception {
        if (User.getCurrentUser() == null) {
            errorLabel.setText("please login first");
        } else {
            errorLabel.setText("");
            new AccountMenuView().start(stage);
        }
    }

    public void exitClicked(MouseEvent mouseEvent) throws Exception {
        new EntranceMenuView().start(stage);
    }

    public void newGameClicked(MouseEvent mouseEvent) throws Exception {
        GameController.getInstance().newGame();
        new SetHealthMenuView().start(stage);
    }
}

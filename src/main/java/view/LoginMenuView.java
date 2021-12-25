package view;

import controller.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class LoginMenuView extends Application {
    public static Stage stage;

    public TextField userName;
    public TextField passWord;
    public Label errorLabel;
    public Button backButton;
    public Button loginButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/login/loginMenu.fxml");
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
        EntranceMenuView.buttons.add(loginButton);
        EntranceMenuView.buttons.add(backButton);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        new EntranceMenuView().start(stage);
    }

    public void loginClicked(MouseEvent mouseEvent) throws Exception {
        if (userName.getText().equals("")) {
            errorLabel.setText("username field is empty");
            return;
        }
        if (passWord.getText().equals("")) {
            errorLabel.setText("password field is empty");
            return;
        }
        String result = LoginMenuController.getInstance().login(userName.getText(), passWord.getText());
        errorLabel.setText(result);
        if (result.equals("login successfully")) {
            new MainMenuView().start(stage);
        }
    }
}

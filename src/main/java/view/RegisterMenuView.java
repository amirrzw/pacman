package view;

import controller.AccountActionsController;
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

import java.net.URL;
import java.util.ArrayList;

public class RegisterMenuView extends Application {
    public static Stage stage;
    public Label errorLabel;
    public TextField userName;
    public TextField passWord;
    public Button backButton;
    public Button registerButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/register/registerMenu.fxml");
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
        EntranceMenuView.buttons.add(registerButton);
        EntranceMenuView.buttons.add(backButton);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        new EntranceMenuView().start(stage);
    }

    public void registerClicked(MouseEvent mouseEvent) {
        if (userName.getText().equals("")) {
            errorLabel.setText("username field is empty");
            return;
        }
        if (passWord.getText().equals("")) {
            errorLabel.setText("password field is empty");
            return;
        }
        String result = AccountActionsController.getInstance().register(userName.getText(), passWord.getText());
        errorLabel.setText(result);
    }
}

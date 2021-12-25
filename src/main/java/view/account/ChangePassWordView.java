package view.account;

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
import view.EntranceMenuView;

import java.net.URL;

public class ChangePassWordView extends Application {
    public static Stage stage;
    public Label error;
    public TextField passText;
    public Button backButton;
    public Button changeButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/accountMenu/changePass.fxml");
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
        EntranceMenuView.buttons.add(changeButton);
        EntranceMenuView.buttons.add(backButton);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        new AccountMenuView().start(stage);
    }

    public void changePassClicked(MouseEvent mouseEvent) {
        if (passText.getText().equals("")) {
            error.setText("new password field is empty");
            return;
        }
        String result = AccountActionsController.getInstance().changePass(passText.getText());
        error.setText(result);
    }
}

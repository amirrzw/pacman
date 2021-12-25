package view.account;

import controller.AccountActionsController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import view.EntranceMenuView;
import view.account.AccountMenuView;

import java.net.URL;

public class DeletePopUpView extends Application {

    public static Stage stage;
    public Button yesButton;
    public Button noButton;

    @Override
    public void start(Stage popUpStage) throws Exception {
        stage = popUpStage;
        stage.setTitle("Delete Account");
        URL fxmlAddress = getClass().getResource("/accountMenu/deletePopUp.fxml");
        Pane pane = FXMLLoader.load(fxmlAddress);
        Scene popUpScene = new Scene(pane);
        Image image = new Image(getClass().getResource("/images/cursor1.png").toExternalForm());
        popUpScene.setCursor(new ImageCursor(image));
        popUpStage.setScene(popUpScene);
        popUpStage.showAndWait();
    }

    @FXML
    public void initialize() {
        addButtonsToList();
        EntranceMenuView.setCursorImageOnButtons();
    }

    public void addButtonsToList() {
        EntranceMenuView.buttons.add(yesButton);
        EntranceMenuView.buttons.add(noButton);
    }

    public void deleteAccount(MouseEvent mouseEvent) throws Exception {
        AccountActionsController.getInstance().deleteAccount();
        stage.close();
        new EntranceMenuView().start(AccountMenuView.stage);
    }

    public void cancelDeleteAccount(MouseEvent mouseEvent) {
        stage.close();
    }
}

package view.account;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.EntranceMenuView;
import view.MainMenuView;

import java.net.URL;

public class AccountMenuView extends Application {

    public static Stage stage;
    public Button changePassButton;
    public Button deleteAccountButton;
    public Button backButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/accountMenu/accountMenu.fxml");
        Pane pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        Image image = new Image(getClass().getResource("/images/cursor1.png").toExternalForm());
        scene.setCursor(new ImageCursor(image));
        stage.setScene(scene);
    }

    @FXML
    public void initialize() {
        addButtonsToList();
        EntranceMenuView.setCursorImageOnButtons();
    }

    public void addButtonsToList() {
        EntranceMenuView.buttons.add(changePassButton);
        EntranceMenuView.buttons.add(deleteAccountButton);
        EntranceMenuView.buttons.add(backButton);
    }

    public void deleteAccountClicked(MouseEvent mouseEvent) throws Exception {
        new DeletePopUpView().start(new Stage());
    }

    public void changePassWord(MouseEvent mouseEvent) throws Exception {
        new ChangePassWordView().start(stage);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        new MainMenuView().start(stage);
    }
}

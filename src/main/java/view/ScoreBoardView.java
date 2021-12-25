package view;

import controller.ScoreBoardController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.Collation;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreBoardView extends Application {

    public static Stage stage;

    public Label label1;
    public Label label2;
    public Label label3;
    public Label label4;
    public Label label5;
    public Label label6;
    public Label label7;
    public Label label8;
    public Label label9;
    public Label label10;

    public ArrayList<Label> labels = new ArrayList<>();
    public Button backButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL fxmlAddress = getClass().getResource("/scoreBoardMenu/scoreBoardMenu.fxml");
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
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.add(label5);
        labels.add(label6);
        labels.add(label7);
        labels.add(label8);
        labels.add(label9);
        labels.add(label10);
        ArrayList<String> scoreBoard = ScoreBoardController.getInstance().showScoreBoard();
        for (int i = 0; i < scoreBoard.size(); i++) {
            if (i == 10) break;
            labels.get(i).setText(scoreBoard.get(i));
        }
    }

    public void addButtonsToList() {
        EntranceMenuView.buttons.add(backButton);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        new MainMenuView().start(stage);
    }
}

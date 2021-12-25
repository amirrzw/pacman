package view;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EntranceMenuView extends Application {
    public static Stage stage;
    public static MediaPlayer musicPlayer;
    public static int playMusicCount = 0;
    public static ArrayList<Button> buttons = new ArrayList<>();
    public Button registerButton;
    public Button loginButton;
    public Button exitButton;
    public Button playAsGuestButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Pacman");
        URL fxmlAddress = getClass().getResource("/entrance/entrance.fxml");
        Pane pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        Image image = new Image(getClass().getResource("/images/cursor1.png").toExternalForm());
        scene.setCursor(new ImageCursor(image));
        primaryStage.setScene(scene);
        primaryStage.show();
        if (playMusicCount == 0) {
           // playMusic();
            playMusicCount++;
        }
        stage.setResizable(false);
    }

    @FXML
    public void initialize() {
        addButtonsToList();
        setCursorImageOnButtons();
    }

    public static void setCursorImageOnButtons() {
        for (Button button : buttons) {
            Image image = new Image(EntranceMenuView.class.getResource("/images/cursor2.png").toExternalForm());
            button.setCursor(new ImageCursor(image));
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setAlignment(Pos.BASELINE_LEFT);
                }
            });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setAlignment(Pos.BASELINE_CENTER);
                }
            });
        }
    }

    public void addButtonsToList() {
        buttons.add(registerButton);
        buttons.add(loginButton);
        buttons.add(exitButton);
        buttons.add(playAsGuestButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void exitClicked(MouseEvent mouseEvent) {
        updateJson();
        System.exit(0);
    }

    public void loginClicked(MouseEvent mouseEvent) throws Exception {
        new LoginMenuView().start(stage);
    }

    public void registerClicked(MouseEvent mouseEvent) throws Exception {
        new RegisterMenuView().start(stage);
    }

    private void updateJson() {
        try {
            FileWriter writer = new FileWriter("data.json");
            writer.write(new YaGson().toJson(User.getAllUsers()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("data.json")));
            ArrayList<User> users = new YaGson().fromJson(json,
                    new TypeToken<List<User>>() {
                    }.getType()
            );
            for (User user : users) {
                User.getAllUsers().add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMusic() {
        Media music = new Media(getClass().getResource("/music/music.mp3").toExternalForm());
        musicPlayer = new MediaPlayer(music);
        musicPlayer.setAutoPlay(true);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }

    public void playAsGuestClicked(MouseEvent mouseEvent) throws Exception {
        User.setCurrentUser(null);
        new MainMenuView().start(stage);
    }
}

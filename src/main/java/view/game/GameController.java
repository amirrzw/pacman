package view.game;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Direction;
import model.Ghost;
import model.Player;
import view.EntranceMenuView;
import view.MainMenuView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class GameController extends Application {
    public static Stage stage;
    public static int gameCounter;
    public static ImageView pacmanImageView;
    public static ArrayList<Circle> coins = new ArrayList<>();
    private static boolean isPaused = false;
    private static boolean isPacmanAndGhostIntersect = false;
    public static boolean isPacmanVulnerable = true;
    public static MediaPlayer deathMusicPlayer;
    public GridPane gridPane;
    public double pacmanX;
    public double pacmanY;
    public int indexX = 10;
    public int indexY = 10;
    public Ghost redGhost;
    public Ghost yellowGhost;
    public Ghost pinkGhost;
    public Ghost blueGhost;
    public Label scoreLabel;
    public Label healthLabel;
    public Button pauseButton;
    public Button exitButton;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Image image1 = new Image(getClass().getResource("/images/pacman.gif").toExternalForm());
        pacmanImageView = new ImageView(image1);
        pacmanImageView.setFitHeight(28);
        pacmanImageView.setFitWidth(28);
        URL fxmlAddress = getClass().getResource("/game/game.fxml");
        Pane pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(keyEvent -> pressKey(keyEvent));
        Image image = new Image(getClass().getResource("/images/cursor1.png").toExternalForm());
        scene.setCursor(new ImageCursor(image));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        addButtonToList();
        EntranceMenuView.setCursorImageOnButtons();
        loadMap();
        animationTimer.start();
        moveGhosts.setCycleCount(Timeline.INDEFINITE);
        moveGhosts.play();
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void addButtonToList() {
        EntranceMenuView.buttons.add(pauseButton);
        EntranceMenuView.buttons.add(exitButton);
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            checkPacmanAndCoinsIntersect();
            reloadMap();
            checkPacmanAndGhostIntersect();
            try {
                checkEndGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
            healthLabel.setText(String.valueOf(Player.getCurrentPlayer().getHealth()));
            scoreLabel.setText(String.valueOf(Player.getCurrentPlayer().getScore()));
        }
    };

    private void checkPacmanAndCoinsIntersect() {
        Circle removableCircle = null;
        for (Circle circle : coins) {
            if (circle.getBoundsInParent().intersects(pacmanImageView.getBoundsInParent())) {
                gridPane.getChildren().remove(circle);
                removableCircle = circle;
                Player.getCurrentPlayer().increaseScore(5);
            }
        }
        if (removableCircle != null) {
            coins.remove(removableCircle);
        }
    }

    private void checkEndGame() throws Exception {
        if (Player.getCurrentPlayer().getHealth() == 0) {
            animationTimer.stop();
            timeline.stop();
            moveGhosts.stop();
            new EndMatchMenu().start(stage);
            if (Player.getCurrentPlayer().getUser() != null) {
                if (Player.getCurrentPlayer().getScore() > Player.getCurrentPlayer().getUser().getHighScore()) {
                    Player.getCurrentPlayer().getUser().setHighScore(Player.getCurrentPlayer().getScore());
                    Player.getCurrentPlayer().getUser().setNumberOfGameScore(gameCounter);
                }
            }
            gameCounter++;
        }
    }

    private void checkPacmanAndGhostIntersect() {
        if (!isPaused) {
            if (redGhost.getImageView().getBoundsInParent().intersects(pacmanImageView.getBoundsInParent())
                    || blueGhost.getImageView().getBoundsInParent().intersects(pacmanImageView.getBoundsInParent())
                    || yellowGhost.getImageView().getBoundsInParent().intersects(pacmanImageView.getBoundsInParent())
                    || pinkGhost.getImageView().getBoundsInParent().intersects(pacmanImageView.getBoundsInParent())) {
                if (isPacmanVulnerable) {
                    Media music = new Media(GameController.class.getResource("/music/death.mp3").toExternalForm());
                    deathMusicPlayer = new MediaPlayer(music);
                    isPaused = true;
                    isPacmanAndGhostIntersect = true;
                    deathMusicPlayer.play();
                    timeline.stop();
                    isPacmanVulnerable = false;
                    Player.getCurrentPlayer().decreaseHealth(1);
                }
            }
        }
    }

    private void reloadMap() {
        if (coins.size() == 0) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Image coinImage = new Image(getClass().getResource("/images/coin.jpg").toExternalForm(), false);
            for (int i = 0; i < 21; i++) {
                for (int j = 0; j < 21; j++) {
                    addCoinToMap(i, j, coinImage);
                }
            }
            reloadRedGhost();
            reloadBlueGhost();
            reloadYellowGhost();
            reloadPinkGhost();
        }
    }

    private void reloadRedGhost() {
        gridPane.getChildren().remove(redGhost.getImageView());
        ImageView ghostImageView = new ImageView();
        ghostImageView.setFitHeight(28);
        ghostImageView.setFitWidth(28);
        Image redGhostImage = new Image(getClass().getResource("/images/redGhost.gif").toExternalForm());
        ghostImageView.setImage(redGhostImage);
        redGhost = new Ghost(ghostImageView, redGhost.getBaseXCoordinate(), redGhost.getBaseYCoordinate(), 1, 1);
        gridPane.add(redGhost.getImageView(), 1, 1);
    }

    private void reloadBlueGhost() {
        gridPane.getChildren().remove(blueGhost.getImageView());
        ImageView ghostImageView = new ImageView();
        ghostImageView.setFitHeight(28);
        ghostImageView.setFitWidth(28);
        Image blueGhostImage = new Image(getClass().getResource("/images/blueGhost.gif").toExternalForm());
        ghostImageView.setImage(blueGhostImage);
        blueGhost = new Ghost(ghostImageView, blueGhost.getBaseXCoordinate(), blueGhost.getBaseYCoordinate(), 19, 1);
        gridPane.add(blueGhost.getImageView(), 19, 1);
    }

    private void reloadYellowGhost() {
        gridPane.getChildren().remove(yellowGhost.getImageView());
        ImageView ghostImageView = new ImageView();
        ghostImageView.setFitHeight(28);
        ghostImageView.setFitWidth(28);
        Image yellowGhostImage = new Image(getClass().getResource("/images/yellowGhost.gif").toExternalForm());
        ghostImageView.setImage(yellowGhostImage);
        yellowGhost = new Ghost(ghostImageView, yellowGhost.getBaseXCoordinate(), yellowGhost.getBaseYCoordinate(), 1, 19);
        gridPane.add(yellowGhost.getImageView(), 1, 19);
    }

    private void reloadPinkGhost() {
        gridPane.getChildren().remove(pinkGhost.getImageView());
        ImageView ghostImageView = new ImageView();
        ghostImageView.setFitHeight(28);
        ghostImageView.setFitWidth(28);
        Image pinkGhostImage = new Image(getClass().getResource("/images/pinkGhost.gif").toExternalForm());
        ghostImageView.setImage(pinkGhostImage);
        pinkGhost = new Ghost(ghostImageView, pinkGhost.getBaseXCoordinate(), pinkGhost.getBaseYCoordinate(), 19, 19);
        gridPane.add(pinkGhost.getImageView(), 19, 19);
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
        isPacmanVulnerable = true;
        System.out.println();
    })
    );
    Timeline moveGhosts = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
        if (!isPaused) {
            checkMoveGhosts(redGhost);
            checkMoveGhosts(pinkGhost);
            checkMoveGhosts(blueGhost);
            checkMoveGhosts(yellowGhost);
        }
    })
    );

    private void loadMap() {
        Image coinImage = new Image(getClass().getResource("/images/coin.jpg").toExternalForm(), false);
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                Pane pane = new Pane();
                pane.setPrefWidth(30);
                pane.setPrefHeight(30);
                if (Player.getCurrentPlayer().getMap()[i][j] == '1') {
                    pane.setStyle("-fx-background-color: #901b9f");
                } else {
                    pane.setStyle("-fx-background-color: #1f1e1e");
                }
                gridPane.add(pane, j, i);
                createGhost(i, j, pane);
                addCoinToMap(i, j, coinImage);
                if (i == 10 && j == 10) {
                    pacmanX = pane.getLayoutX();
                    pacmanY = pane.getLayoutY();
                }
            }
        }
        gridPane.add(pacmanImageView, 10, 10);
        addGhostToMap();
    }

    private void addCoinToMap(int iIndex, int jIndex, Image coinImage) {
        if (Player.getCurrentPlayer().getMap()[iIndex][jIndex] != '1') {
            ImagePattern imagePattern = new ImagePattern(coinImage);
            Circle circle = new Circle();
            circle.setRadius(7);
            circle.setStroke(Color.SEAGREEN);
            circle.setFill(imagePattern);
            gridPane.add(circle, jIndex, iIndex);
            circle.setTranslateX(circle.getLayoutX() + 5);
            coins.add(circle);
        }
    }

    private void createGhost(int iIndex, int jIndex, Pane pane) {
        ImageView ghostImageView = new ImageView();
        ghostImageView.setFitHeight(28);
        ghostImageView.setFitWidth(28);
        if (iIndex == 1 && jIndex == 1) {
            Image redGhostImage = new Image(getClass().getResource("/images/redGhost.gif").toExternalForm());
            ghostImageView.setImage(redGhostImage);
            redGhost = new Ghost(ghostImageView, pane.getLayoutX(), pane.getLayoutY(), 1, 1);
        }
        if (iIndex == 1 && jIndex == 19) {
            Image blueGhostImage = new Image(getClass().getResource("/images/blueGhost.gif").toExternalForm());
            ghostImageView.setImage(blueGhostImage);
            blueGhost = new Ghost(ghostImageView, pane.getLayoutX(), pane.getLayoutY(), 19, 1);
        }
        if (iIndex == 19 && jIndex == 1) {
            Image yellowGhostImage = new Image(getClass().getResource("/images/yellowGhost.gif").toExternalForm());
            ghostImageView.setImage(yellowGhostImage);
            yellowGhost = new Ghost(ghostImageView, pane.getLayoutX(), pane.getLayoutY(), 1, 19);
        }
        if (iIndex == 19 && jIndex == 19) {
            Image pinkGhostImage = new Image(getClass().getResource("/images/pinkGhost.gif").toExternalForm());
            ghostImageView.setImage(pinkGhostImage);
            pinkGhost = new Ghost(ghostImageView, pane.getLayoutX(), pane.getLayoutY(), 19, 19);
        }
    }

    private void addGhostToMap() {
        gridPane.add(redGhost.getImageView(), 1, 1);
        gridPane.add(blueGhost.getImageView(), 19, 1);
        gridPane.add(yellowGhost.getImageView(), 1, 19);
        gridPane.add(pinkGhost.getImageView(), 19, 19);
    }

    private void pressKey(KeyEvent keyEvent) {
        if (isPacmanAndGhostIntersect) {
            isPaused = false;
            isPacmanAndGhostIntersect = false;
            timeline.play();
            return;
        }
        if (isPaused) return;
        TranslateTransition translate = new TranslateTransition();
        switch (keyEvent.getCode()) {
            case RIGHT: {
                if (canMove(indexX + 1, indexY)) {
                    movePacman(Direction.RIGHT);
                    pacmanImageView.setRotate(360);
                    indexX += 1;
                    pacmanX += 30;
                }
            }
            break;
            case DOWN: {
                if (canMove(indexX, indexY + 1)) {
                    movePacman(Direction.DOWN);
                    pacmanImageView.setRotate(90);
                    indexY += 1;
                    pacmanY += 30;
                }
            }
            break;
            case LEFT: {
                if (canMove(indexX - 1, indexY)) {
                    movePacman(Direction.LEFT);
                    pacmanImageView.setRotate(180);
                    indexX -= 1;
                    pacmanX -= 30;
                }
            }
            break;
            case UP: {
                if (canMove(indexX, indexY - 1)) {
                    movePacman(Direction.UP);
                    pacmanImageView.setRotate(270);
                    indexY -= 1;
                    pacmanY -= 30;
                }
            }
        }
    }

    private boolean canMove(int x, int y) {
        return x <= 19 && x >= 1 && y <= 19 && y >= 1 && Player.getCurrentPlayer().getMap()[y][x] != '1';
    }

    private void movePacman(Direction direction) {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(200));
        translate.setNode(pacmanImageView);
        switch (direction) {
            case RIGHT: {
                translate.setToX(pacmanX + 30);
                translate.play();
            }
            break;
            case DOWN: {
                translate.setToY(pacmanY + 30);
                translate.play();
            }
            break;
            case LEFT: {
                translate.setToX(pacmanX - 30);
                translate.play();
            }
            break;
            case UP: {
                translate.setToY(pacmanY - 30);
                translate.play();
            }
        }
    }

    static void shuffle(int[] random) {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            int randomIndex = rand.nextInt(4);
            int temp = random[i];
            random[i] = random[randomIndex];
            random[randomIndex] = temp;
        }
    }

    private void checkMoveGhosts(Ghost ghost) {
        int[] direction = new int[]{1, 2, 3, 4};
        shuffle(direction);
        for (int i = 0; i < 4; i++) {
            switch (direction[i]) {
                case 1: {
                    if (canMove(ghost.getxIndex() + 1, ghost.getyIndex())) {
                        moveGhosts(ghost, Direction.RIGHT);
                        ghost.setxCoordinate(ghost.getxCoordinate() + 30);
                        ghost.setxIndex(ghost.getxIndex() + 1);
                        return;
                    }
                }
                case 2: {
                    if (canMove(ghost.getxIndex(), ghost.getyIndex() + 1)) {
                        moveGhosts(ghost, Direction.DOWN);
                        ghost.setyCoordinate(ghost.getyCoordinate() + 30);
                        ghost.setyIndex(ghost.getyIndex() + 1);
                        return;
                    }
                }
                case 3: {
                    if (canMove(ghost.getxIndex() - 1, ghost.getyIndex())) {
                        moveGhosts(ghost, Direction.LEFT);
                        ghost.setxCoordinate(ghost.getxCoordinate() - 30);
                        ghost.setxIndex(ghost.getxIndex() - 1);
                        return;
                    }
                }
                case 4: {
                    if (canMove(ghost.getxIndex(), ghost.getyIndex() - 1)) {
                        moveGhosts(ghost, Direction.UP);
                        ghost.setyCoordinate(ghost.getyCoordinate() - 30);
                        ghost.setyIndex(ghost.getyIndex() - 1);
                        return;
                    }
                }
            }
        }
    }

    private void moveGhosts(Ghost ghost, Direction direction) {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(200));
        translate.setNode(ghost.getImageView());
        switch (direction) {
            case RIGHT: {
                translate.setToX(ghost.getxCoordinate() + 30);
                translate.play();
            }
            break;
            case DOWN: {
                translate.setToY(ghost.getyCoordinate() + 30);
                translate.play();
            }
            break;
            case LEFT: {
                translate.setToX(ghost.getxCoordinate() - 30);
                translate.play();
            }
            break;
            case UP: {
                translate.setToY(ghost.getyCoordinate() - 30);
                translate.play();
            }
        }
    }

    public void pauseClicked(MouseEvent mouseEvent) {
        if (isPaused) {
            isPaused = false;
        } else {
            isPaused = true;
        }
    }

    public void exitClicked(MouseEvent mouseEvent) throws Exception {
        timeline.stop();
        animationTimer.stop();
        moveGhosts.stop();
        new MainMenuView().start(stage);
    }
}

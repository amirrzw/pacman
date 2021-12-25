package controller;

import model.Player;
import model.User;

public class GameController {
    private static GameController gameController;

    private GameController() {
    }

    public static GameController getInstance() {
        if (gameController == null)
            gameController = new GameController();
        return gameController;
    }

    public void newGame() {
        new Player(User.getCurrentUser());
    }

    public void setHealth(int health) {
        Player.getCurrentPlayer().setHealth(health);
    }
}

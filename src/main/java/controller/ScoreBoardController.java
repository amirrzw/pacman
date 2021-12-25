package controller;

import model.User;
import view.ScoreBoardView;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardController {

    private static ScoreBoardController scoreBoardController;

    private ScoreBoardController() {
    }

    public static ScoreBoardController getInstance() {
        if (scoreBoardController == null)
            scoreBoardController = new ScoreBoardController();
        return scoreBoardController;
    }

    public ArrayList<String> showScoreBoard() {
        ArrayList<String> scoreBoard = new ArrayList<>();
        ArrayList<User> users = User.getAllUsers();
        Comparator<User> comparator = Comparator.comparing(User::getHighScore, Comparator.reverseOrder())
                .thenComparing(User::getNumberOfGameScore);
        users.sort(comparator);
        int rank = 1;
        for (int i = 0; i < users.size(); i++) {
            if (i == 0) {
                scoreBoard.add("1- " + users.get(0).getUserName() + " : " + users.get(0).getHighScore());
                continue;
            }
            if (users.get(i).getHighScore() == users.get(i - 1).getHighScore()) {
                scoreBoard.add(rank + "- " + users.get(i).getUserName() + " : " + users.get(i).getHighScore());
                continue;
            }
            rank = i + 1;
            scoreBoard.add(rank + "- " + users.get(i).getUserName() + " : " + users.get(i).getHighScore());
        }
        return scoreBoard;
    }
}

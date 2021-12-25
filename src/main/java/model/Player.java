package model;

public class Player {
    private User user;
    private int score = 0;
    private int health;
    private char[][] map;
    private static Player currentPlayer;

    public Player(User user) {
        this.user = user;
        currentPlayer = this;
    }

    public User getUser() {
        return user;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getScore() {
        return score;
    }

    public char[][] getMap() {
        return map;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
    }

    public int getHealth() {
        return health;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public void setHealth(int health) {
        this.health = health;
    }

   public void decreaseHealth(int health) {
        this.health -= health;
   }
}

package model;

import java.util.ArrayList;

public class User {
    private static User currentUser;
    private static ArrayList<User> allUsers;

    private String userName;
    private String passWord;
    private int highScore;
    private int numberOfGameScore;
    private ArrayList<char[][]> savedMap;

    static {
        allUsers = new ArrayList<>();
    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        allUsers.add(this);
        savedMap = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public int getHighScore() {
        return highScore;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<char[][]> getSavedMap() {
        return savedMap;
    }

    public int getNumberOfGameScore() {
        return numberOfGameScore;
    }

    public void setNumberOfGameScore(int numberOfGameScore) {
        this.numberOfGameScore = numberOfGameScore;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static User getUserByUserName(String userName) {
        for (User user : allUsers) {
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }
}

package controller;

import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountActionsController {

    private static AccountActionsController accountActionsController;
    static Pattern userNamePattern = Pattern.compile("^\\w+$");
    static Pattern passWordPattern = Pattern.compile("^\\S+$");

    private AccountActionsController() {
    }

    public static AccountActionsController getInstance() {
        if (accountActionsController == null)
            accountActionsController = new AccountActionsController();
        return accountActionsController;
    }

    public String register(String userName, String passWord) {
        User user = User.getUserByUserName(userName);
        if (user != null) {
            return "user with username " + userName + " already exists";
        }
        Matcher matcher = userNamePattern.matcher(userName);
        if (!matcher.find()) {
            return "username format is invalid";
        }
        matcher = passWordPattern.matcher(passWord);
        if (!matcher.find()) {
            return "password format is invalid";
        }
        if (passWord.length() < 4) {
            return "password must be at least 4 characters";
        }
        new User(userName, passWord);
        return "register is successfully";
    }

    public String changePass(String newPass) {
        Matcher matcher = passWordPattern.matcher(newPass);
        if (!matcher.find()) {
            return "password format is invalid";
        }
        if (newPass.length() < 4) {
            return "password must be at least 4 characters";
        }
        User.getCurrentUser().setPassWord(newPass);
        return "password changed successfully";
    }

    public void deleteAccount() {
        User.getAllUsers().remove(User.getCurrentUser());
        User.setCurrentUser(null);
    }
}

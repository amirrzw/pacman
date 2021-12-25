package controller;

import model.User;

public class LoginMenuController {

    private static LoginMenuController loginMenuController;

    private LoginMenuController() {
    }

    public static LoginMenuController getInstance() {
        if (loginMenuController == null)
            loginMenuController = new LoginMenuController();
        return loginMenuController;
    }

    public String login(String userName, String passWord) {
        User user = User.getUserByUserName(userName);
        if (user == null) {
            return "no user exists with this username";
        }
        if (!user.getPassWord().equals(passWord)) {
            return "incorrect password";
        }
        User.setCurrentUser(user);
        return "login successfully";
    }
}

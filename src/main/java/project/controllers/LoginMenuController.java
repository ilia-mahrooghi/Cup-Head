package project.controllers;

import project.models.User;
import project.models.UserDatabase;

public class LoginMenuController {
    UserDatabase userDatabase;

    public LoginMenuController(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public boolean isValidInput(String input) {
        if (input == null || input.equals(""))
            return false;
        if (input.length() > 15)
            return false;
        if (!input.matches("\\w+"))
            return false;
        return true;
    }

    public boolean isStrongPassword(String password) {
        if (password == null)
            return false;
        if (password.length() < 8)
            return false;
        if (!password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*")
                || !password.matches(".*\\d.*"))
            return false;
        return true;
    }

    public Output register(String username, String password, String nickname) {
        if (userDatabase.getUserByUsername(username) != null) return Output.REPEATED_USERNAME;
        User user = new User(username, password, nickname);
        userDatabase.getUsers().add(user);
        return Output.REGISTERED;
    }

    public Output login(String username, String password) {
        User user = userDatabase.getUserByUsername(username);
        if (user == null) return Output.INVALID_USERNAME_OR_PASSWORD;
        if (!user.getPassword().equals(password)) return Output.INVALID_USERNAME_OR_PASSWORD;
        return null;
    }

}

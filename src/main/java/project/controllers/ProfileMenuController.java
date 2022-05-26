package project.controllers;

import project.models.User;
import project.models.UserDatabase;

public class ProfileMenuController {
    private User user;
    private UserDatabase userDatabase;

    public ProfileMenuController(User user, UserDatabase userDatabase) {
        this.user = user;
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

    public User getUserByUsernameWithoutAUser(String username) {
        for (User user1 : userDatabase.getUsers())
            if (user != user1 && user1.getUsername().equals(username)) return user1;
        return null;
    }


    public Output changeUser(String username, String nickname, String password) {
        if (getUserByUsernameWithoutAUser(username) != null) return Output.REPEATED_USERNAME;
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(password);
        return Output.CHANGE_DATA;
    }


}

package project.models;

import java.util.ArrayList;

public class UserDatabase {
    private ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getUserByUsername(String username) {
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public User getUserByNickname(String nickname) {
        for (User user : users)
            if (user.getNickname().equals(nickname))
                return user;
        return null;
    }

    public ArrayList<User> getSortedUsers() {
        ArrayList<User> sortedUsers = new ArrayList<>(this.users);
        for (int i = 0; i < sortedUsers.size(); i++)
            for (int j = i + 1; j < sortedUsers.size(); j++) {
                boolean toChange = false;
                if (sortedUsers.get(i).getHighScore().getScore() < sortedUsers.get(j).getHighScore().getScore())
                    toChange = true;
                else if (sortedUsers.get(i).getHighScore().getScore() == sortedUsers.get(j).getHighScore().getScore() &&
                        sortedUsers.get(i).getHighScore().getTime() < sortedUsers.get(j).getHighScore().getTime())
                    toChange = true;
                if (toChange) {
                    User tmp = sortedUsers.get(i);
                    sortedUsers.set(i, sortedUsers.get(j));
                    sortedUsers.set(j, tmp);
                }

            }
        return sortedUsers;
    }
}
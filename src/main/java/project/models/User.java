package project.models;

import java.net.URL;
import java.util.Random;

public class User {
    private String username;
    private String password;
    private String nickname;
    private HighScore highScore = new HighScore(0, -1800000);
    private URL url;


    public User(String username, String password, String nickname) {
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        Random random = new Random();
        setUrl(Avatar.values()[Math.abs(random.nextInt() % 4)].getUrl());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public HighScore getHighScore() {
        return highScore;
    }

    public void setHighScore(HighScore highScore) {
        this.highScore = highScore;
    }
}
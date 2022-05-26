package project.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import project.App;
import project.models.Music;
import project.models.User;
import project.models.UserDatabase;
import project.view.Game.GameMenu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MainMenu {
    private static User user;
    private static UserDatabase userDatabase;

    @FXML
    private ImageView muteUnmuteImage;
    @FXML
    private Button profileMenu;
    @FXML
    private BorderPane content;

    public static void setUp(User user, UserDatabase userDatabase) {
        MainMenu.userDatabase = userDatabase;
        MainMenu.user = user;
    }

    public void initialize() throws MalformedURLException {
        if(MainMenu.user == null) profileMenu.setDisable(true);
        setInitializeMuteUnmute();
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> setBackgroundPositions(content, xPosition.get()));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(80), new KeyValue(xPosition, -15000)));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void setInitializeMuteUnmute() throws MalformedURLException {
        if (Music.getInstance().getMenuMusic().isMute()) {
            muteUnmuteImage.setImage(new Image(String.valueOf(
                    new URL(Objects.requireNonNull(App.class.getResource("/project/png/sound/mute.png")).toString()))));
        } else {
            muteUnmuteImage.setImage(new Image(String.valueOf(
                    new URL(Objects.requireNonNull(App.class.getResource("/project/png/sound/unmute.png")).toString()))));
        }
    }

    void setBackgroundPositions(BorderPane borderPane, double xPosition) {
        String style = "-fx-background-position: " +
                "left " + xPosition / 6 + "px bottom," +
                "left " + xPosition / 5 + "px bottom," +
                "left " + xPosition / 4 + "px bottom," +
                "left " + xPosition / 3 + "px bottom," +
                "left " + xPosition / 2 + "px bottom," +
                "left " + xPosition + "px bottom;";
        borderPane.setStyle(style);
    }

    public void profileMenu(MouseEvent mouseEvent) throws IOException {
        ProfileMenu.setUp(user, userDatabase);
        App.changeFxml("/project/fxml/ProfileMenu.fxml", "Profile Menu");
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        App.changeFxml("/project/fxml/loginMenu.fxml", "Login Menu");
    }

    public void scoreBoard(MouseEvent mouseEvent) throws IOException {
        ScoreBoard.setUp(user, userDatabase);
        App.changeFxml("/project/fxml/ScoreBoard.fxml", "Score Board");
    }

    public void muteUnmute(MouseEvent mouseEvent) throws MalformedURLException {
        if (Music.getInstance().getMenuMusic().isMute()) {
            Music.getInstance().getMenuMusic().setMute(false);
            muteUnmuteImage.setImage(new Image(String.valueOf(
                    new URL(Objects.requireNonNull(App.class.getResource("/project/png/sound/unmute.png")).toString()))));
        } else {
            Music.getInstance().getMenuMusic().setMute(true);
            muteUnmuteImage.setImage(new Image(String.valueOf(
                    new URL(Objects.requireNonNull(App.class.getResource("/project/png/sound/mute.png")).toString()))));
        }
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        SettingsMenu.setUp(MainMenu.user);
        App.changeFxml("/project/fxml/Settings.fxml", "Settings");
    }
}
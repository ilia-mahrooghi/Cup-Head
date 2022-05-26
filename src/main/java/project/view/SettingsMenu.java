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
import project.models.Game;
import project.models.Music;
import project.models.User;
import project.view.Game.GameMenu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class SettingsMenu {
    private static User user;

    public static void setUp(User user) {
        SettingsMenu.user = user;
    }


    private boolean hasGameSound = true;
    private int difficulty = 1;
    private boolean isDevilMode = false;

    @FXML
    public Button blackAndWhite;
    private boolean isBlackAndWhite = false;
    @FXML
    private BorderPane content;
    @FXML
    private ImageView muteUnmuteImage;
    @FXML
    private ImageView gameSound;
    @FXML
    private Button hardLevel;
    @FXML
    private Button mediumLevel;
    @FXML
    private Button easyLevel;
    @FXML
    private Button devilMode;

    public void initialize() throws MalformedURLException {
        blackAndWhite.setStyle("-fx-background-color: red");
        devilMode.setStyle("-fx-background-color: red;");
        mediumLevel.setStyle("-fx-background-color: #07b507");
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

    public void changeGameSound(MouseEvent mouseEvent) throws MalformedURLException {
        if (hasGameSound) {
            hasGameSound = false;
            this.gameSound.setImage(new Image(String.valueOf(
                    new URL(Objects.requireNonNull(App.class.getResource("/project/png/sound/mute.png")).toString()))));
        } else {
            hasGameSound = true;
            this.gameSound.setImage(new Image(String.valueOf(
                    new URL(Objects.requireNonNull(App.class.getResource("/project/png/sound/unmute.png")).toString()))));
        }
    }

    public void hardLevel(MouseEvent mouseEvent) {
        hardLevel.setStyle("-fx-background-color: #07b507");
        mediumLevel.setStyle("");
        easyLevel.setStyle("");
        difficulty = 2;
    }

    public void MediumLevel(MouseEvent mouseEvent) {
        mediumLevel.setStyle("-fx-background-color: #07b507");
        hardLevel.setStyle("");
        easyLevel.setStyle("");
        difficulty = 1;
    }

    public void easyLevel(MouseEvent mouseEvent) {
        easyLevel.setStyle("-fx-background-color: #07b507");
        mediumLevel.setStyle("");
        hardLevel.setStyle("");
        difficulty = 0;

    }

    public void devilMode(MouseEvent mouseEvent) {
        if (isDevilMode) {
            isDevilMode = false;
            devilMode.setStyle("-fx-background-color: red");
            easyLevel.setDisable(false);
            mediumLevel.setDisable(false);
            hardLevel.setDisable(false);
        } else {
            isDevilMode = true;
            devilMode.setStyle("-fx-background-color: #07b507");
            easyLevel.setDisable(true);
            mediumLevel.setDisable(true);
            hardLevel.setDisable(true);
        }
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        Music.getInstance().getMenuMusic().pause();
        if (hasGameSound) {
            Music.getInstance().getGameMusic().play();
            Music.getInstance().getGameMusic().setCycleCount(-1);
        }
        GameMenu.setUp(SettingsMenu.user, new Game(this.difficulty, this.isDevilMode));
        GameMenu.startGame(isBlackAndWhite);
    }

    public void blackAndWhite(MouseEvent mouseEvent) {
        if (isBlackAndWhite) {
            isBlackAndWhite = false;
            blackAndWhite.setStyle("-fx-background-color: red");
        } else {
            isBlackAndWhite = true;
            blackAndWhite.setStyle("-fx-background-color: #07b507");
        }
    }
}
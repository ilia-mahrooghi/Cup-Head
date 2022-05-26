package project.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;
import project.App;
import project.models.Music;
import project.models.User;
import project.models.UserDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoard {
    private static UserDatabase userDatabase = new UserDatabase();
    private static User user;

    public static void setUp(User user, UserDatabase userDatabase) {
        ScoreBoard.user = user;
        ScoreBoard.userDatabase = userDatabase;
    }

    @FXML
    private ImageView muteUnmuteImage;
    @FXML
    private ScrollPane scoreBoardPane;
    @FXML
    private BorderPane content;

    public void initialize() throws MalformedURLException {
        setInitializeMuteUnmute();
        setScoreBoard(userDatabase.getSortedUsers());
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

    private void setScoreBoard(ArrayList<User> users) {
        HBox mainHBox = new HBox();
        VBox nicknameVBox = new VBox();
        nicknameVBox.setPrefWidth(469);
        nicknameVBox.setSpacing(10);
        VBox pointVBox = new VBox();
        pointVBox.setPrefWidth(462);
        pointVBox.setSpacing(10);
        mainHBox.getChildren().addAll(nicknameVBox, pointVBox);
        addTitleOfScoreBoard(mainHBox, nicknameVBox, pointVBox);
        addNamesAndPoints(mainHBox, nicknameVBox, pointVBox, users);
        scoreBoardPane.setContent(mainHBox);
    }

    private void addTitleOfScoreBoard(HBox mainHBox, VBox nicknameVBox, VBox pointVBox) {
        Label nickname = new Label("nickname");
        Label point = new Label("points");
        //font and size
        nickname.setFont(Font.font("century", 48));
        point.setFont(Font.font("century", 48));
        //nickname
        nickname.setPrefWidth(470);
        nickname.setPrefHeight(69);
        //point
        point.setPrefWidth(462);
        point.setPrefHeight(69);
        //test alignment
        nickname.setAlignment(Pos.CENTER);
        point.setAlignment(Pos.CENTER);
        //css
        nickname.setStyle("-fx-background-color: #d000ff;" +
                "-fx-border-radius: 30 0 0 0;" +
                "-fx-background-radius: 30 0 0 0;");
        point.setStyle("-fx-background-color: #d057ec;" +
                "-fx-border-radius: 0 30 0 0;" +
                "-fx-background-radius: 0 30 0 0;");
        //add to Vbox
        nicknameVBox.getChildren().add(nickname);
        pointVBox.getChildren().add(point);
    }

    private void addNamesAndPoints(HBox mainHBox, VBox nicknameVBox, VBox pointVBox, ArrayList<User> users) {
        DateFormat timeFormat = new SimpleDateFormat("mm:ss");
        int counter = 1;
        for (User user1 : users) {
            Label nickname = new Label(user1.getNickname());
            Label point = new Label("Score: " + user1.getHighScore().getScore());
            Label time = new Label("Time: " + timeFormat.format(user1.getHighScore().getTime()));
            //font and size
            nickname.setFont(Font.font("century", 48));
            point.setFont(Font.font("century", 48));
            time.setFont(Font.font("century", 48));
            //nickname
            nickname.setPrefWidth(470);
            nickname.setPrefHeight(140);
            //point
            point.setPrefWidth(470);
            point.setPrefHeight(70);
            time.setPrefWidth(470);
            time.setPrefHeight(70);
            //test alignment
            nickname.setAlignment(Pos.CENTER);
            point.setAlignment(Pos.CENTER);
            time.setAlignment(Pos.CENTER);
            //playerCss
            if (user1 == user) {
                nickname.setStyle("-fx-border-radius: 30 0 0 30;" +
                        "-fx-background-radius: 30 0 0 30;");
                point.setStyle("-fx-border-radius: 0 30 30 0;" +
                        "-fx-background-radius: 0 30 30 0;");
                time.setStyle("-fx-border-radius: 0 30 30 0;" +
                        "-fx-background-radius: 0 30 30 0;");
                nickname.setTextFill(Paint.valueOf("blue"));
                point.setTextFill(Paint.valueOf("blue"));
                time.setTextFill(Paint.valueOf("blue"));
            }
            addGoldSilverBronze(counter, nickname, point, time);
            //add to Vbox
            nicknameVBox.getChildren().add(nickname);
            pointVBox.getChildren().addAll(new VBox(point, time));
            counter++;
        }
    }

    private void addGoldSilverBronze(int counter, Label nickname, Label point, Label time) {
        if (counter == 1) {
            nickname.setStyle("-fx-background-color: gold");
            point.setStyle("-fx-background-color: gold");
            time.setStyle("-fx-background-color: gold");
        } else if (counter == 2) {
            nickname.setStyle("-fx-background-color: silver");
            point.setStyle("-fx-background-color: silver");
            time.setStyle("-fx-background-color: silver");
        } else if (counter == 3) {
            nickname.setStyle("-fx-background-color: #d3830c");
            point.setStyle("-fx-background-color: #d3830c");
            time.setStyle("-fx-background-color: #d3830c");
        }
    }

    private void setBackgroundPositions(BorderPane borderPane, double xPosition) {
        String style = "-fx-background-position: " +
                "left " + xPosition / 6 + "px bottom," +
                "left " + xPosition / 5 + "px bottom," +
                "left " + xPosition / 4 + "px bottom," +
                "left " + xPosition / 3 + "px bottom," +
                "left " + xPosition / 2 + "px bottom," +
                "left " + xPosition + "px bottom;";
        borderPane.setStyle(style);
    }

    public void exit(MouseEvent mouseEvent) throws IOException {
        MainMenu.setUp(user, userDatabase);
        App.changeFxml("/project/fxml/MainMenu.fxml", "Main Menu");
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
}
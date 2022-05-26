package project.view.Game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import project.App;
import project.controllers.GameMenuController;
import project.models.Components.Airplane;
import project.models.Components.Boss;
import project.models.Game;
import project.models.Music;
import project.models.User;
import project.view.PopupMessage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMenu {
    private static Pane mainPane;
    private static User user;
    private static GameMenuController gameMenuController;
    private static Game game;
    private static AtomicInteger moveAirplaneCounter;
    private static Timeline timeline;
    private static Pane root;
    private static boolean isBlackAndWhite;

    public static void setUp(User user, Game game) {
        GameMenu.game = game;
        GameMenu.user = user;
        GameMenu.gameMenuController
                = new GameMenuController(user, game);
    }

    @FXML
    public ProgressBar rocketTimeLeft;
    @FXML
    private Button blackAndWhite;
    @FXML
    private Pane endGamePane;
    @FXML
    private Label endScore;
    @FXML
    private Label endTime;
    @FXML
    private Label victoryOrLoose;
    @FXML
    private VBox endGameVBox;
    @FXML
    private ImageView airplaneShotMode;
    private boolean isShotMode;
    @FXML
    private ProgressBar enemyProgramsBar;
    @FXML
    private Label enemyHealthLabel;
    @FXML
    private Label airplaneHp;
    @FXML
    private BorderPane content;
    @FXML
    private Pane bottomBar;
    @FXML
    private Label clock;
    @FXML
    public Label score;
    private static boolean isEndGame = false;
    private boolean isLastMenuShowed = false;

    private long startGameTime;

    public static void startGame(boolean isBlackAndWhite) throws IOException {
        GameMenu.isBlackAndWhite = isBlackAndWhite;
        setIsEndGame(false);
        moveAirplaneCounter = new AtomicInteger(30);
        mainPane = null;
        URL address = new URL(App.class.getResource("/project/fxml/Game.fxml").toString());
        root = FXMLLoader.load(address);
        App.getStage().setTitle("Game");
        PopupMessage.setRoot(root);
        Pane mainPain = new Pane();
        Airplane airplane = gameMenuController.createAirplane();
        Boss boss = createBoss();
        root.getChildren().add(mainPain);
        mainPain.getChildren().add(airplane);
        mainPain.getChildren().add(boss);
        GameMenu.mainPane = mainPain;
        App.setRoot(root);
        airplane.requestFocus();
    }

    public static Pane getMainPane() {
        return mainPane;
    }

    public static boolean getIsEndGame() {
        return GameMenu.isEndGame;
    }

    public static void setIsEndGame(boolean isEndGame) {
        GameMenu.isEndGame = isEndGame;
    }

    public void initialize() throws MalformedURLException {
        endGameVBox.setVisible(false);
        gameMenuController.setAirplaneMovement();
        startGameTime = System.currentTimeMillis();
        setBackGround();
        updateEverything();
    }

    private void updateEverything() {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(1),
                        event -> {
                            try {
                                if (isBlackAndWhite) blackAndWhiteRoot();
                                if (!isEndGame) {
                                    gameMenuController.isAirplaneDead();
                                    gameMenuController.killMiniBoss();
                                    gameMenuController.decreaseLeftTimes();
                                    gameMenuController.bossShot();
                                    gameMenuController.handlingPhase1BossDeath();
                                    gameMenuController.giveCommandToAirplane(moveAirplaneCounter);
                                    gameMenuController.miniBossHandling();
                                    gameMenuController.handlingPhase2BossDeath();
                                    showProgressLeftForRocket();
                                    setAirplaneShotModeImage();
                                    showAirplaneHp();
                                    showBossHp();
                                    showTIme();
                                    showScore();
                                } else {
                                    endGame();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                )
        );
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void showScore() throws MalformedURLException {
        score.setText("Score: " + Airplane.getInstance().getScore());
    }

    private void showTIme() {
        DateFormat timeFormat = new SimpleDateFormat("mm:ss");
        long difference = System.currentTimeMillis() - startGameTime;
        difference -= 1800000;
        clock.setText(timeFormat.format(difference));
    }

    private void showProgressLeftForRocket() throws MalformedURLException {
        this.rocketTimeLeft.setProgress((double) Airplane.getInstance().getTimeLeftToAllowRocketShot()
                / (double) Airplane.getInstance().getTotalTimeForRocketShot());
    }

    private void setAirplaneShotModeImage() throws MalformedURLException {
        if (isShotMode != Airplane.getInstance().isShootingBullet()) {
            isShotMode = !isShotMode;
            if (Airplane.getInstance().isShootingBullet())
                this.airplaneShotMode.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                        App.class.getResource("/project/png/frames/AirplaneShotMode/bullet.png")).toString()))));
            else
                this.airplaneShotMode.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull
                        (App.class.getResource("/project/png/frames/AirplaneShotMode/bomb.png")).toString()))));
        }
    }

    private void showAirplaneHp() throws MalformedURLException {
        airplaneHp.setText("HP: " + Airplane.getInstance().getHealth());
    }

    private void showBossHp() throws MalformedURLException {
        if (Boss.getInstance().getHealth() >= 0)
            enemyHealthLabel.setText("Boss Health: " + Boss.getInstance().getHealth());
        enemyProgramsBar.setProgress(
                (double) Boss.getInstance().getHealth() / (double) Boss.getInstance().getTotalHealth());
    }


    private static Boss createBoss() throws MalformedURLException {
        return Boss.getInstance();
    }

    private void setBackGround() {
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> setBackgroundPositions(this.content, xPosition.get()));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(80), new KeyValue(xPosition, -15000)));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void setBackgroundPositions(BorderPane borderPane, double xPosition) {
        String style = "-fx-background-position: " +
                "left " + xPosition / 6 + "px bottom," +
                "left " + xPosition / 5 + "px bottom," +
                "left " + xPosition / 4 + "px bottom," +
                "left " + xPosition / 3 + "px bottom," +
                "left " + xPosition / 2 + "px bottom," +
                "left " + xPosition + "px bottom;";
        borderPane.setStyle(style);
    }

    private void endGame() throws MalformedURLException {
        if (!isLastMenuShowed) {
            long gameTime = System.currentTimeMillis() - startGameTime - 1800000;
            gameMenuController.changeHighScore(user, Airplane.getInstance().getScore(), gameTime);
            endScore.setText(score.getText());
            endTime.setText("Time: " + clock.getText());
            isLastMenuShowed = true;
            addVictoryOrDefeatLabel();
            mainPane.getChildren().add(endGamePane);
            endGameVBox.setVisible(true);
        }
    }

    private void addVictoryOrDefeatLabel() throws MalformedURLException {
        String winOrLooseString = gameMenuController.getWinOrLooseString();
        victoryOrLoose.setText(winOrLooseString);
    }

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        Music.getInstance().getGameMusic().pause();
        Music.getInstance().getMenuMusic().play();
        gameMenuController.resetEveryThing();
        timeline.pause();
        App.changeFxml("/project/fxml/MainMenu.fxml", "Main Menu");
    }

    public void restart(MouseEvent mouseEvent) throws IOException {
        gameMenuController.resetEveryThing();
        timeline.pause();
        setUp(user, new Game(game.getDifficulty(), game.isDevilMode()));
        GameMenu.startGame(GameMenu.isBlackAndWhite);
    }

    public void blackAndWhiteRoot() {
        isBlackAndWhite = true;
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(0.1);
        colorAdjust.setHue(-0.05);
        colorAdjust.setBrightness(0.1);
        colorAdjust.setSaturation(-1);
        root.setEffect(colorAdjust);
    }
}
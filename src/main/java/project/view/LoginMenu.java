package project.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import project.App;
import project.controllers.LoginMenuController;
import project.controllers.Output;
import project.models.Music;
import project.models.User;
import project.models.UserDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;


public class LoginMenu {
    private static UserDatabase userDatabase;
    private static LoginMenuController loginMenuController;

    public static void setUp(UserDatabase userDatabase) {
        LoginMenu.userDatabase = userDatabase;
        loginMenuController = new LoginMenuController(userDatabase);
    }

    @FXML
    private Button loginAsAGuest;
    @FXML
    private ImageView muteUnmuteImage;
    @FXML
    private BorderPane content;
    @FXML
    private TextField registerUsername;
    @FXML
    private TextField registerNickname;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;

    public void login(MouseEvent mouseEvent) throws Exception {
        Music.getInstance().getButtonClick().play();
        Output output = loginMenuController.login(loginUsername.getText(), loginPassword.getText());
        if (output != null) new PopupMessage(Alert.AlertType.ERROR, output.toString());
        else {
            User user = userDatabase.getUserByUsername(loginUsername.getText());
            MainMenu.setUp(user, userDatabase);
            App.changeFxml("/project/fxml/MainMenu.fxml", "Main Menu");
        }
    }

    public void register(MouseEvent mouseEvent) {
        Music.getInstance().getButtonClick().play();
        Output output = loginMenuController.register(registerUsername.getText(), registerPassword.getText(), registerNickname.getText());
        if (output == Output.REPEATED_USERNAME) new PopupMessage(Alert.AlertType.ERROR, output.toString());
        else new PopupMessage(Alert.AlertType.INFORMATION, output.toString());
    }

    private void registerDataHandling() {
        String passwordText = registerPassword.getText();
        String confirmPasswordText = confirmPassword.getText();
        boolean[] validInput = {false, false, false, false};
        if (!loginMenuController.isValidInput(passwordText) ||
                !loginMenuController.isStrongPassword(passwordText)) {
            registerPassword.setStyle("-fx-border-color: Red;");
        } else {
            validInput[0] = true;
            registerPassword.setStyle("-fx-border-color: #32e532");
        }
        if (!passwordText.equals(confirmPasswordText)) {
            confirmPassword.setStyle("-fx-border-color: Red;");
        } else {
            validInput[1] = true;
            confirmPassword.setStyle("-fx-border-color: #32e532");
        }
        if (!loginMenuController.isValidInput(registerUsername.getText())) {
            registerUsername.setStyle("-fx-border-color: Red;");
        } else {
            validInput[2] = true;
            registerUsername.setStyle("-fx-border-color: #32e532");
        }
        if (!loginMenuController.isValidInput(registerNickname.getText())) {
            registerNickname.setStyle("-fx-border-color: Red;");
        } else {
            validInput[3] = true;
            registerNickname.setStyle("-fx-border-color: #32e532");
        }
        registerButton.setDisable(!validInput[0] || !validInput[1] || !validInput[2] || !validInput[3]);
    }

    public void registerPassword(KeyEvent keyEvent) {
        registerDataHandling();
    }

    public void confirmPassword(KeyEvent keyEvent) {
        registerDataHandling();
    }

    public void registerUsername(KeyEvent keyEvent) {
        registerDataHandling();
    }

    public void registerNickname(KeyEvent keyEvent) {
        registerDataHandling();
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

    public void initialize() throws MalformedURLException {
        Music.getInstance().getMenuMusic().play();
        setInitializeMuteUnmute();
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> setBackgroundPositions(content, xPosition.get()));
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

    public void loginAsAGuest(MouseEvent mouseEvent) throws IOException {
        MainMenu.setUp(null, userDatabase);
        App.changeFxml("/project/fxml/MainMenu.fxml", "Main Menu");
    }
}
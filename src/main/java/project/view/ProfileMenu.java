package project.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import project.App;
import project.controllers.Output;
import project.controllers.ProfileMenuController;
import project.models.Avatar;
import project.models.Music;
import project.models.User;
import project.models.UserDatabase;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class ProfileMenu {
    private static User user;
    private static UserDatabase userDatabase;
    private static ProfileMenuController profileMenuController;

    public static void setUp(User user, UserDatabase userDatabase) {
        ProfileMenu.user = user;
        ProfileMenu.userDatabase = userDatabase;
        profileMenuController = new ProfileMenuController(user, userDatabase);
    }

    @FXML
    private ImageView muteUnmuteImage;
    @FXML
    public Button exitImageVBox;
    @FXML
    private VBox imageVBox;
    @FXML
    private Button submit;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField nickname;
    @FXML
    private TextField username;
    @FXML
    private BorderPane content;
    @FXML
    private ImageView profilePicture;
    @FXML
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ImageView avatar4;
    @FXML
    public ImageView[] avatars;
    @FXML
    private VBox imageBox1;
    @FXML
    private VBox imageBox3;
    @FXML
    private VBox imageBox2;
    @FXML
    private VBox imageBox4;
    @FXML
    private VBox[] imageBoxes;

    public void initialize() throws MalformedURLException {
        setInitializeMuteUnmute();
        avatars = new ImageView[]{avatar1, avatar2, avatar3, avatar4};
        imageBoxes = new VBox[]{imageBox1, imageBox2, imageBox3, imageBox4};
        for (int i = 0; i < 4; i++)
            avatars[i].setImage(new Image(String.valueOf(Avatar.values()[i].getUrl())));

        imageVBox.setVisible(false);
        profilePicture.setImage(new Image(String.valueOf(user.getUrl())));
        username.setText(user.getUsername());
        nickname.setText(user.getNickname());
        password.setText(user.getPassword());
        confirmPassword.setVisible(false);
        confirmPasswordLabel.setVisible(false);
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

    //methods
    private void handleData() {
        boolean[] isValid = {false, false, false, false};
        String usernameText = username.getText();
        String nicknameText = nickname.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();

        if (!profileMenuController.isValidInput(nicknameText))
            this.nickname.setStyle("-fx-border-color: Red;");
        else {
            this.nickname.setStyle("-fx-border-color: #32e532");
            isValid[0] = true;
        }
        if (!profileMenuController.isStrongPassword(passwordText))
            this.password.setStyle("-fx-border-color: Red;");
        else {
            this.password.setStyle("-fx-border-color: #32e532");
            isValid[1] = true;
        }
        if (confirmPassword.isVisible()) {
            if (!passwordText.equals(confirmPasswordText))
                this.confirmPassword.setStyle("-fx-border-color: Red;");
            else {
                this.confirmPassword.setStyle("-fx-border-color: #32e532");
                isValid[2] = true;
            }
        } else isValid[2] = true;

        if (!profileMenuController.isValidInput(usernameText))
            this.username.setStyle("-fx-border-color: Red;");
        else {
            this.username.setStyle("-fx-border-color: #32e532");
            isValid[3] = true;
        }
        submit.setDisable(!isValid[0] || !isValid[1] || !isValid[2] || !isValid[3]);
    }

    public void password(KeyEvent keyEvent) {
        confirmPasswordLabel.setVisible(true);
        confirmPassword.setVisible(true);
        handleData();
    }

    public void nickname(KeyEvent keyEvent) {
        handleData();
    }

    public void confirmPassword(KeyEvent keyEvent) {
        handleData();
    }

    public void submit(MouseEvent mouseEvent) {
        Output output = profileMenuController.changeUser(username.getText(), nickname.getText(), password.getText());
        if (output == Output.REPEATED_USERNAME) new PopupMessage(Alert.AlertType.ERROR, output.toString());
        else new PopupMessage(Alert.AlertType.INFORMATION, output.toString());
    }

    public void exit(MouseEvent mouseEvent) throws IOException {
        App.changeFxml("/project/fxml/MainMenu.fxml", "Main Menu");
    }

    public void chooseFileProfilePicture(MouseEvent mouseEvent) throws MalformedURLException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, PNG, JPEG Files", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fc.showOpenDialog(App.getStage());
        if (selectedFile != null) {
            ListView listView = new ListView();
            listView.getItems().add(selectedFile.getAbsoluteFile());
            String fileName = String.valueOf(listView.getItems());
            fileName = fileName.substring(1, fileName.length() - 1);
            user.setUrl(Paths.get(fileName).toUri().toURL());
            this.profilePicture.setImage(new Image(String.valueOf(Paths.get(fileName).toUri().toURL())));
            listView.getItems().clear();
        }
    }

    public void chooseFromAvatars(MouseEvent mouseEvent) {
        imageVBox.setVisible(true);
        for (int i = 0; i < 4; i++) {
            if (user.getUrl().equals(Avatar.values()[i].getUrl())) {
                imageBoxes[i].setStyle("-fx-border-color: red;" +
                        "-fx-border-radius: 30 30 30 30;" +
                        "-fx-background-radius: 30 30 30 30;" +
                        "-fx-stroke-width: 20;"
                );
            } else imageBoxes[i].setStyle("");
        }
        profilePicture.setImage(new Image(String.valueOf(user.getUrl())));
    }

    public void exitImageVBox(MouseEvent mouseEvent) {
        imageVBox.setVisible(false);
    }

    public void selectImage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == avatar1) user.setUrl(Avatar.AVATAR1.getUrl());
        if (mouseEvent.getSource() == avatar2) user.setUrl(Avatar.AVATAR2.getUrl());
        if (mouseEvent.getSource() == avatar3) user.setUrl(Avatar.AVATAR3.getUrl());
        if (mouseEvent.getSource() == avatar4) user.setUrl(Avatar.AVATAR4.getUrl());
        chooseFromAvatars(null);
    }

    public void username(KeyEvent keyEvent) {
        handleData();
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

    public void removeAccount(MouseEvent mouseEvent) throws IOException {
        userDatabase.getUsers().remove(user);
        App.changeFxml("/project/fxml/loginMenu.fxml", "Login Menu");
    }
}
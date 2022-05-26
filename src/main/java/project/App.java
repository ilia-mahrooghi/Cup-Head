package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.models.UserDatabase;
import project.view.LoginMenu;
import project.view.PopupMessage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    private static Scene scene;
    private static Stage stage;

    public static Scene getScene() {
        return App.scene;
    }

    public static Stage getStage() {
        return App.stage;
    }

    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();
        LoginMenu.setUp(userDatabase);
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL address = new URL(getClass().getResource("/project/fxml/loginMenu.fxml").toString());
        Parent root = FXMLLoader.load(address);
        App.scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login menu");
        stage.setFullScreen(true);
        stage.setResizable(false);
        PopupMessage.setStage(stage);
        PopupMessage.setRoot(root);
        App.stage = stage;
        stage.show();
    }

    public static void setRoot(Parent root) throws IOException {
        App.scene.setRoot(root);
    }

    public static void changeFxml(String path, String title) throws IOException {
        URL address = new URL(App.class.getResource(path).toString());
        Parent root = FXMLLoader.load(address);
        setRoot(root);
        stage.setTitle(title);
        PopupMessage.setRoot(root);
    }
}
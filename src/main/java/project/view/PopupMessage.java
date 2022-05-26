package project.view;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupMessage {
    private static Stage stage;
    private static Parent root;

    public PopupMessage(Alert.AlertType alertType, String label) {
        if (alertType.equals(Alert.AlertType.ERROR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            initializeAlert(label, alert);
            setStyleForError(alert);
            setStyleForButton(alert);
            makeScreenBlur(alert);
            alert.show();
        } else if (alertType.equals(Alert.AlertType.INFORMATION)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            initializeAlert(label, alert);
            setStyleForInformation(alert);
            setStyleForButton(alert);
            makeScreenBlur(alert);
            alert.show();
        }
    }

    private void initializeAlert(String label, Alert alert) {
        alert.setContentText(label);
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
    }

    private void makeScreenBlur(Alert alert) {
        root.setEffect(new GaussianBlur(20));
        alert.setOnCloseRequest(dialogEvent -> root.setEffect(null));
    }

    private void setStyleForError(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #ec0e0e; -fx-border-width: 7; -fx-background-radius: 14; -fx-background-color: #9e376c; -fx-font-family: \"High Tower Text\";");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-line-spacing: 5px;");
    }

    private void setStyleForInformation(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #f700ff; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"High Tower Text\"; -fx-background-color: #d057ec;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
    }

    private void setStyleForButton(Alert alert) {
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #ffffff; -fx-font-size: 16; -fx-text-fill: rgba(40,38,38,0.7);"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
    }

    public static void setStage(Stage stage) {
        PopupMessage.stage = stage;
    }

    public static void setRoot(Parent root) {
        PopupMessage.root = root;
    }
}
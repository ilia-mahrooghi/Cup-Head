package project.models.Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.App;
import project.view.Game.Animation.AirplaneBombMove;
import project.view.Game.GameMenu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class AirplaneBomb extends ImageView {

    public AirplaneBomb() {
        try {
            this.setImage(new Image(String.valueOf(
                    new URL(Objects.requireNonNull(App.class.getResource("/project/png/frames/images/bomb.png")).toString()))));
            setX(Airplane.getInstance().getX() + (Airplane.getInstance().getFitWidth() / 2));
            setY(Airplane.getInstance().getY() + Airplane.getInstance().getFitHeight());
            GameMenu.getMainPane().getChildren().add(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AirplaneBombMove airplaneBombMove = new AirplaneBombMove(this);
        airplaneBombMove.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameMenu.getMainPane().getChildren().remove(this);
            }
        });
        airplaneBombMove.play();
    }

    public void moveDown() {
        this.setY(this.getY() + 8);
    }

    public boolean isHeatBoss() throws MalformedURLException {
        if (Boss.getInstance().getPhaseNumber() == 1) {
            double distance = Math.pow(
                    Math.pow(Boss.getInstance().getX() + 300 - this.getX(), 2)
                            + Math.pow(Boss.getInstance().getY() + (Boss.getInstance().getFitHeight() / 2) - this.getY(), 2), 0.5);
            return distance < 300;
        } else if (Boss.getInstance().getPhaseNumber() == 2) {
            return Boss.getInstance().getBoundsInParent().intersects(this.getBoundsInLocal());
        }
        return false;
    }

}
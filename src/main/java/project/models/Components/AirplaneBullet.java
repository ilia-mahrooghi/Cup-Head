package project.models.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.App;
import project.view.Game.Animation.AirPlaneBulletMove;
import project.view.Game.GameMenu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class AirplaneBullet extends ImageView {

    public AirplaneBullet() throws MalformedURLException {
        this.setImage(new Image(String.valueOf(
                new URL(Objects.requireNonNull(App.class.getResource("/project/png/frames/images/bullet.png")).toString()))));
        setX(Airplane.getInstance().getX() + Airplane.getInstance().getFitWidth());
        setY(Airplane.getInstance().getY() + (Airplane.getInstance().getFitHeight() / 2));
        GameMenu.getMainPane().getChildren().add(this);
        AirPlaneBulletMove airPlaneBulletMove = new AirPlaneBulletMove(this);
        airPlaneBulletMove.play();
    }

    public void moveRight() throws MalformedURLException {
        this.setX(this.getX() + 25);
    }

    public boolean isHeatBoss() throws MalformedURLException {
        if (Boss.getInstance().getPhaseNumber() == 1) {
            double distance = Math.pow(
                    Math.pow(Boss.getInstance().getX() + 300 - this.getX(), 2)
                            + Math.pow(Boss.getInstance().getY() + (Boss.getInstance().getFitHeight() / 2) - this.getY(), 2), 0.5);
            return distance < 300;
        } else if (Boss.getInstance().getPhaseNumber() == 2) {
            return Math.abs(Boss.getInstance().getX() - this.getX() + 50) <= 100 &&
                    Math.abs(Boss.getInstance().getY() - this.getY() + 75) <= 150;
        } else {
            return true;
        }
    }
}
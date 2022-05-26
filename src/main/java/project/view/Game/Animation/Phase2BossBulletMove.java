package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import project.App;
import project.models.Components.Airplane;
import project.models.Components.Boss;
import project.view.Game.GameMenu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Phase2BossBulletMove extends Transition {
    private ImageView bullet = new ImageView();
    private final double speed = 20;

    public Phase2BossBulletMove() throws MalformedURLException {
        bullet.setImage(new Image(String.valueOf(
                new URL(Objects.requireNonNull(App.class.getResource("/project/png/frames/phase2Shot/Bullet/1.png")).toString()))));
        GameMenu.getMainPane().getChildren().add(bullet);
        bullet.setX(Boss.getInstance().getX() - 70);
        bullet.setY(Boss.getInstance().getY() + 100);
        setCycleCount(1);
        setCycleDuration(Duration.millis(5000));
        setOnFinished(event -> GameMenu.getMainPane().getChildren().remove(bullet));
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) Math.floor(frac * 2000) + 1;
        frame = frame % 8 + 1;
        try {
            if (Airplane.getInstance().isHeated(this.bullet) && !Airplane.getInstance().isRocket())
                new AirplaneHeat(true);
            bullet.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                    "/project/png/frames/phase2Shot/Bullet/" + frame + ".png")).toString()))));
            moveLeft();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void moveLeft() {
        bullet.setX(bullet.getX() - speed);
    }
}

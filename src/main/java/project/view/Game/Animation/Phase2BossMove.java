package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.Airplane;
import project.models.Components.Boss;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Phase2BossMove extends Transition {

    public Phase2BossMove() {
        setCycleCount(-1);
        setCycleDuration(Duration.millis(2000));
    }

    @Override
    protected void interpolate(double frac) {
        try {
            int frame = (int) (Math.abs(frac * 15)) + 1;
            if (Airplane.getInstance().isHeated(Boss.getInstance()) && !Boss.getInstance().isDeath())
                new AirplaneHeat(true);
            if (Boss.getInstance().getHealth() <= 0) pause();
            if (!Boss.getInstance().isShooting()) {
                Boss.getInstance().phase2Move();
                Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                        App.class.getResource("/project/png/frames/phase2Move/" + frame + ".png")).toString()))));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
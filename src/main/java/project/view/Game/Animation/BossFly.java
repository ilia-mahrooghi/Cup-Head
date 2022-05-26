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

public class BossFly extends Transition {

    public BossFly() {
        setCycleDuration(Duration.millis(800));
        setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) Math.floor(frac * 5) + 1;
        try {
            if (Airplane.getInstance().isHeated(Boss.getInstance()) && !Boss.getInstance().isDeath()) new AirplaneHeat(true);
            if (Boss.getInstance().getHealth() <= 0) pause();
            if (!Boss.getInstance().isShooting())
                Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                        App.class.getResource("/project/png/frames/BossFly/" + frame + ".png")).toString()))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            Boss.getInstance().moveBoss();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
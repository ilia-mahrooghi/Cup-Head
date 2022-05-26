package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.Boss;
import project.models.Music;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Phase2BossDeath extends Transition {

    public Phase2BossDeath() {
        Music.getInstance().getPhase2Death().play();
        setCycleDuration(Duration.millis(3500));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) Math.floor(frac * 29) + 1;
        try {
            if (frame <= 12)
                Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                        App.class.getResource("/project/png/frames/Phase2Death/" + frame + ".png")).toString()))));
            else Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                    App.class.getResource("/project/png/frames/Phase2Death/end/" + (frame - 12) + ".png")).toString()))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}

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

public class Phase2BossShot extends Transition {
    private boolean isShot = false;

    public Phase2BossShot() {
        setCycleCount(1);
        setCycleDuration(Duration.millis(1500));
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) (Math.floor(18 * frac)) + 1;
        try {
            Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                    App.class.getResource("/project/png/frames/phase2Shot/" + frame + ".png")).toString()))));
            if (frame == 10 && !isShot) {
                Music.getInstance().getPhase2Shot().play();
                isShot = true;
                new Phase2BossBulletMove().play();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

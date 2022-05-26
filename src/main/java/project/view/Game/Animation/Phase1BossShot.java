package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.Boss;
import project.models.Components.BossEgg;
import project.models.Music;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Phase1BossShot extends Transition {
    private boolean isShot = false;

    public Phase1BossShot() {
        setCycleDuration(Duration.millis(1000));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double frac) {
        try {
            int frame = (int) (Math.floor(frac * 11)) + 1;
            if (frame == 5 && !isShot) {
                isShot = true;
                new BossEgg();
                Music.getInstance().getPhase1BossEgg().play();
            }
            Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                    App.class.getResource("/project/png/frames/BossShoot/" + frame + ".png")).toString()))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

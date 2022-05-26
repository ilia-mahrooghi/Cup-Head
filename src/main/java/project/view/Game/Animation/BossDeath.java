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

public class BossDeath extends Transition {
    public BossDeath() {
        Music.getInstance().getPhase1Death().play();
        setCycleDuration(Duration.millis(2000));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) Math.floor(frac * 43) + 1;
        try {
            setHeightImage(frame);
            if (frame <= 26)
                Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                        App.class.getResource("/project/png/Phase 1/Death/" + frame + ".png")).toString()))));
            else Boss.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                    App.class.getResource("/project/png/Phase 1/Death/FX/A/" + (frame - 26) + ".png")).toString()))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setHeightImage(int frame) throws MalformedURLException {
        if (frame >= 13) {
            Boss.getInstance().setFitHeight(Boss.getInstance().getImage().getHeight() + 3);
        }
    }
}
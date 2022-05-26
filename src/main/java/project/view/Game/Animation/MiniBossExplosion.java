package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.MiniBoss;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MiniBossExplosion extends Transition {
    private MiniBoss miniBoss;

    public MiniBossExplosion(MiniBoss miniBoss) {
        this.miniBoss = miniBoss;
        setCycleCount(1);
        setCycleDuration(Duration.millis(50));
    }

    @Override
    protected void interpolate(double frac) {
        try {
            int frame = (int) (Math.floor(frac * 8) + 1);
            miniBoss.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                    "/project/png/frames/MiniBossExplode/" + miniBoss.getColor() + "/" + frame + ".png")).toString()))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}

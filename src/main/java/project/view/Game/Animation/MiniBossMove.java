package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.Airplane;
import project.models.Components.MiniBoss;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MiniBossMove extends Transition {
    private MiniBoss miniBoss;

    public MiniBossMove(MiniBoss miniBoss) {
        this.miniBoss = miniBoss;
        setCycleCount(1);
        setCycleDuration(Duration.millis(12000));
    }

    @Override
    protected void interpolate(double frac) {
        if (!miniBoss.isDeath()) {
            int frame = (int) Math.floor(frac * 55);
            frame = frame % 4 + 1;
            try {
                if (Airplane.getInstance().isHeated(miniBoss)) new AirplaneHeat(true);
                miniBoss.moveLeft();
                miniBoss.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                        "/project/png/frames/MiniBossFly/" + miniBoss.getColor() + "/" + frame + ".png")).toString()))));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}

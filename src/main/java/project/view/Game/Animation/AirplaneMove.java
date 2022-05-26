package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.Airplane;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class AirplaneMove extends Transition {
    public AirplaneMove() {
        setCycleCount(-1);
        setCycleDuration(Duration.millis(20));
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) (Math.floor(frac * 3) + 1);
        try {
            if (!Airplane.getInstance().isRocket())
                Airplane.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                        "/project/png/frames/AirplaneMove/" + frame + ".png")).toString()))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
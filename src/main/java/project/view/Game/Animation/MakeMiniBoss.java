package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import project.models.Components.MiniBoss;

import java.net.MalformedURLException;

public class MakeMiniBoss extends Transition {
    private boolean[] madeMiniBosses = {false, false, false};
    private double yCoordinate;

    public MakeMiniBoss(double yCoordinate) {
        this.yCoordinate = yCoordinate;
        setCycleCount(1);
        setCycleDuration(Duration.millis(1500));
    }

    @Override
    protected void interpolate(double frac) {
        int number = (int) Math.floor(frac * 2);
        if (!madeMiniBosses[number]) {
            madeMiniBosses[number] = true;
            try {
                new MiniBoss(yCoordinate);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}

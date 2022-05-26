package project.view.Game.Animation;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import project.models.Components.Airplane;

import java.net.MalformedURLException;

public class AirplaneHeat {
    public AirplaneHeat(boolean isHeated) throws MalformedURLException {
        if (!Airplane.getInstance().isHeated()) {
            Airplane.getInstance().setHeated(true);
            if (isHeated && !Airplane.getInstance().isRocket())
                Airplane.getInstance().setHealth(Airplane.getInstance().getHealth() - 1);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), Airplane.getInstance());
            fadeTransition.setAutoReverse(true);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setCycleCount(1);
            fadeTransition.setOnFinished(event -> {
                try {
                    Airplane.getInstance().setHeated(false);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
            fadeTransition.play();
        }
    }
}

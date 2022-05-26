package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import project.models.Components.Airplane;
import project.models.Components.AirplaneBomb;
import project.models.Components.Boss;
import project.models.Components.MiniBoss;
import project.view.Game.GameMenu;

import java.net.MalformedURLException;

public class AirplaneBombMove extends Transition {
    private final AirplaneBomb airplaneBomb;

    public AirplaneBombMove(AirplaneBomb airplaneBomb) {
        setCycleCount(1);
        setCycleDuration(Duration.millis(5000));
        this.airplaneBomb = airplaneBomb;
    }

    @Override
    protected void interpolate(double frac) {
        try {
            airplaneBomb.moveDown();
            if (airplaneBomb.isHeatBoss() || getHeatedMiniBoss() != null) {
                if (airplaneBomb.isHeatBoss()) {
                    Boss.getInstance().setHealth(Boss.getInstance().getHealth() - Airplane.getInstance().getBombDamage());
                    Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 4);
                } else {
                    getHeatedMiniBoss().setHealth(getHeatedMiniBoss().getHealth() - 2);
                    Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 10);
                }
                pause();
                AirplaneBombExplosion airplaneBombExplosion = new AirplaneBombExplosion(airplaneBomb);
                airplaneBombExplosion.setOnFinished(event -> GameMenu.getMainPane().getChildren().remove(airplaneBomb));
                airplaneBombExplosion.play();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private MiniBoss getHeatedMiniBoss() {
        for (MiniBoss miniBoss : MiniBoss.getAllMiniBosses()) {
            if (miniBoss.getBoundsInParent().intersects(airplaneBomb.getLayoutBounds()))
                return miniBoss;
        }
        return null;
    }
}

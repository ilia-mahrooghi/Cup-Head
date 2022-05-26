package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import project.models.Components.Airplane;
import project.models.Components.AirplaneBullet;
import project.models.Components.Boss;
import project.models.Components.MiniBoss;
import project.view.Game.GameMenu;

import java.net.MalformedURLException;

public class AirPlaneBulletMove extends Transition {
    private AirplaneBullet airplaneBullet;

    public AirPlaneBulletMove(AirplaneBullet airplaneBullet) {
        this.airplaneBullet = airplaneBullet;
        setCycleCount(1);
        setCycleDuration(Duration.millis((1900 - airplaneBullet.getLayoutX()) * 500));
    }

    @Override
    protected void interpolate(double frac) {
        try {
            airplaneBullet.moveRight();
            if ((airplaneBullet.isHeatBoss() && !Boss.getInstance().isDeath()) || getHeatedMiniBoss() != null) {
                if (airplaneBullet.isHeatBoss() && !Boss.getInstance().isDeath()) {
                    Boss.getInstance().setHealth(Boss.getInstance().getHealth() - Airplane.getInstance().getBulletDamage());
                    Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 2);
                } else {
                    getHeatedMiniBoss().setHealth(getHeatedMiniBoss().getHealth() - 1);
                    Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 10);
                }
                pause();
                airplaneBullet.setFitWidth(40);
                airplaneBullet.setFitHeight(40);
                AirplaneBulletExplosion airplaneBulletExplosion = new AirplaneBulletExplosion(airplaneBullet);
                airplaneBulletExplosion.setOnFinished(event -> GameMenu.getMainPane().getChildren().remove(airplaneBullet));
                airplaneBulletExplosion.play();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private MiniBoss getHeatedMiniBoss() {
        for (MiniBoss miniBoss : MiniBoss.getAllMiniBosses()) {
            if (miniBoss.getBoundsInParent().intersects(airplaneBullet.getLayoutBounds()))
                return miniBoss;
        }
        return null;
    }
}

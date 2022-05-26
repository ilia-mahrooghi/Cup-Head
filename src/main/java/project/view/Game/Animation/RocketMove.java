package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import project.models.Components.Airplane;
import project.models.Components.Boss;
import project.models.Components.MiniBoss;

import java.net.MalformedURLException;

public class RocketMove extends Transition {

    private double firstXCoordinate;

    public RocketMove(double firstXCoordinate) {
        this.firstXCoordinate = firstXCoordinate;
        setCycleDuration(Duration.millis(5000));
        setCycleCount(1);
        try {
            Airplane.getInstance().setFitHeight(90);
            Airplane.getInstance().setFitWidth(140);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.setOnFinished(event -> {
            try {
                Airplane.getInstance().setRocket(false);
                Airplane.getInstance().setWidthAndHeight();
                Airplane.getInstance().setX(firstXCoordinate);
                new AirplaneHeat(false);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void interpolate(double frac) {
        try {
            Airplane.getInstance().rocketMoveRight();
            if (isHeatBoss() || getHeatedMiniBoss() != null) {
                if (isHeatBoss()) {
                    Boss.getInstance().setHealth(Boss.getInstance().getHealth() - Airplane.getInstance().getRocketDamage());
                    Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 6);
                } else {
                    getHeatedMiniBoss().setHealth(getHeatedMiniBoss().getHealth() - Airplane.getInstance().getRocketDamage());
                    Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 10);
                }
                Airplane.getInstance().setWidthAndHeight();
                pause();
                RocketExplosion rocketExplosion = new RocketExplosion(firstXCoordinate);
                rocketExplosion.play();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private MiniBoss getHeatedMiniBoss() throws MalformedURLException {
        for (MiniBoss miniBoss : MiniBoss.getAllMiniBosses()) {
            if (miniBoss.getBoundsInParent().intersects(Airplane.getInstance().getLayoutBounds()))
                return miniBoss;
        }
        return null;
    }

    private boolean isHeatBoss() throws MalformedURLException {
        double distance = Math.pow(
                Math.pow(Boss.getInstance().getX() + 300 - Airplane.getInstance().getX(), 2)
                        + Math.pow(Boss.getInstance().getY() + (Boss.getInstance().getFitHeight() / 2) - Airplane.getInstance().getY(), 2), 0.5);
        return distance < 300;
    }
}

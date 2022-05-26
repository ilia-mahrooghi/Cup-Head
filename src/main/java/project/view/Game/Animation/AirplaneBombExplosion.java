package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.AirplaneBomb;

public class AirplaneBombExplosion extends Transition {

    private AirplaneBomb airplaneBomb;

    public AirplaneBombExplosion(AirplaneBomb airplaneBomb) {
        this.airplaneBomb = airplaneBomb;
        setCycleDuration(Duration.millis(500));
    }


    @Override
    protected void interpolate(double frac) {
        int frame = (int) (Math.floor(frac * 8) + 1);
        if (frame >= 4) {
            airplaneBomb.setX(airplaneBomb.getX() - 5);
            airplaneBomb.setY(airplaneBomb.getY() - 5);
        }
        airplaneBomb.setImage(new Image(
                App.class.getResource("/project/png/frames/airplaneDamage/bombDamage/" + frame + ".png").toString()));
    }
}
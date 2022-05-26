package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.AirplaneBullet;

import java.net.MalformedURLException;

public class AirplaneBulletExplosion extends Transition {

    private AirplaneBullet airplaneBullet;

    public AirplaneBulletExplosion(AirplaneBullet airplaneBullet) throws MalformedURLException {
        this.airplaneBullet = airplaneBullet;
        setCycleCount(1);
        setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) Math.floor(frac * 10);
        airplaneBullet.setImage(new Image(
                App.class.getResource("/project/png/frames/airplaneDamage/bulletDamage/" + frame + ".png").toString()));
    }
}

package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import project.App;
import project.models.Components.Airplane;
import project.models.Components.BossEgg;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class BossEggMove extends Transition {
    private BossEgg bossEgg;

    public BossEggMove(BossEgg bossEgg) {
        this.bossEgg = bossEgg;
        setCycleDuration(Duration.millis(bossEgg.getX() * 10));
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) (Math.floor(frac * 300));
        frame %= 12;
        frame++;
        try {
            if (Airplane.getInstance().isHeated(bossEgg) && !Airplane.getInstance().isRocket()) new AirplaneHeat(true);
            bossEgg.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                    App.class.getResource("/project/png/frames/Phase1EggMove/" + frame + ".png")).toString()))));
            bossEgg.moveLeft();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}

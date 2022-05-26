package project.view.Game.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import project.App;
import project.models.Components.Airplane;
import project.view.Game.GameMenu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class AirplaneChangeToRocket extends Transition {
    private ImageView lighting = new ImageView();
    private boolean isLightingAdded = false;

    public AirplaneChangeToRocket() throws MalformedURLException {
        Airplane.getInstance().setRocket(true);
        setCycleDuration(Duration.millis(1000));
        setCycleCount(1);
        lighting.setFitWidth(1000);
        lighting.setFitHeight(1000);
        lighting.setX(Airplane.getInstance().getX() - 435);
        lighting.setY(Airplane.getInstance().getY() - 435);
        Airplane.getInstance().setFitHeight(Airplane.getInstance().getFitHeight() + 20);
        Airplane.getInstance().setFitWidth(Airplane.getInstance().getFitWidth() + 20);
        setOnFinished(event -> {
            try {
                Airplane.getInstance().setFitHeight(Airplane.getInstance().getFitHeight() - 20);
                Airplane.getInstance().setFitWidth(Airplane.getInstance().getFitWidth() - 20);
                GameMenu.getMainPane().getChildren().remove(lighting);
                Airplane.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                        "/project/png/frames/ChangeToRocket/13.png")).toString()))));
                new RocketMove(Airplane.getInstance().getX()).play();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) (Math.floor(frac * 12)) + 1;
        if (frame <= 12)
            try {
                if (frame == 8 && !isLightingAdded) {
                    isLightingAdded = true;
                    GameMenu.getMainPane().getChildren().remove(Airplane.getInstance());
                    GameMenu.getMainPane().getChildren().add(lighting);
                    GameMenu.getMainPane().getChildren().add(Airplane.getInstance());
                }
                int lightingFrame = frame - 7;
                if (isLightingAdded)
                    lighting.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                            "/project/png/frames/ChangeToRocket/lighting/" + lightingFrame + ".png")).toString()))));
                Airplane.getInstance().setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                        "/project/png/frames/ChangeToRocket/" + frame + ".png")).toString()))));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
    }
}
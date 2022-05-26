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

public class RocketExplosion extends Transition {
    private double firstXCoordinate;
    private ImageView lighting = new ImageView();
    private ImageView boom = new ImageView();

    public RocketExplosion(double firstXCoordinate) throws MalformedURLException {
        this.firstXCoordinate = firstXCoordinate;
        boom.setX(Airplane.getInstance().getX() - 300);
        boom.setY(Airplane.getInstance().getY() - 235);
        boom.setFitHeight(400);
        boom.setFitWidth(600);
        GameMenu.getMainPane().getChildren().add(lighting);
        GameMenu.getMainPane().getChildren().add(boom);
        lighting.setFitWidth(1000);
        lighting.setFitHeight(1000);
        lighting.setX(Airplane.getInstance().getX() - 470);
        lighting.setY(Airplane.getInstance().getY() - 470);
        this.setOnFinished(event -> {
            try {
                GameMenu.getMainPane().getChildren().remove(lighting);
                GameMenu.getMainPane().getChildren().remove(boom);
                Airplane.getInstance().setRocket(false);
                Airplane.getInstance().setWidthAndHeight();
                Airplane.getInstance().setX(firstXCoordinate);
                new AirplaneHeat(false);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        setCycleCount(1);
        setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) (Math.floor(frac * 18)) + 1;
        int lightingFrame = (int) (Math.floor(frac * 4)) + 1;
        boom.setFitHeight(boom.getFitHeight() + 30);
        boom.setFitWidth(boom.getFitWidth() + 30);
        boom.setX(boom.getX() - 15);
        boom.setY(boom.getY() - 15);
        try {
            lighting.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                    "/project/png/frames/ChangeToRocket/lighting/" + lightingFrame + ".png")).toString()))));
            boom.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                    App.class.getResource("/project/png/frames/ChangeToRocket/Boom/" + frame + ".png")).toString()))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

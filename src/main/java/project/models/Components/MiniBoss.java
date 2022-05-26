package project.models.Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.App;
import project.view.Game.GameMenu;
import project.view.Game.Animation.MiniBossMove;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MiniBoss extends ImageView {
    private static ArrayList<MiniBoss> allMiniBosses = new ArrayList<>();

    public static ArrayList<MiniBoss> getAllMiniBosses() {
        return allMiniBosses;
    }

    private int health = 2;
    private String color = "purple";
    private boolean isDeath = false;

    public MiniBoss(double yCoordinate) throws MalformedURLException {
        if (new Random().nextInt() % 2 == 0) this.color = "purple";
        else this.color = "yellow";
        new Image(String.valueOf(new URL(Objects.requireNonNull(App.class.getResource(
                "/project/png/frames/MiniBossFly/" + color + "/1.png")).toString())));
        setX(1900);
        setY(yCoordinate);
        setFitHeight(120);
        setFitWidth(120);
        allMiniBosses.add(this);
        GameMenu.getMainPane().getChildren().add(this);
        MiniBossMove miniBossMove = new MiniBossMove(this);
        miniBossMove.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameMenu.getMainPane().getChildren().remove(this);
                allMiniBosses.remove(this);
            }
        });
        miniBossMove.play();
    }

    public void moveLeft() throws MalformedURLException {
        this.setX(this.getX() - 8);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getColor() {
        return color;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }
}

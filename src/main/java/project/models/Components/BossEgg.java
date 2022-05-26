package project.models.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.App;
import project.view.Game.GameMenu;
import project.view.Game.Animation.BossEggMove;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class BossEgg extends ImageView {

    public BossEgg() throws MalformedURLException {
        this.setImage(new Image(String.valueOf(
                new URL(Objects.requireNonNull(App.class.getResource("/project/png/frames/Phase1EggMove/1.png")).toString()))));
        setX(Boss.getInstance().getX() - 10);
        setY(Boss.getInstance().getY() + (Boss.getInstance().getFitHeight() / 2));
        GameMenu.getMainPane().getChildren().add(this);
        BossEggMove bossEggMove = new BossEggMove(this);
        bossEggMove.play();
    }

    public void moveLeft() throws MalformedURLException {
        this.setX(this.getX() - 13);
    }
}

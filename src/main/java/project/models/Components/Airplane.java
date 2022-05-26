package project.models.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.App;
import project.view.Game.Animation.AirplaneMove;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Airplane extends ImageView {
    private static Airplane instance;

    private final int speed = 15;
    private int health = 10;
    private int bulletDamage = 2;
    private int bombDamage = 4;
    private int rocketDamage = 8;
    private int timeLeftToAllowBulletShot = 0;
    private int timeLeftToAllowBombShot = 0;
    private int timeLeftToAllowRocketShot = 0;
    private final int totalTimeForRocketShot = 20000;
    private boolean isShootingBullet = true;
    private boolean isHeated = false;
    private boolean isRocket = false;
    private int score = 0;

    public Airplane() throws MalformedURLException {
        this.setX(50);
        this.setY(350);
        this.setImage(new Image(String.valueOf(
                new URL(Objects.requireNonNull(App.class.getResource("/project/png/frames/AirplaneMove/1.png")).toString()))));
        setWidthAndHeight();
        new AirplaneMove().play();
    }

    public static Airplane getInstance() throws MalformedURLException {
        if (instance == null) instance = new Airplane();
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public void moveUp() {
        if (!isHeatUpWall())
            this.setY(this.getY() - speed);
    }

    public void moveRight() throws MalformedURLException {
        if (!isHeatRightWall() && !isRocket)
            this.setX(this.getX() + speed);
    }

    public void moveLeft() throws MalformedURLException {
        if (!isHeatLeftWall() && !isRocket)
            this.setX(this.getX() - speed);
    }

    public void moveDown() {
        if (!isHeatDownWall())
            this.setY(this.getY() + speed);
    }

    public boolean isHeatUpWall() {
        return this.getY() <= 0;
    }

    public boolean isHeatLeftWall() {
        return this.getX() <= 0;
    }

    public boolean isHeatRightWall() {
        return (this.getX() + this.getImage().getWidth()) >= 1900;
    }

    public boolean isHeatDownWall() {
        return (this.getY() + this.getImage().getHeight()) >= 1000;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public int getTimeLeftToAllowBulletShot() {
        return timeLeftToAllowBulletShot;
    }

    public void decreaseTimeLeftToAllowBulletShot() {
        if (timeLeftToAllowBulletShot > 0) {
            this.timeLeftToAllowBulletShot--;
        }
    }

    public void resetTimeLeftToAllowBulletShot() {
        timeLeftToAllowBulletShot = 150;
    }

    public void resetTimeLeftToAllowBombShot() {
        timeLeftToAllowBombShot = 500;
    }

    public int getTimeLeftToAllowBombShot() {
        return timeLeftToAllowBombShot;
    }

    public void decreaseTimeLeftToAllowBombShot() {
        if (timeLeftToAllowBombShot > 0) {
            this.timeLeftToAllowBombShot--;
        }
    }

    public void resetTimeLeftToAllowRocketShot() {
        timeLeftToAllowRocketShot = totalTimeForRocketShot;
    }

    public int getTimeLeftToAllowRocketShot() {
        return timeLeftToAllowRocketShot;
    }

    public void decreaseTimeLeftToAllowRocketShot() {
        if (timeLeftToAllowRocketShot > 0) {
            this.timeLeftToAllowRocketShot--;
        }
    }

    public boolean isShootingBullet() {
        return isShootingBullet;
    }

    public void setShootingBullet(boolean shootingBullet) {
        isShootingBullet = shootingBullet;
    }

    public int getBombDamage() {
        return bombDamage;
    }

    public void setBombDamage(int bombDamage) {
        this.bombDamage = bombDamage;
    }

    public boolean isHeated(ImageView object) {
        return this.getBoundsInParent().intersects(object.getLayoutBounds());
    }

    public boolean isHeated() {
        return isHeated;
    }

    public void setHeated(boolean heated) {
        isHeated = heated;
    }

    public int getRocketDamage() {
        return rocketDamage;
    }

    public void setRocketDamage(int rocketDamage) {
        this.rocketDamage = rocketDamage;
    }

    public int getTotalTimeForRocketShot() {
        return totalTimeForRocketShot;
    }

    public boolean isRocket() {
        return isRocket;
    }

    public void setRocket(boolean rocket) {
        isRocket = rocket;
    }

    public void rocketMoveRight() {
        this.setX(this.getX() + 25);
    }

    public void setWidthAndHeight() {
        this.setFitHeight(120);
        this.setFitWidth(120);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
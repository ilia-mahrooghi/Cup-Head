package project.models.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.App;
import project.view.Game.Animation.BossFly;
import project.view.Game.Animation.Phase2BossMove;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class Boss extends ImageView {
    private static Boss instance;


    private int speed = 10;
    private int phaseNumber = 1;
    private int health = 1200;
    private boolean isDeath = false;
    private int timeLeftToShot = 10000;
    private boolean isShooting = false;
    private int timeLeftForMiniBoss = 11000;
    private BossFly phase1Fly = null;
    private Phase2BossMove phase2BossMove = null;

    public final int getTotalHealth() {
        return 1200;
    }

    private boolean isMovingUp;

    private Boss() throws MalformedURLException {
        this.setX(1250);
        this.setY(170);
        this.setImage(new Image(String.valueOf(
                new URL(Objects.requireNonNull(App.class.getResource("/project/png/frames/BossFly/1.png")).toString()))));
        this.setFitHeight(600);
        this.setFitWidth(509);
        phase1Fly = new BossFly();
        phase1Fly.play();
    }

    public static Boss getInstance() throws MalformedURLException {
        if (instance == null) instance = new Boss();
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public void moveUp() {
        if (!isHeatUpWall())
            this.setY(this.getY() - speed);
    }

    public void moveRight() {
        if (!isHeatRightWall())
            this.setX(this.getX() + speed);
    }

    public void moveLeft() {
        if (!isHeatLeftWall())
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

    public void moveBoss() throws MalformedURLException {
        if (isMovingUp) {
            this.moveUp();
            if (isHeatUpWall()) isMovingUp = false;
        } else {
            this.moveDown();
            if (isHeatDownWall()) isMovingUp = true;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public void resetTimeLeftToShot() {
        this.timeLeftToShot = 10000;
    }

    public void decreaseTimeLeftToShot() {
        if (timeLeftToShot > 0)
            timeLeftToShot--;
    }

    public int getTimeLeftToShot() {
        return timeLeftToShot;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public void decreaseTimeLeftForMiniBoss() {
        timeLeftForMiniBoss--;
    }

    public void resetTimeLeftForMiniBoss() {
        this.timeLeftForMiniBoss = 11000;
    }

    public int getTimeLeftForMiniBoss() {
        return timeLeftForMiniBoss;
    }

    public int getPhaseNumber() {
        return phaseNumber;
    }

    public void setPhaseNumber(int phaseNumber) {
        this.phaseNumber = phaseNumber;
    }

    public void changeToPhase2() throws MalformedURLException {
        phaseNumber = 2;
        phase1Fly.pause();
        this.setFitHeight(300);
        this.setFitWidth(200);
        this.setImage(new Image(String.valueOf(new URL(Objects.requireNonNull(
                App.class.getResource("/project/png/frames/phase2Move/1.png")).toString()))));
        speed = 6;
        phase2BossMove = new Phase2BossMove();
        phase2BossMove.play();
    }

    private int directionMoveTimesLeftAndRight;
    private int directionLeftAndRight;

    private int directionMoveTimesUpAndDown;
    private int directionUpAndDown;

    public void phase2Move() {
        if (directionMoveTimesLeftAndRight == 0) {

            directionLeftAndRight = Math.abs(new Random().nextInt() % 3) - 1;
        }
        if (directionMoveTimesUpAndDown == 0) {
            directionUpAndDown = Math.abs(new Random().nextInt() % 3) - 1;
        }
        phase2MoveLeftAndRight();
        phase2MoveUpAndDown();
    }

    private void phase2MoveLeftAndRight() {
        switch (directionLeftAndRight) {
            case 0:
                if (isHeatLeftWall()) {
                    directionLeftAndRight = 1;
                    directionMoveTimesLeftAndRight = 1;
                    break;
                }
                moveLeft();
                directionMoveTimesLeftAndRight++;
                break;
            case 1:
                if (isHeatRightWall()) {
                    directionLeftAndRight = 0;
                    directionMoveTimesLeftAndRight = 1;
                    break;
                }
                moveRight();
                directionMoveTimesLeftAndRight++;
                break;
        }
        if (directionMoveTimesLeftAndRight == 50)
            directionMoveTimesLeftAndRight = 0;
    }

    private void phase2MoveUpAndDown() {
        switch (directionUpAndDown) {
            case 0:
                if (isHeatUpWall()) {
                    directionUpAndDown = 1;
                    directionMoveTimesUpAndDown = 1;
                    break;
                }
                moveUp();
                directionMoveTimesUpAndDown++;
                break;
            case 1:
                if (isHeatDownWall()) {
                    directionUpAndDown = 0;
                    directionMoveTimesUpAndDown = 1;
                    break;
                }
                moveDown();
                directionMoveTimesUpAndDown++;
                break;
        }
        if (directionMoveTimesUpAndDown == 30)
            directionMoveTimesUpAndDown = 0;
    }
}
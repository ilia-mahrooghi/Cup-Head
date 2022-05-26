package project.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import project.models.Components.*;
import project.models.Game;
import project.models.HighScore;
import project.models.User;
import project.view.Game.Animation.*;
import project.view.Game.GameMenu;

import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMenuController {
    private User user;
    private Game game;
    private boolean spacePressed = false;
    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean downPressed = false;
    private boolean upPressed = false;

    public GameMenuController(User user, Game game) {
        this.game = game;
        this.user = user;
    }

    public Airplane createAirplane() throws MalformedURLException {
        Airplane airplane = Airplane.getInstance();
        setAirplaneHealthAndDamage(airplane, game);
        return airplane;
    }

    public void setAirplaneMovement() throws MalformedURLException {
        Airplane.getInstance().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().getName().equals("Right") || event.getCode().getName().equals("D"))
                    rightPressed = true;
                if (event.getCode().getName().equals("Left") || event.getCode().getName().equals("A"))
                    leftPressed = true;
                if (event.getCode().getName().equals("Up") || event.getCode().getName().equals("W"))
                    upPressed = true;
                if (event.getCode().getName().equals("Down") || event.getCode().getName().equals("S"))
                    downPressed = true;
                if (event.getCode().getName().equals("Space"))
                    spacePressed = true;
                try {
                    if (event.getCode().getName().equals("Tab")) {
                        Airplane.getInstance().setShootingBullet(!Airplane.getInstance().isShootingBullet());
                    }
                    if (event.getCode().getName().equals("Enter") && !GameMenu.getIsEndGame()) {
                        if (Airplane.getInstance().getTimeLeftToAllowRocketShot() == 0) {
                            Airplane.getInstance().resetTimeLeftToAllowRocketShot();
                            new AirplaneChangeToRocket().play();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        Airplane.getInstance().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().getName().equals("Right") || event.getCode().getName().equals("D"))
                    rightPressed = false;
                if (event.getCode().getName().equals("Left") || event.getCode().getName().equals("A"))
                    leftPressed = false;
                if (event.getCode().getName().equals("Up") || event.getCode().getName().equals("W"))
                    upPressed = false;
                if (event.getCode().getName().equals("Down") || event.getCode().getName().equals("S"))
                    downPressed = false;
                if (event.getCode().getName().equals("Space"))
                    spacePressed = false;
            }
        });
    }

    public void moveAirplane() throws MalformedURLException {
        if (upPressed) Airplane.getInstance().moveUp();
        if (downPressed) Airplane.getInstance().moveDown();
        if (rightPressed) Airplane.getInstance().moveRight();
        if (leftPressed) Airplane.getInstance().moveLeft();
        if (spacePressed)
            if (Airplane.getInstance().isShootingBullet()
                    && Airplane.getInstance().getTimeLeftToAllowBulletShot() == 0) {
                new AirplaneBullet();
                Airplane.getInstance().resetTimeLeftToAllowBulletShot();
            } else if (!Airplane.getInstance().isShootingBullet() &&
                    Airplane.getInstance().getTimeLeftToAllowBombShot() == 0) {
                new AirplaneBomb();
                Airplane.getInstance().resetTimeLeftToAllowBombShot();
            }
    }


    private void setAirplaneHealthAndDamage(Airplane airplane, Game game) {
        if (game.getDifficulty() == 0) {
            airplane.setHealth(10);
            airplane.setBulletDamage(3);
            airplane.setBombDamage(6);
            airplane.setRocketDamage(12);
        }
        if (game.getDifficulty() == 1 || game.isDevilMode()) {
            airplane.setHealth(5);
            airplane.setBulletDamage(2);
            airplane.setBombDamage(4);
            airplane.setRocketDamage(8);
        }
        if (game.getDifficulty() == 2) {
            airplane.setHealth(2);
            airplane.setBulletDamage(1);
            airplane.setBombDamage(2);
            airplane.setRocketDamage(4);
        }
        if (game.isDevilMode())
            airplane.setHealth(3);
    }

    public void killMiniBoss() {
        for (MiniBoss miniBoss : MiniBoss.getAllMiniBosses()) {
            if (miniBoss.getHealth() <= 0) {
                miniBoss.setDeath(true);
                MiniBossExplosion miniBossExplosion = new MiniBossExplosion(miniBoss);
                miniBossExplosion.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        MiniBoss.getAllMiniBosses().remove(miniBoss);
                        GameMenu.getMainPane().getChildren().remove(miniBoss);
                    }
                });
                miniBossExplosion.play();
                break;
            }
        }
    }

    public void decreaseLeftTimes() throws MalformedURLException {
        Boss.getInstance().decreaseTimeLeftToShot();
        Airplane.getInstance().decreaseTimeLeftToAllowBulletShot();
        Airplane.getInstance().decreaseTimeLeftToAllowBombShot();
        Airplane.getInstance().decreaseTimeLeftToAllowRocketShot();
    }

    public void giveCommandToAirplane(AtomicInteger moveAirplaneCounter) throws MalformedURLException {
        moveAirplaneCounter.getAndDecrement();
        if (moveAirplaneCounter.get() == 0) {
            moveAirplane();
            moveAirplaneCounter.set(30);
        }
    }

    public void handlingPhase1BossDeath() throws MalformedURLException {
        boolean notDevilMode = (Boss.getInstance().getHealth() <= 0 && !game.isDevilMode());
        boolean devilMode = (Boss.getInstance().getHealth() <= Boss.getInstance().getTotalHealth() / 2
                && game.isDevilMode() && Boss.getInstance().getPhaseNumber() == 1);
        if (notDevilMode || devilMode) {
            if (!Boss.getInstance().isDeath()) {
                Boss.getInstance().setDeath(true);
                if (notDevilMode) Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 50);
                else Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 30);
                BossDeath bossDeath = new BossDeath();
                bossDeath.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            if (notDevilMode) {
                                GameMenu.setIsEndGame(true);
                                GameMenu.getMainPane().getChildren().remove(Boss.getInstance());
                            } else {
                                Boss.getInstance().setPhaseNumber(2);
                                Boss.getInstance().changeToPhase2();
                                Boss.getInstance().setDeath(false);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                bossDeath.play();
            }
        }
    }

    public void handlingPhase2BossDeath() throws MalformedURLException {
        if (Boss.getInstance().getHealth() <= 0 && Boss.getInstance().getPhaseNumber() == 2)
            if (!Boss.getInstance().isDeath()) {
                Airplane.getInstance().setScore(Airplane.getInstance().getScore() + 80);
                Boss.getInstance().setDeath(true);
                Phase2BossDeath bossDeath = new Phase2BossDeath();
                bossDeath.setOnFinished(event -> {
                    try {
                        GameMenu.setIsEndGame(true);
                        GameMenu.getMainPane().getChildren().remove(Boss.getInstance());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                });
                bossDeath.play();
            }
    }

    public void phase1BossShot() throws MalformedURLException {
        if (Boss.getInstance().getTimeLeftToShot() == 0 && Boss.getInstance().getPhaseNumber() == 1 && !Boss.getInstance().isDeath()) {
            Boss.getInstance().setShooting(true);
            Boss.getInstance().resetTimeLeftToShot();
            Phase1BossShot phase1BossShot = new Phase1BossShot();
            phase1BossShot.setOnFinished(event -> {
                try {
                    Boss.getInstance().setShooting(false);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
            phase1BossShot.play();
        }
    }

    public void phase2BossShot() throws MalformedURLException {
        if (Boss.getInstance().getPhaseNumber() == 2 && Boss.getInstance().getTimeLeftToShot() == 0 && !Boss.getInstance().isDeath()) {
            Boss.getInstance().setShooting(true);
            Boss.getInstance().resetTimeLeftToShot();
            Phase2BossShot phase2BossShot = new Phase2BossShot();
            phase2BossShot.setOnFinished(event -> {
                try {
                    Boss.getInstance().setShooting(false);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
            phase2BossShot.play();
        }
    }

    public void bossShot() throws MalformedURLException {
        phase1BossShot();
        phase2BossShot();
    }

    public void miniBossHandling() throws MalformedURLException {
        if (Boss.getInstance().getPhaseNumber() == 1) {
            Boss.getInstance().decreaseTimeLeftForMiniBoss();
            if (Boss.getInstance().getTimeLeftForMiniBoss() == 0) {
                new MakeMiniBoss(Boss.getInstance().getY()).play();
                Boss.getInstance().resetTimeLeftForMiniBoss();
            }
        }
    }

    public String getWinOrLooseString() throws MalformedURLException {
        if (Airplane.getInstance().getHealth() <= 0) return "Defeat";
        else return "Victory";
    }

    public void resetEveryThing() {
        Airplane.clear();
        Boss.clear();
        MiniBoss.getAllMiniBosses().clear();
    }

    public void isAirplaneDead() throws MalformedURLException {
        if (Airplane.getInstance().getHealth() <= 0)
            GameMenu.setIsEndGame(true);
    }

    public void changeHighScore(User user, int score, long time) {
        if (user == null) return;
        if (user.getHighScore().getScore() < score)
            user.setHighScore(new HighScore(score, time));
        if (user.getHighScore().getScore() == score && user.getHighScore().getTime() > time)
            user.setHighScore(new HighScore(score, time));
    }
}

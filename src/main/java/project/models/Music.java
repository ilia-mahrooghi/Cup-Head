package project.models;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import project.App;

import java.util.Objects;

public class Music {

    private static Music singleton;

    private final AudioClip buttonClick =
            new AudioClip(Objects.requireNonNull(App.class.getResource("/project/soundEffects/clickButton.wav")).toString());
    private final AudioClip phase1BossEgg =
            new AudioClip(Objects.requireNonNull(App.class.getResource("/project/soundEffects/gameSounds/eggExplode.wav")).toString());
    private final AudioClip phase1Death =
            new AudioClip(Objects.requireNonNull(App.class.getResource("/project/soundEffects/gameSounds/phase1Death.wav")).toString());
    private final AudioClip phase2Death =
            new AudioClip(Objects.requireNonNull(App.class.getResource("/project/soundEffects/gameSounds/phase2Death.wav")).toString());
    private final AudioClip phase2Shot =
            new AudioClip(Objects.requireNonNull(App.class.getResource("/project/soundEffects/gameSounds/phase2Shot.wav")).toString());

    private final MediaPlayer menuMusic =
            new MediaPlayer(new Media(Objects.requireNonNull(App.class.getResource("/project/music/MenuMusic.mp3")).toString()));
    private final MediaPlayer gameMusic =
            new MediaPlayer(new Media(Objects.requireNonNull(App.class.getResource("/project/music/Game.mp3")).toString()));

    private Music() {
        menuMusic.setCycleCount(-1);
    }

    public static Music getInstance() {
        if (singleton == null) singleton = new Music();
        return singleton;
    }

    public AudioClip getButtonClick() {
        return this.buttonClick;
    }

    public MediaPlayer getMenuMusic() {
        return this.menuMusic;
    }

    public MediaPlayer getGameMusic() {
        return gameMusic;
    }

    public AudioClip getPhase1BossEgg() {
        return phase1BossEgg;
    }

    public AudioClip getPhase1Death() {
        return phase1Death;
    }

    public AudioClip getPhase2Death() {
        return phase2Death;
    }

    public AudioClip getPhase2Shot() {
        return phase2Shot;
    }
}
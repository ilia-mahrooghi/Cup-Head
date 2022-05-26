package project.models;

public class Game {
    private int difficulty;
    private boolean isDevilMode;

    public Game(int difficulty, boolean isDevilMode) {
        this.difficulty = difficulty;
        this.isDevilMode = isDevilMode;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDevilMode() {
        return isDevilMode;
    }

    public void setDevilMode(boolean devilMode) {
        isDevilMode = devilMode;
    }
}

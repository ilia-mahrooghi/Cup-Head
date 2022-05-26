package project.models;

public class HighScore {
    private int score;
    private long time;

    public HighScore(int score, long time){
        setScore(score);
        setTime(time);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

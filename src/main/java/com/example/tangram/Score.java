package com.example.tangram;

public class Score {
    private int level;
    private int score;

    public Score(int level, int score) {
        this.level = level;
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

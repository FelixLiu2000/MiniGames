package utlities;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String password;
    private float totalScore;
    private float highScore;
    private int roundPlayed;
    private int currentGameRounds;
    private float currentRoundScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public float getHighScore() {
        return highScore;
    }

    public void setHighScore(float highScore) {
        this.highScore = highScore;
    }

    public int getRoundPlayed() {
        return roundPlayed;
    }

    public void setRoundPlayed(int roundPlayed) {
        this.roundPlayed = roundPlayed;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCurrentGameRounds() {
        return currentGameRounds;
    }

    public void setCurrentGameRounds(int currentGameRounds) {
        this.currentGameRounds = currentGameRounds;
    }

    public float getCurrentRoundScore() {
        return currentRoundScore;
    }

    public void setCurrentRoundScore(float currentRoundScore) {
        this.currentRoundScore = currentRoundScore;
    }
}

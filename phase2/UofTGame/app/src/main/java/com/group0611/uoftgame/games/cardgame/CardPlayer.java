package com.group0611.uoftgame.games.cardgame;

public class CardPlayer {
    private int score;
    private int totalScore, totalMisMatches, totalMatches, totalMatchAttempts;
    private String username;

    CardPlayer(){
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    int getScore() {
        return this.score;
    }

    void setScore(int newScore) {
        this.score = newScore;
    }

    int getTotalScore() {
        return totalScore;
    }

    void setTotalScore(int currentScore){ this.totalScore = currentScore;}

    int getTotalMatches() {
        return totalMatches;
    }

    void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    int getTotalMisMatches(){return totalMisMatches;}

    void setTotalMisMatches(int misMatches){ this.totalMisMatches = misMatches;}

    int getTotalMatchAttempts(){return totalMatchAttempts;}

    void setTotalMatchAttempts(int matchAttempts){this.totalMatchAttempts = matchAttempts;}
}

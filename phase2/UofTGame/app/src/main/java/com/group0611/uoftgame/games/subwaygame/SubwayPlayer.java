package com.group0611.uoftgame.games.subwaygame;

public class SubwayPlayer {
//    SubwayGame subwayGame;
    int coins;
    int obstacles;
    int score;

    public SubwayPlayer() {
        this.coins = 0;
        this.obstacles = 0;
        this.score = 10;
    }

    void increaseCoinsCollected() {
        this.coins += 1;
    }

    void increaseObstacleCollisions() {
        this.obstacles += 1;
    }

    void changeTotalScore(int change) {
        this.score += change;
    }
}

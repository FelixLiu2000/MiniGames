package com.example.game.games.subwaygame;

import java.util.ArrayList;

import utilities.Game;

public class SubwayGame extends Game {
    private ArrayList<SubwayObstacle> obstacles = new ArrayList<>();
    private int score;

    SubwayGame() {
        super();
        this.score = 10;

    }

    private void createRunner() {
        SubwayRunner runner = new SubwayRunner();
    }

    private int play() {
        // play until time=0
        // create obstacle every 3 seconds
        createObstacle();
        // check if runner and an obstacle are in the same position every second
        checkCollision();

        return score;
    }

    private void createObstacle() {
        int obstacleLane = pickLane();
        SubwayObstacle newObstacle = new SubwayObstacle(obstacleLane);
        obstacles.add(newObstacle);
    }

    // determine which lane the new obstacle is in
    private int pickLane() {
        int lane;
        double random = Math.random();
        if (random < 0.33) {
            lane = 1;
        } else if (random < 0.66) {
            lane = 2;
        } else lane = 3;

        return lane;
    }

    // check if runner and obstacle are in the same position
    private void checkCollision() {
        // if position of runner and obstacle is the same
            // decrease score
    }

    private void decreaseScore() {
        this.score -= 1;
    }


//    private void moveObstacle() {
//
//    }
//
//    private void moveRunner() {
//        // move right
//
//        // move left
//
//    }
//
//    // check if runner has hit an obstacle
//    private void checkObstacle() {
//        // if runner has same position as obstacle
//            // score -= 1
//
//    }

}

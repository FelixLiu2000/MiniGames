package com.example.game;

public class SubwayRunner {
    private int currentLane;

    SubwayRunner() {
        super();
        this.currentLane = 2;
    }

    private void switchLane(String direction) {
        if (direction.equals("right")) {
            currentLane += 1;
        } else currentLane -= 1;
    }
}

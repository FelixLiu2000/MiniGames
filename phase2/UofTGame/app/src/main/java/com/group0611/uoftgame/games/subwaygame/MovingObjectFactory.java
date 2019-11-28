package com.group0611.uoftgame.games.subwaygame;

import com.group0611.uoftgame.activities.SubwayGameActivity;

class MovingObjectFactory {
    private SubwayGameActivity activity;

    MovingObjectFactory(SubwayGameActivity act){
        this.activity = act;
    }

    /**
     * Randomly creates either a Coin or an Obstacle.
     * @return MovingObject (Coin or Obstacle)
     */
    MovingObject createObj() {
        int rand = pickObj();
        if (rand == 1) {
            return createCoin();
        } else return createObstacle();
    }

    /**
     * Randomly generates either 1 or 2.
     * @return int
     */
    private int pickObj() {
        return (int) (Math.random() * 3);
    }

    /**
     * Creates a new Coin object.
     * @return Coin
     */
    private Coin createCoin(){
        return new Coin(activity);
    }

    /**
     * Creates a new Obstacle object
     * @return Obstacle
     */
    private Obstacle createObstacle(){
        return new Obstacle(activity);
    }
}

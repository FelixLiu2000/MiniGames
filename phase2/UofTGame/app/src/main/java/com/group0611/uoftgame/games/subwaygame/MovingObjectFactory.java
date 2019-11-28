package com.group0611.uoftgame.games.subwaygame;

import com.group0611.uoftgame.activities.SubwayGameActivity;

class MovingObjectFactory {
    private SubwayGameActivity activity;

    MovingObjectFactory(SubwayGameActivity act){
        this.activity = act;
    }

    MovingObject createObj() {
        int movingObj = pickObj();
        if (movingObj == 1) {
            return createCoin();
        } else return createObstacle();
    }

    private int pickObj() {
        return (int) (Math.random() * 3);
    }

    private Coin createCoin(){
        return new Coin(activity);
    }

    private Obstacle createObstacle(){
        return new Obstacle(activity);
    }
}

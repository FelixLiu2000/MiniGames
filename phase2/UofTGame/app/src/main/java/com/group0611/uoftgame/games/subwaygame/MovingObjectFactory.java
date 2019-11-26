package com.group0611.uoftgame.games.subwaygame;

import com.group0611.uoftgame.activities.SubwayGameActivity;

public class MovingObjectFactory {
    SubwayGameActivity activity;

    public Coin createCoin(){
        return new Coin(activity);
    }

    public Obstacle createObstacle(){
        return new Obstacle(activity);
    }
}

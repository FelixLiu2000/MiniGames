package com.group0611.uoftgame.games.subwaygame;

import com.group0611.uoftgame.activities.SubwayGameActivity;

public class MovingObjectFactory {
    SubwayGameActivity activity;


    public MovingObject getMovingObject(String MovingObjectType) {
        if (MovingObjectType == null) {
            return null;
        } else if (MovingObjectType.equalsIgnoreCase("OBSTACLE")) {
            return new Obstacle(activity);
        } else if (MovingObjectType.equalsIgnoreCase("COIN")) {
            return new Coin(activity);
        }
        return null;
    }

}

package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.group0611.uoftgame.activities.SubwayGameActivity;

public abstract class MovingObject extends AppCompatImageView {

    public MovingObject(Context context) {
        super(context);
    }

    abstract  void setImage();

    void setSize(ImageView newMovingObject){
//      Obstacle obj = (Obstacle) newObstacle;
        newMovingObject.getLayoutParams().height = 70;
        newMovingObject.getLayoutParams().width = 70;
    }

    /** Set location of the new obstacle */
    void setPosition(ImageView newMovingObject) {
        // set y variable to 0
        newMovingObject.setY(0);
        // randomly pick a lane
        int obstacleLane = pickLane();
        // set obstacle's x position based on the lane
        if (obstacleLane == 1) {
            newMovingObject.setX(160);
        } else if (obstacleLane == 2) {
            newMovingObject.setX(500);
        } else {
            newMovingObject.setX(860); // lane 3
        }
    }

    /** randomly determine which lane the new obstacle is in */
    int pickLane() {
        return (int) (Math.random() * 4);
    }





    /** change the score by 1 */
    abstract int changeScore() ;


}

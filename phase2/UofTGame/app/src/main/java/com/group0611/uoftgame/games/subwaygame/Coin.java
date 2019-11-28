package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.widget.ImageView;

//import androidx.appcompat.widget.AppCompatImageView;

import com.group0611.uoftgame.R;
//import com.group0611.uoftgame.activities.SubwayGameActivity;

public class Coin extends MovingObject {;

    public Coin(Context context){
        super(context);
    }

//    @Override
    public void setImage() {
        this.setImageResource(R.drawable.coin);

    }

//    /** Set location of the new obstacle */
//    void setPosition() {
//        // set y variable to 0
//        this.setY(0);
//        // randomly pick a lane
//        int obstacleLane = pickLane();
//        // set obstacle's x position based on the lane
//        if (obstacleLane == 1) {
//            this.setX(160);
//        } else if (obstacleLane == 2) {
//            this.setX(500);
//        } else {
//            this.setX(860); // lane 3
//        }
//    }

    void setSize(){
//      Obstacle obj = (Obstacle) newObstacle;
        this.getLayoutParams().height = 100;
        this.getLayoutParams().width = 100;
    }

    /** increase the score by 1 */
    int changeScore() {
        return 1;
    }
}

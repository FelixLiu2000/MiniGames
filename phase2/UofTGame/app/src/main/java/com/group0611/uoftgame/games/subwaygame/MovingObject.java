package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.group0611.uoftgame.activities.SubwayGameActivity;

public abstract class MovingObject extends AppCompatImageView {
    private SubwayGameActivity activity;


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

    /** check if runner and obstacle are in the same position and decrease score by 1 if they are */
    void checkCollision() {
        // loop through obstacles
        for (int i = 0; i < activity.movingObjects.size(); i++) {
            // get position of obstacle
            View obstacle = activity.movingObjects.get(i);
            float obstacleX = obstacle.getX();
            float obstacleY = obstacle.getY();
            // check if runner and obstacle are in the same lane
            boolean sameLane = checkLane(obstacleX);
//      System.out.println("sameLane is " + sameLane);
            // check if runner and obstacle have the same y coordinate
            boolean sameY = checkCoordY(obstacleY);
//      System.out.println("sameY is " + sameY);
            if (sameLane && sameY)
                // decrease score
                changeScore();
            activity.updateScore(score);
        }
    }

    private boolean checkCoordY(float obstacleY) {
        return (obstacleY == 1200);
    }

    private boolean checkLane(float obstacleX) {
        if (activity.runnerLane == 1 && obstacleX == 160) { // if both are in lane 1
            return true;
        } else if (activity.runnerLane == 2 && obstacleX == 500) { // if both are in lane 2
            return true;
        } else if (activity.runnerLane == 3 && obstacleX == 860) { // if both are in lane 3
            return true;
        } else return false;
    }

    /** change the score by 1 */
    abstract void changeScore() ;


}

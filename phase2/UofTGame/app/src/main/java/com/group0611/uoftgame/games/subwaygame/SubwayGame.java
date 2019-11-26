package com.group0611.uoftgame.games.subwaygame;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.activities.SubwayGameActivity;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.utilities.AppManager;

import java.util.ArrayList;
import java.util.List;

public class SubwayGame extends Game {
  private SubwayGameActivity activity;
  private int score;
  public MovingObjectFactory factory;


  public SubwayGame(int timeLimit, AppManager appManager, SubwayGameActivity activity) {
    super(timeLimit, appManager);
    this.score = 10;
    this.activity = activity;
    play();
  }

//  public int getScore() {
//    return this.score;
//  }
//
//  public void setScore(int change) {
//    this.score += change;
//  }

  protected void play() {
    // create 60 second timer
    new CountDownTimer(getAppManager().getCurrentPlayer().getTimeChoice()[0], 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
//        System.out.println("Timer is ticking! " + millisUntilFinished);
        // check for collisions every second
        checkCollision();
        // move all obstacles down every second
        activity.moveDown();
        // create new obstacle every 4 seconds
        double nearestThousand = Math.ceil(millisUntilFinished / 1000) * 1000;
        if (nearestThousand % 4000 == 0) {
          createMovingObject();
//          createObstacle();
        }

        updateTime(millisUntilFinished);
      }

      @Override
      public void onFinish() {
        endGame();
      }
    }.start();
  }
  public void updateTime(long timeLeft){
    ((TextView) activity.findViewById(R.id.timeleft)).setText("Time Left: " + timeLeft/1000);
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
        activity.updateScore(((MovingObject)obstacle).changeScore());
    }
  }
  // helper method to check for the y coordinate of an obstacle
  private boolean checkCoordY(float obstacleY) {
    return (obstacleY == 1200);
  }

  // helper method to check for the lane of the obstacle
  private boolean checkLane(float obstacleX) {
    if (activity.runnerLane == 1 && obstacleX == 160) { // if both are in lane 1
      return true;
    } else if (activity.runnerLane == 2 && obstacleX == 500) { // if both are in lane 2
      return true;
    } else if (activity.runnerLane == 3 && obstacleX == 860) { // if both are in lane 3
      return true;
    } else return false;
  }

  public void createMovingObject() {
    MovingObject obstacle = factory.createObstacle();
    obstacle.setImage();
    createObject(obstacle);

    MovingObject coin = factory.createCoin() ;
    coin.setImage();
    createObject(coin);

  }


  /** create a new obstacle and add it to obstacles ArrayList in SubwayGameActivity */
  private void createObject(MovingObject obj) {
    //    System.out.println("obstacle created");
    //    ImageView newObstacle = new ImageView(activity);
    //    newObstacle.setImageResource(R.drawable.circle_card);
    // set obstacle position

//    if (obj instanceof Obstacle) {
      activity.movingObjects.add(obj);
      ((ConstraintLayout) activity.findViewById(R.id.Layout)).addView(obj);
      obj.setSize(obj);
      obj.setPosition(obj);
//      }

//    else if (obj instanceof Coin) {
//      activity.movingObjects.add(obj);
//      ((ConstraintLayout) activity.findViewById(R.id.Layout)).addView(obj);
//      obj.setSize(obj);
//      setPosition(obj);
//    }

  }

//  private void setSize(ImageView newMovingObject){
////      Obstacle obj = (Obstacle) newObstacle;
//      newMovingObject.getLayoutParams().height = 70;
//      newMovingObject.getLayoutParams().width = 70;
//  }

//  /** Set location of the new obstacle */
//  private void setPosition(ImageView newMovingObject) {
//    // set y variable to 0
//    newMovingObject.setY(0);
//    // randomly pick a lane
//    int obstacleLane = pickLane();
//    // set obstacle's x position based on the lane
//    if (obstacleLane == 1) {
//      newMovingObject.setX(160);
//    } else if (obstacleLane == 2) {
//      newMovingObject.setX(500);
//    } else {
//        newMovingObject.setX(860); // lane 3
//    }
//  }
//
//  /** randomly determine which lane the new obstacle is in */
//  private int pickLane() {
//    return (int) (Math.random() * 4);
//  }

//  /** check if runner and obstacle are in the same position and decrease score by 1 if they are */
//  private void checkCollision() {
//    // loop through obstacles
//    for (int i = 0; i < activity.movingObjects.size(); i++) {
//      // get position of obstacle
//      View obstacle = activity.movingObjects.get(i);
//      float obstacleX = obstacle.getX();
//      float obstacleY = obstacle.getY();
//      // check if runner and obstacle are in the same lane
//      boolean sameLane = checkLane(obstacleX);
////      System.out.println("sameLane is " + sameLane);
//      // check if runner and obstacle have the same y coordinate
//      boolean sameY = checkCoordY(obstacleY);
////      System.out.println("sameY is " + sameY);
//      if (sameLane && sameY)
//        // decrease score
//        changeScore();
//        activity.updateScore(score);
//      }
//    }
//
//    private boolean checkCoordY(float obstacleY) {
//      return (obstacleY == 1200);
//    }
//
//    private boolean checkLane(float obstacleX) {
//    if (activity.runnerLane == 1 && obstacleX == 160) { // if both are in lane 1
//      return true;
//    } else if (activity.runnerLane == 2 && obstacleX == 500) { // if both are in lane 2
//      return true;
//    } else if (activity.runnerLane == 3 && obstacleX == 860) { // if both are in lane 3
//      return true;
//    } else return false;
//  }
//
//    /** decrease the score by 1 */
//  private void changeScore() {
//    if (this.score > 0) {
//      this.score -= 1;
//      System.out.println("Current score is: " + this.score);
//    }
//  }

  @Override
  protected void endGame() {
    System.out.println("Game Over!");
    System.out.println("Final score is: " + score);
    this.getAppManager().getCurrentPlayer().setCurrentGameScore(this.score);
    this.activity.leaveGame(this.getAppManager());
  }

  /** A wrapper method to implement abstract method from Game */
  public void updateGame() {
    play();
  }
}

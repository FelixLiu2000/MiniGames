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
    this.factory = new MovingObjectFactory(activity);
    play();
  }

  protected void play() {
    // create 60 second timer
    new CountDownTimer(getAppManager().getCurrentPlayer().getTimeChoice()[0], 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
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
    displayObject(obstacle);

    MovingObject coin = factory.createCoin() ;
    coin.setImage();
    displayObject(coin);

  }


  /** create a new obstacle and add it to obstacles ArrayList in SubwayGameActivity */
  private void displayObject(MovingObject obj) {
      activity.movingObjects.add(obj);
      ((ConstraintLayout) activity.findViewById(R.id.Layout)).addView(obj);
      obj.setSize();
      obj.setPosition();
  }


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

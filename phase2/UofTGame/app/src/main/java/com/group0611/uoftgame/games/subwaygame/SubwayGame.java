package com.group0611.uoftgame.games.subwaygame;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.activities.GameActivity;
import com.group0611.uoftgame.activities.SubwayGameActivity;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.utilities.AppManager;

public class SubwayGame extends Game {
  private int score;
  private int coins;
  private MovingObjectFactory factory;

  public SubwayGame(GameBuilder gameBuilder) {
    //    super(timeLimit, appManager);
    super(gameBuilder);
    this.score = 10;
    this.coins = 0;
    this.factory = new MovingObjectFactory(getActivity());
    startGame();
  }

  @Override
  protected SubwayGameActivity getActivity() {
    return (SubwayGameActivity) super.getActivity();
  }

  protected void startGame() {
    // create 60 second timer
    new CountDownTimer(getAppManager().getCurrentPlayer().getTimeChoice()[0], 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        // check for collisions every second
        checkCollision();
        // move all obstacles down every second
        getActivity().moveDown();
        // create new obstacle every 4 seconds
        double nearestThousand = Math.ceil(millisUntilFinished / 1000) * 1000;
        if (nearestThousand % 4000 == 0) {
          createMovingObject();
        }
        updateTime(millisUntilFinished);
      }

      @Override
      public void onFinish() {
        endGame();
      }
    }.start();
  }

  private void updateTime(long timeLeft) {
    ((TextView) getActivity().findViewById(R.id.timeleft)).setText("Time Left: " + timeLeft / 1000);
  }

  /** check if runner and obstacle are in the same position and decrease score by 1 if they are */
  private void checkCollision() {
    // loop through obstacles
    for (int i = 0; i < getActivity().movingObjects.size(); i++) {
      // get position of obstacle
      View movingObject = getActivity().movingObjects.get(i);
      float objX = movingObject.getX();
      float objY = movingObject.getY();
      // check if runner and obstacle are in the same lane
      boolean sameLane = checkLane(objX);
      // check if runner and obstacle have the same y coordinate
      boolean sameY = checkCoordY(objY);
      if (sameLane && sameY) {
        // set scoreChange to -1 if an obstacle is hit or 1 if a coin is hit
        int scoreChange = ((MovingObject) movingObject).changeScore();
        updateScore(scoreChange);
        updateCoins(scoreChange);
        getActivity().displayNewScore(score);
      }
    }
  }

  /**
   * Decrease the score if an obstacle is hit and as long as the score is above zero. Increase the
   * score if a coin is hit.
   */
  private void updateScore(int change) {
    // if an obstacle is hit and the score is above 0
    if (change == -1 && score > 0) {
      score += change;
    } else if (change == 1) {
      score += change;
      System.out.println("Score: " + score);
    }
  }

  private void updateCoins(int change) {
    if (change == 1) {
      this.coins += 1;
    }
  }

  // helper method to check for the y coordinate of an obstacle
  private boolean checkCoordY(float obstacleY) {
    return (obstacleY == 1200);
  }

  // helper method to check for the lane of the obstacle
  private boolean checkLane(float obstacleX) {
    if (getActivity().runnerLane == 1 && obstacleX == 160) { // if both are in lane 1
      return true;
    } else // if both are in lane 3
    if (getActivity().runnerLane == 2 && obstacleX == 500) { // if both are in lane 2
      return true;
    } else return getActivity().runnerLane == 3 && obstacleX == 860;
  }

  private void createMovingObject() {
    MovingObject obj = factory.createObj();

    obj.setImage();
    displayObject(obj);
  }

  /** create a new obstacle and add it to obstacles ArrayList in SubwayGameActivity */
  private void displayObject(MovingObject obj) {
    getActivity().movingObjects.add(obj);
    ((ConstraintLayout) getActivity().findViewById(R.id.Layout)).addView(obj);
    obj.setSize();
    obj.setPosition();
  }

  protected int getCurrentPlayerScore() {
    return this.score;
  }

  @Override
  protected void endGame() {
    System.out.println("Game Over!");
    System.out.println("Final score is: " + score);
    getAppManager().getCurrentPlayer().setCurrentGameScore(this.score);
    getActivity().leaveGame(this.getAppManager());
  }

  /** A wrapper method to implement abstract method from Game */
  public void updateGame() {
    startGame();
  }
}

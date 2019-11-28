package com.group0611.uoftgame.games.subwaygame;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.activities.SubwayGameActivity;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.games.LivesGame;
import com.group0611.uoftgame.games.MultiplayerGame;
import com.group0611.uoftgame.games.TimedGame;

public class SubwayGame extends Game implements MultiplayerGame, LivesGame, TimedGame {
  private int score;
  // keeps track of the number of coins collected
  private int coins;
  // creates obstacles and coins
  private MovingObjectFactory factory;

  public SubwayGame(GameBuilder gameBuilder) {
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

  /**
   * Creates a timer and
   * (1) checks for collisions and moves every moving object down every second, and
   * (2) creates a moving object every 4 seconds.
  */
  protected void startGame() {
    // create 120 second timer
    new CountDownTimer(getAppManager().getCurrentPlayer().getTimeChoice()[0], 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        // check for collisions every second
        checkCollision();
        // move all moving objects down every second
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

  /**
   * Check if runner and a moving object are in the same position.
   * Increase score if coin is hit and decrease score if obstacle is hit.
   */
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
   *
   * @param change the amount the score needs to change by; 1 if a coin is hit and -1 if an obstacle
   *               is hit.
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

  /**
   * Increase the number of coins collected by 1.
   *
   * @param change the amount the score needs to change by; 1 if a coin is hit and -1 if an
   * obstacle * is hit.
   */
  private void updateCoins(int change) {
    if (change == 1) {
      this.coins += 1;
    }
  }

  /**
   * Checks the y coordinate of a moving object.
   * @param objY the y coordinate of the moving object.
   * @return boolean true if the y coordinate is 1200, i.e. the object is in the same position as
   * the runner.
   */
  private boolean checkCoordY(float objY) {
    return (objY == 1200);
  }

  /**
   * Compares the lane the moving object is in to the lane the runner is in.
   * @param objX the x coordinate of the moving object.
   * @return boolean true if the moving object and runner are in the same lane.
   */
  private boolean checkLane(float objX) {
    if (getActivity().runnerLane == 1 && objX == 160) { // if both are in lane 1
      return true;
    } else
    if (getActivity().runnerLane == 2 && objX == 500) { // if both are in lane 2
      return true;
    } else return getActivity().runnerLane == 3 && objX == 860; // if both are in lane 3
  }

  /**Creates a moving object by using the moving object factory.*/
  private void createMovingObject() {
    MovingObject obj = factory.createObj();

    obj.setImage();
    displayObject(obj);
  }

  /**
   * Adds the new moving object to the moving object ArrayList in SubwayGameActivity.
   * Displays the object to the screen by setting its size and position.
   * */
  private void displayObject(MovingObject obj) {
    getActivity().movingObjects.add(obj);
    ((ConstraintLayout) getActivity().findViewById(R.id.Layout)).addView(obj);
    obj.setSize();
    obj.setPosition();
  }

  protected int getCurrentPlayerScore() {
    return this.score;
  }

  //Timed Game
  /**
   * Whether the game currently uses a time limit.
   * @return true if the game has a time limit, false otherwise.
   */
  public boolean getUsesTimedGameMode(){
    return super.getUsesTimedGameMode();
    }


  /**
   * The amount of time the game runs for.
   * @return the game's time limit.
   */
  public int getTimeLimit(){
    return 120;
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

  @Override
  public boolean getUsesMultiplayerGameMode() {
    return false;
  }

  @Override
  public int getPlayerScore(int playerNumber) {
    return 0;
  }

  @Override
  public void nextPlayerTurn() {

  }

  @Override
  public int getCurrentPlayerNumber() {
    return 0;
  }
}

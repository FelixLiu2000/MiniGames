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
import com.group0611.uoftgame.utilities.Player;

import java.util.ArrayList;

public class SubwayGame extends Game implements LivesGame, TimedGame, MultiplayerGame {
  // list of players
  private ArrayList<SubwayPlayer> players = new ArrayList<>();

  // creates obstacles and coins
  private MovingObjectFactory factory;

  private CountDownTimer subwayGameTimer;

  private int currentPlayer = 1;


  private int time = 120000;

  public SubwayGame(GameBuilder gameBuilder) {
    super(gameBuilder);
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
    SubwayPlayer playerOne = new SubwayPlayer();
    players.add(playerOne);
    // create 60 second timer
    subwayGameTimer =
        new CountDownTimer(getTimeLimit(), 1000) {
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
            if (getCurrentPlayerScore() == 0 && getUsesLivesGameMode() && !getUsesMultiplayerGameMode()) {
              endGame();
            }
          }

          @Override
          public void onFinish() {
            if (getUsesTimedGameMode()) {
              endGame();
            } else if (getUsesMultiplayerGameMode()) {
              nextPlayerTurn();
              this.start();
            } else {
              this.start();
            }
          }
        }.start();
  }

  /**
   * Updates the time remaining display at the top of the screen
   * @param timeLeft the time remaining in the game
   */
  private void updateTime(long timeLeft) {
    if(!getUsesLivesGameMode()){
      ((TextView) getActivity().findViewById(R.id.timeleft)).setText("Time Left: " + timeLeft / 1000);
    } else {
      ((TextView) getActivity().findViewById(R.id.timeleft)).setText("Lives Left: " + timeLeft / 1000);
    }

  }

  /**
   * Checks if runner and a moving object are in the same position.
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
        SubwayPlayer currentPlayer = getCurrPlayer();
        // set scoreChange to -1 if an obstacle is hit or 1 if a coin is hit
        int scoreChange = ((MovingObject) movingObject).changeScore();
        // update the stats of the current player
        updateScore(scoreChange, currentPlayer);
        updateCoins(scoreChange, currentPlayer);
        updateObstacles(scoreChange, currentPlayer);
        // updates the sore displayed at the top of the screen
        getActivity().displayNewScore(getCurrentPlayerScore());
      }
    }
  }

  /**
   * Returns of the current player
   * @return the current player of the game
   */
  private SubwayPlayer getCurrPlayer() {
    return players.get(currentPlayer-1);
  }

  /**
   * Decrease the score if an obstacle is hit and as long as the score is above zero. Increase the
   * score if a coin is hit.
   *
   * @param change the amount the score needs to change by; 1 if a coin is hit and -1 if an obstacle
   *               is hit.
   */
  private void updateScore(int change, SubwayPlayer currentPlayer) {
    // if an obstacle is hit and the score is above 0, decrease score by 1
    if (change == -1 && currentPlayer.score > 0) {
      currentPlayer.changeTotalScore(change);
      // if a coin is hit, increase score by 1
    } else if (change == 1) {
      currentPlayer.changeTotalScore(change);
    }
  }

  /**
   * Increase the number of coins collected by 1.
   *
   * @param change the amount the score needs to change by; 1 if a coin is collected and -1 if an
   * obstacle is hit.
   */
  private void updateCoins(int change, SubwayPlayer currentPlayer) {
    if (change == 1) {
      currentPlayer.increaseCoinsCollected();
    }
  }

  /**
   * Decrease the number of coins collected by 1.
   *
   * @param change the amount the score needs to change by; 1 if a coin is collected and -1 if an
   * obstacle is hit.
   */
  private void updateObstacles(int change, SubwayPlayer currentPlayer) {
    if (change == -1) {
      currentPlayer.increaseObstacleCollisions();
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

  /**Creates a moving object by calling the moving object factory.*/
  private void createMovingObject() {
    MovingObject obj = factory.createObj();

    obj.setImage();
    displayObject(obj);
  }

  /**
   * Adds the new moving object to the moving object ArrayList in SubwayGameActivity.
   * Displays the object to the screen by setting its size and position.
   * @param obj the moving object (Obstacle or Coin) being displayed to the screen
   */
  private void displayObject(MovingObject obj) {
    getActivity().movingObjects.add(obj);
    ((ConstraintLayout) getActivity().findViewById(R.id.Layout)).addView(obj);
    obj.setSize();
    obj.setPosition();
  }

  /**
   * Returns the score of the current player
   * @return score of the current player
   */
  protected int getCurrentPlayerScore() {
    return getCurrPlayer().score;
  }

  /**
   * Returns the Subway Game timer
   * @return the Subway Game timer
   */
  public CountDownTimer getSubwayGameTimer() { return this.subwayGameTimer; }

  /**
   * Returns whether the game currently uses a time limit.
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
      return super.getTimeLimit();
  }

  @Override
  protected void endGame() {
    System.out.println("Game Over!");
    System.out.println("Final score is: " + getCurrentPlayerScore());
//     getAppManager().getCurrentPlayer().setCurrentGameScore(this.score);

    // get all player score statistics
    int totalScore = getCurrentPlayerScore();
    int totalCoins = getCurrPlayer().coins;
    int totalObstacles = getCurrPlayer().obstacles;
    Player playerOne = getAppManager().getPlayerOne();
    getAppManager().updatePlayerSubwayGameStats(playerOne, totalScore, totalCoins,  totalObstacles);

    // if in multiplayer mode, get player 2 statistics
    if (getUsesMultiplayerGameMode()) {
      SubwayPlayer otherPlayer = players.get(currentPlayer);
      int playerTwoScore = otherPlayer.score;
      int playerTwoCoins = otherPlayer.coins;
      int playerTwoObstacles = otherPlayer.obstacles;
      Player playerTwo = getAppManager().getPlayerTwo();
      getAppManager().updatePlayerSubwayGameStats(playerTwo, playerTwoScore, playerTwoCoins,  playerTwoObstacles);
      // determine if player 1 is the winner
      Boolean playerOneWon = getPlayerOneWon();
      this.getAppManager().updateTwoPlayerStats(playerOneWon);
    }
    getActivity().leaveGame(this.getAppManager());
  }

  /**
   * Determines whether player 1 finished the game with a higher score than player 2
   * @return true if player 1 had a higher score, false otherwise
   */
  private Boolean getPlayerOneWon() {
    SubwayPlayer playerOne = players.get(0);
    SubwayPlayer playerTwo = players.get(1);
    return playerOne.score > playerTwo.score;
  }

  /** A wrapper method to implement abstract method from Game */
  public void updateGame() {
    startGame();
  }

  @Override
  public boolean getUsesLivesGameMode() { return super.getUsesLivesGameMode(); }

  @Override
  public int getStartingLives() {
    return super.getStartingLives();
    }

  @Override
  public boolean isOutOfLives() { return getCurrentPlayerScore() <= 0; }

  @Override
  public boolean getUsesMultiplayerGameMode() { return super.getUsesMultiplayerGameMode(); }

  @Override
  public int getPlayerScore(int playerNumber) {
    SubwayPlayer currPlayer = players.get(playerNumber-1);
    return currPlayer.score;
  }

  @Override
  public void nextPlayerTurn() {
    // remove the old obstacles
    ((ConstraintLayout) getActivity().findViewById(R.id.Layout)).removeAllViews();
    // reset the player labels/stats
    currentPlayer ++;
    SubwayPlayer newPlayer = new SubwayPlayer();
    players.add(newPlayer);
    getActivity().displayNewScore(newPlayer.score);
    ((TextView) getActivity().findViewById(R.id.playercounter)).setText("Player " + getCurrentPlayerNumber());
    startGame();
  }

  @Override
  public int getCurrentPlayerNumber() {
    return currentPlayer;
  }


}

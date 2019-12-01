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

import java.util.ArrayList;
import java.util.List;

public class SubwayGame extends Game implements LivesGame, TimedGame, MultiplayerGame {
  private int score;
  // keeps track of the number of coins collected
  private int coins;
  // creates obstacles and coins
  private MovingObjectFactory factory;
  private CountDownTimer subwayGameTimer;

  private int currentPlayer = 1;
  private List<Integer> playerScores = new ArrayList<>();
  private ArrayList<SubwayPlayer> players = new ArrayList<>();


  private int time = getAppManager().getCurrentPlayer().getTimeChoice()[0];

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
//
//  @Override
//  public boolean getUsesLivesGameMode() { return super.getUsesLivesGameMode(); }
//
//  @Override
//  public int getStartingLives() {
//    return super.getStartingLives();
////    return score;
//  }
//
//  @Override
//  public boolean isOutOfLives() { return score <= 0; }
//
//  @Override
//  public boolean getUsesMultiplayerGameMode() { return super.getUsesMultiplayerGameMode(); }
//
//  @Override
//  public int getPlayerScore(int playerNumber) { return playerScores.get(playerNumber-1); }
//
//  @Override
//  public void nextPlayerTurn() {
//    currentPlayer++;
//    playerScores.add(score);
//    score = 0;
//    ((TextView) getActivity().findViewById(R.id.playercounter)).setText("Player " + getCurrentPlayerNumber());
//    startGame();
//  }
//
//  @Override
//  public int getCurrentPlayerNumber() {
//    return currentPlayer;
//  }

  /**
   * Creates a timer and
   * (1) checks for collisions and moves every moving object down every second, and
   * (2) creates a moving object every 4 seconds.
   */
  protected void startGame() {
    // create 60 second timer
    subwayGameTimer = new CountDownTimer(time, 1000) {
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
        if(score == 0 && getUsesLivesGameMode()){
          endGame();
        }
      }

      @Override
      public void onFinish() {
        if(!getUsesLivesGameMode()){
          endGame();
        }else{
          this.start();
        }
      }
    }.start();
  }

  private void updateTime(long timeLeft) {
    if(!getUsesLivesGameMode()){
      ((TextView) getActivity().findViewById(R.id.timeleft)).setText("Time Left: " + timeLeft / 1000);
    } else {
      ((TextView) getActivity().findViewById(R.id.timeleft)).setText("Lives Left: " + timeLeft / 1000);
    }

  }

  /**
   * Gets the player with a given player number.
   *
   * @param playerNumber the number of the player (indexed at 1).
   */
  SubwayPlayer getPlayer(int playerNumber) {
    if (playerNumber == 1 || (getUsesMultiplayerGameMode() && playerNumber <= players.size())) {
      return players.get(playerNumber - 1);
    } else {
      throw new IllegalArgumentException(
              "Illegal argument: player with number " + playerNumber + " not found.");
    }
  }

  /**
   * Sets the game's player(s) and initializes their starting lives and usernames.
   *
   * @param players the players to be assigned.
   */
  void setPlayers(SubwayPlayer[] players) {
    for (SubwayPlayer player : players) {
      player.setRemainingLives(this.getStartingLives());
      player.setUsername(getAppManager().getCurrentPlayerDisplayName());
      this.players.add(player);
    }
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
  public CountDownTimer getSubwayGameTimer() { return this.subwayGameTimer; }

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
  public boolean getUsesLivesGameMode() { return super.getUsesLivesGameMode(); }

  @Override
  public int getStartingLives() {
    return super.getStartingLives();
//    return score;
    }

  @Override
  public boolean isOutOfLives() { return score <= 0; }

  @Override
  public boolean getUsesMultiplayerGameMode() { return super.getUsesMultiplayerGameMode(); }

  @Override
  public int getPlayerScore(int playerNumber) { return playerScores.get(playerNumber-1); }

  @Override
  public void nextPlayerTurn() {
    currentPlayer++;
    playerScores.add(score);
    score = 0;
    ((TextView) getActivity().findViewById(R.id.playercounter)).setText("Player " + getCurrentPlayerNumber());
    startGame();
  }

  @Override
  public int getCurrentPlayerNumber() {
    return currentPlayer;
  }


}

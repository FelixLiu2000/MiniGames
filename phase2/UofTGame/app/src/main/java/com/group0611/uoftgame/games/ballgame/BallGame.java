package com.group0611.uoftgame.games.ballgame;

import android.graphics.Rect;

import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.games.LivesGame;
import com.group0611.uoftgame.games.MultiplayerGame;
import com.group0611.uoftgame.games.TimedGame;

import java.util.ArrayList;

public class BallGame extends Game implements LivesGame, TimedGame, MultiplayerGame {

  private ArrayList<Ball> activeBallObjects = new ArrayList<>();
  private ArrayList<Integer> inactiveBallIndices = new ArrayList<>();
  private Player[] players;
  private int currentPlayerNumber = 1;
  private Target target;
  private Rect activeArea;
  private int timeRemaining;
  private CollisionManager collisionManager = new CollisionManager();
  private boolean hasLivesGameMode, hasMultiplayerGameMode, hasTimedGameMode;
  private int lives, timeLimit;

  public BallGame(GameBuilder builder) {
    super(builder);
    this.hasLivesGameMode = builder.getHasLives();
    this.hasMultiplayerGameMode = builder.getHasMultiplayer();
    this.hasTimedGameMode = builder.getHasTimed();
    setStartingLives(builder.getLives());
    setTimeLimit(builder.getTimeLimit());
  }

  @Override
  public boolean hasLivesGameMode() {
    return hasLivesGameMode;
  }

  @Override
  public boolean hasMultiplayerGameMode() {
    return hasMultiplayerGameMode;
  }

  @Override
  public boolean hasTimedGameMode() {
    return hasTimedGameMode;
  }

  @Override
  public int getStartingLives() {
    return lives;
  }

  @Override
  public void setStartingLives(int lives) {
    if (lives >= 0) {
      this.lives = lives;
    }
  }

  @Override
  public boolean isOutOfLives() {
    return getPlayer(currentPlayerNumber).getLives() == 0;
  }

  @Override
  public int getTimeLimit() {
    return timeLimit;
  }

  @Override
  public void setTimeLimit(int timeLimit) {
    if (timeLimit >= 0) {
      this.timeLimit = timeLimit;
    }
  }

  @Override
  public int getPlayer1Score() {
    return this.getPlayer(1).getScore();
  }

  @Override
  public void setPlayer1Score(int score) {
    this.getPlayer(1).setScore(score);
  }

  @Override
  public int getPlayer2Score() {
    return this.getPlayer(2).getScore();
  }

  @Override
  public void setPlayer2Score(int score) {
    this.getPlayer(2).setScore(score);
  }

  ArrayList<Ball> getActiveBallObjects() {
    return activeBallObjects;
  }

  ArrayList<Integer> getInactiveBallIndices() {
    return inactiveBallIndices;
  }

  int getTimeRemaining() {
    return timeRemaining;
  }

  void setTimeRemaining(int time) {
    this.timeRemaining = time;
  }

  @Override
  public void startGame() {
    setTimeRemaining(getTimeLimit());
  }

  Player getPlayer(int playerNumber) {
    if (playerNumber == 1 || (hasMultiplayerGameMode && playerNumber == 2)) {
      return players[playerNumber - 1];
    } else {
      throw new IllegalArgumentException("Illegal argument: player number must be 1 or 2.");
    }
  }

  void setPlayers(Player[] players) {
    for (Player player : players) {
      player.setLives(getStartingLives());
      player.setUsername(getAppManager().getCurrentPlayerDisplayName());
    }
    this.players = players;
  }

  int getCurrentPlayerNumber() {
    return currentPlayerNumber;
  }

  void setTarget(Target target) {
    /*
    target =
        new Target(
            view.getLeft(),
            view.getTop(),
            view.getLayoutParams().width,
            view.getLayoutParams().height);
    target.setObjectView(view);
     */
    this.target = target;
  }

  void setActiveArea(int x, int y, int right, int bottom) {
    this.activeArea = new Rect(x, y, right, bottom);
  }

  @Override
  public void nextPlayerTurn() {
    // Switch from one player number to the next
    if (currentPlayerNumber == 1) {
      currentPlayerNumber = 2;
    } else {
      currentPlayerNumber = 1;
    }
    setTimeRemaining(getTimeLimit());
  }

  Ball shootBall(int width, int height) {
    /*
    view.setX(player.getX());
    view.setY(player.getY());
    Ball newBall = player.shootBall(view.getLayoutParams().width, view.getLayoutParams().height);
    newBall.setObjectView(view);
    activeBallObjects.add(newBall);
    */
    Ball newBall = getPlayer(currentPlayerNumber).shootBall(width, height);
    activeBallObjects.add(newBall);
    return newBall;
  }

  void setShotAngle(int angle) {
    getPlayer(currentPlayerNumber).setShotAngle(angle);
    // updateShotAngleText(angle);
  }

  void setShotPower(int power) {
    getPlayer(currentPlayerNumber).setShotPower(power);
    // updateShotPowerText(power);
  }

  /*  private void updateShotAngleText(int angle) {
    String newAngle = "Angle: " + angle;
    this.angleView.setText(newAngle);
  }

  private void updateShotPowerText(int power) {
    String newPower = "Power: " + power;
    this.powerView.setText(newPower);
  }

  private void updateTimeText(int time) {
    String newTime = "Time: " + time;
    this.timeView.setText(newTime);
  }*/

  void updateMovements() {
    for (Ball object : activeBallObjects) {
      object.update();
      System.out.println("BALL UPDATED");
    }
  }

  void updateCollisions() {
    ArrayList<Ball> invalidTargetBallCollisions =
        collisionManager.checkOffTargetBallCollisions(
            activeBallObjects,
            target,
            activeArea.left,
            activeArea.top,
            activeArea.right,
            activeArea.bottom);
    if (!invalidTargetBallCollisions.isEmpty()) {
      for (Ball ball : invalidTargetBallCollisions) {
        targetMissed(ball);
      }
    }
    ArrayList<Ball> validTargetBallCollisions =
        collisionManager.checkOnTargetBallCollisions(activeBallObjects, target);
    if (!validTargetBallCollisions.isEmpty()) {
      for (Ball ball : validTargetBallCollisions) {
        targetHit(ball);
      }
    }
  }

  private void targetHit(Ball ball) {
    ball.onCollide(target);
    // Update player score and score TextView
    final int SCORE_PER_HIT = 5;
    final int BONUS_POINTS_PER_LIFE = 5;
    // Award player bonus points for hit and every life remaining and set lives to 0
    Player currentPlayer = getPlayer(currentPlayerNumber);
    currentPlayer.setScore(
        currentPlayer.getScore() + SCORE_PER_HIT + currentPlayer.getLives() * BONUS_POINTS_PER_LIFE);
    currentPlayer.setLives(0);
    destroyBall(ball);
  }

  private void targetMissed(Ball ball) {
    if (hasLivesGameMode) {
      getPlayer(currentPlayerNumber).setLives(getPlayer(currentPlayerNumber).getLives() - 1);
    }
    destroyBall(ball);
  }

  private void destroyBall(Ball ball) {
    // Destroy ball, remove object from active ball list and add to inactive for destruction
    inactiveBallIndices.add(activeBallObjects.indexOf(ball));
    activeBallObjects.remove(ball);
  }

  @Override
  protected void endGame() {
    System.out.println("Game ended");
    // TODO: Below is temporary, should add score functionality for multiple players
    int topScore = getPlayer1Score();
    if (hasMultiplayerGameMode && topScore < getPlayer2Score()) {
      topScore = getPlayer2Score();
    }
    this.getAppManager().getCurrentPlayer().setCurrentGameScore(topScore);
    getActivity().leaveGame(this.getAppManager());
  }
}

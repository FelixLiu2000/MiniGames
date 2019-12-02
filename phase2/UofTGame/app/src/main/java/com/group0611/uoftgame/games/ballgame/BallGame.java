package com.group0611.uoftgame.games.ballgame;

import android.graphics.Rect;

import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.games.LivesGame;
import com.group0611.uoftgame.games.MultiplayerGame;
import com.group0611.uoftgame.games.TimedGame;

import java.util.ArrayList;

public class BallGame extends Game implements LivesGame, TimedGame, MultiplayerGame {

  private ArrayList<Ball> activeBallObjects = new ArrayList<>();
  // Stores index of ball objects to be removed from activeBallObjects
  private ArrayList<Integer> inactiveBallIndices = new ArrayList<>();
  private ArrayList<Player> players = new ArrayList<>();
  // Number of the current player (indexed at 1).
  private int currentPlayerNumber = 1;
  private Target target;
  // Active playing area that ball movement is restricted to
  private Rect activeArea;
  private int timeRemaining;
  // Manages ball collisions
  private CollisionManager collisionManager = new CollisionManager();

  public BallGame(GameBuilder builder) {
    super(builder);
  }

  @Override
  public boolean getUsesLivesGameMode() {
    return super.getUsesLivesGameMode();
  }

  @Override
  public boolean getUsesTimedGameMode() {
    return super.getUsesTimedGameMode();
  }

  @Override
  public boolean getUsesMultiplayerGameMode() {
    return super.getUsesMultiplayerGameMode();
  }

  @Override
  public int getStartingLives() {
    return super.getStartingLives();
  }

  @Override
  public boolean isOutOfLives() {
    return getPlayer(currentPlayerNumber).getRemainingLives() == 0;
  }

  @Override
  public int getTimeLimit() {
    return super.getTimeLimit();
  }

  @Override
  public int getPlayerScore(int playerNumber) {
    return getPlayer(playerNumber).getScore();
  }

  private void setPlayerScore(int playerNumber, int newScore) {
    getPlayer(playerNumber).setScore(newScore);
  }

  @Override
  protected int getCurrentPlayerScore() {
    return getPlayerScore(getCurrentPlayerNumber());
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

  /**
   * Gets the player with a given player number.
   *
   * @param playerNumber the number of the player (indexed at 1).
   */
  Player getPlayer(int playerNumber) {
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
  void setPlayers(Player[] players) {
    for (Player player : players) {
      player.setRemainingLives(this.getStartingLives());
      player.setUsername(getAppManager().getCurrentPlayer().getUsername());
      this.players.add(player);
    }
  }

  /**
   * Returns the player number of the player active during the current turn.
   *
   * @return the number of the current player (indexed at 1).
   */
  @Override
  public int getCurrentPlayerNumber() {
    return currentPlayerNumber;
  }

  /**
   * Sets the game's target.
   *
   * @param target target to be assigned.
   */
  void setTarget(Target target) {
    this.target = target;
  }

  /**
   * Defines the active rectangular area that ball game object movement is restricted to.
   *
   * @param left x-coordinate of the left-side of the rectangle.
   * @param top y-coordinate of the top-side of the rectangle.
   * @param right x-coordinate of the right-side of the rectangle.
   * @param bottom y-coordinate of the bottom-side of the rectangle.
   */
  void setActiveArea(int left, int top, int right, int bottom) {
    this.activeArea = new Rect(left, top, right, bottom);
  }

  /** Starts the next player's turn and resets the game's time limit. */
  @Override
  public void nextPlayerTurn() {
    // Convert leftover lives to bonus points for current player, then set lives to 0
    int bonusPoints =
        getPlayer(currentPlayerNumber).getRemainingLives() * GameConstants.BONUS_POINTS_PER_LIFE;
    setPlayerScore(currentPlayerNumber, getCurrentPlayerScore() + bonusPoints);
    getPlayer(currentPlayerNumber).setRemainingLives(0);
    // Switch from one player number to the next
    if (currentPlayerNumber == 1) {
      currentPlayerNumber = 2;
    } else {
      currentPlayerNumber = 1;
    }
    // Reset time remaining for next player
    setTimeRemaining(getTimeLimit());
  }

  /**
   * Causes the current player to shoot a ball with a given size.
   *
   * @param width the width of the ball.
   * @param height the height of the ball.
   * @return the ball being shot.
   */
  Ball shootBall(int width, int height) {
    /*
    view.setX(player.getX());
    view.setY(player.getY());
    Ball newBall = player.shootBall(view.getLayoutParams().width, view.getLayoutParams().height);
    newBall.setObjectView(view);
    activeBallObjects.add(newBall);
    */
    // Trigger ball shot
    Ball newBall = getPlayer(currentPlayerNumber).shootBall(width, height);
    activeBallObjects.add(newBall);
    return newBall;
  }

  /**
   * Sets the shot angle used by the current player.
   *
   * @param angle elevation of the shot's initial velocity vector.
   */
  void setShotAngle(int angle) {
    getPlayer(currentPlayerNumber).setShotAngle(angle);
  }

  /**
   * Sets the power used by the current player.
   *
   * @param power magnitude of the shot's initial velocity vector.
   */
  void setShotPower(int power) {
    getPlayer(currentPlayerNumber).setShotPower(power);
  }

  /** Updates the position of this game's ball objects. */
  void updateMovements() {
    for (Ball object : activeBallObjects) {
      object.update();
      System.out.println("BALL UPDATED");
    }
  }

  /** Updates the collisions between this game's Collidable objects. */
  void updateCollisions() {
    // Gets the balls that have collided with the active area boundaries or the side of the target.
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
    // Gets the balls that have successfully hit the target.
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
    // Award player points for hit
    setPlayerScore(getCurrentPlayerNumber(), getCurrentPlayerScore() + GameConstants.SCORE_PER_HIT);
    // Update total hits count
    getPlayer(currentPlayerNumber).setTotalHits(getPlayer(currentPlayerNumber).getTotalHits() + 1);
    destroyBall(ball);
  }

  private void targetMissed(Ball ball) {
    if (getUsesLivesGameMode()) {
      getPlayer(currentPlayerNumber)
          .setRemainingLives(getPlayer(currentPlayerNumber).getRemainingLives() - 1);
    }
    // Update total miss count
    getPlayer(currentPlayerNumber)
        .setTotalMisses(getPlayer(currentPlayerNumber).getTotalMisses() + 1);
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
    Player playerOne = getPlayer(1);
    // Give app manager player one game stats
    this.getAppManager()
        .updatePlayerBallGameStats(
            this.getAppManager().getPlayerOne(),
            playerOne.getScore(),
            playerOne.getTotalThrows(),
            playerOne.getTotalHits(),
            playerOne.getTotalMisses());
    // Give app manager player two game stats
    if (getUsesMultiplayerGameMode()) {
      Player playerTwo = getPlayer(2);
      this.getAppManager()
          .updatePlayerBallGameStats(
              this.getAppManager().getPlayerTwo(),
              playerTwo.getScore(),
              playerTwo.getTotalThrows(),
              playerTwo.getTotalHits(),
              playerTwo.getTotalMisses());
      // Notify app manager of whether player one beat player two
      this.getAppManager().updateTwoPlayerStats(playerOne.getScore() > playerTwo.getScore());
    }

    getActivity().leaveGame(this.getAppManager());
  }
}

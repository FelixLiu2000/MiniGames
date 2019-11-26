package com.group0611.uoftgame.games.ballgame;

import android.graphics.Rect;

import com.group0611.uoftgame.games.Game;
import java.util.ArrayList;

public class BallGame extends Game {

  private ArrayList<Ball> activeBallObjects = new ArrayList<>();
  private ArrayList<Integer> inactiveBallIndices = new ArrayList<>();
  private Player player;
  private Target target;
  private Rect activeArea;
  private int timeRemaining;
  private CollisionManager collisionManager = new CollisionManager();

  public BallGame(GameBuilder builder) {
    super(builder);
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

  Player getPlayer() {
    return player;
  }

  void setPlayer(Player player) {
    this.player = player;
  }

  public void setTarget(Target target) {
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

  /* @Deprecated
   * Main game loop. Loop from https://dewitters.com/dewitters-gameloop/ private void gameLoop() {
   * final double FRAME_TIME = 1000 / 60; final int MAX_FRAMES_SKIPPED = 5; Timer gameTimer = new
   * Timer(getTimeLimit() * 1000); double nextTick = SystemClock.uptimeMillis(); while
   * (!gameTimer.isStopped()) { int frames = 0; while (SystemClock.uptimeMillis() > nextTick &&
   * frames < MAX_FRAMES_SKIPPED) { updateMovements(); nextTick += FRAME_TIME; frames++; }
   *
   * <p>renderGame((float)((SystemClock.uptimeMillis() + FRAME_TIME - nextTick) / FRAME_TIME)); } }
   */

  Ball shootBall(int width, int height) {
    /*
    view.setX(player.getX());
    view.setY(player.getY());
    Ball newBall = player.shootBall(view.getLayoutParams().width, view.getLayoutParams().height);
    newBall.setObjectView(view);
    activeBallObjects.add(newBall);
    */
    Ball newBall = player.shootBall(width, height);
    activeBallObjects.add(newBall);
    return newBall;
  }

  void setShotAngle(int angle) {
    this.player.setShotAngle(angle);
    // updateShotAngleText(angle);
  }

  void setShotPower(int power) {
    this.player.setShotPower(power);
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
        destroyBall(ball);
      }
    }
    ArrayList<Ball> validTargetBallCollisions =
        collisionManager.checkOnTargetBallCollisions(activeBallObjects, target);
    if (!validTargetBallCollisions.isEmpty()) {
      for (Ball ball : validTargetBallCollisions) {
        targetHit(ball);
        destroyBall(ball);
      }
    }
  }

  private void targetHit(Ball ball) {
    ball.onCollide(target);
    // Update player score and score TextView
    final int SCORE_PER_HIT = 5;
    setScore(getScore() + SCORE_PER_HIT);
  }

  private void destroyBall(Ball ball) {
    // Destroy ball, remove object from active ball list and add to inactive for destruction
    inactiveBallIndices.add(activeBallObjects.indexOf(ball));
    activeBallObjects.remove(ball);
  }

  @Override
  protected void endGame() {
    System.out.println("Game ended");
    this.getAppManager().getCurrentPlayer().setCurrentGameScore(this.player.getScore());
    getActivity().leaveGame(this.getAppManager());
  }
}

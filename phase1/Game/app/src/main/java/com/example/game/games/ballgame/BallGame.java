package com.example.game.games.ballgame;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;

import com.example.game.games.Game;
import com.example.game.utilities.AppManager;

import java.util.ArrayList;

public class BallGame extends Game {

  private ArrayList<Ball> ballObjects = new ArrayList<>();
  private Player player;
  private Target target;
  private LinearLayout ballLayout;
  private TextView scoreView, timeView, powerView, angleView;

  static final double GRAVITY = 0.1;
  public static final int SHOT_STARTING_POWER = 2;
  public static final int SHOT_STARTING_ANGLE = 0;
  public static final int SHOT_MAX_POWER = 20;
  public static final int SHOT_MAX_ANGLE = 90;

  public BallGame(int timeLimit, AppManager appManager) {
    super(timeLimit, appManager);
  }

  @Override
  public int play() {
    gameLoop();
    return player.getScore();
  }

  public void initializePlayer(View view) {
    player = new Player(view.getLeft(), view.getTop());
  }

  public void initializeTarget(View view) {
    target =
        new Target(
            view.getLeft(),
            view.getTop(),
            view.getLayoutParams().width,
            view.getLayoutParams().height);
    target.setObjectView(view);
  }

  public void initializeLayouts(LinearLayout layout) {
    this.ballLayout = layout;
  }

  public void initializeOutputViews(TextView score, TextView time, TextView power, TextView angle) {
    this.scoreView = score;
    this.timeView = time;
    this.powerView = power;
    this.angleView = angle;
    updateShotPowerText(SHOT_STARTING_POWER);
    updateShotAngleText(SHOT_STARTING_ANGLE);
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

  private void gameLoop() {
    final int FPS = 60;
    final long TIMER_REFRESH = 1000 / FPS;
    CountDownTimer gameTimer =
        new CountDownTimer(getTimeLimit() * 1000, TIMER_REFRESH) {
          private int currentTimeRemaining = getTimeLimit();
          @Override
          public void onTick(long l) {
            updateMovements();
            updateCollisions();
            if ((int)(Math.ceil(l/ 1000)) < currentTimeRemaining) {
              currentTimeRemaining--;
              updateTimeText(currentTimeRemaining);
            }
          }

          @Override
          public void onFinish() {
            endGame();
          }
        }.start();
  }

  public void shootBall(View view) {
    view.setX(player.getX());
    view.setY(player.getY());
    Ball newBall = player.shootBall(view.getLayoutParams().width, view.getLayoutParams().height);
    newBall.setObjectView(view);
    ballObjects.add(newBall);
  }

  public void setShotAngle(int angle) {
    this.player.setShotAngle(angle);
    updateShotAngleText(angle);
  }

  public void setShotPower(int power) {
    this.player.setShotPower(power);
    updateShotPowerText(power);
  }

  private void updateShotAngleText(int angle) {
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
  }

  private void updateMovements() {
    for (Ball object : ballObjects) {
      object.update();
      System.out.println("BALL UPDATED");
    }
  }

  private void updateCollisions() {
    ArrayList<Ball> collidedBalls = CollisionManager.checkTargetBallCollisions(ballObjects, target);
    if (!collidedBalls.isEmpty()) {
      for (Ball ball : collidedBalls) {
        targetHit(ball);
        destroyBall(ball);
      }
    }
    ArrayList<Ball> escapedBalls =
        CollisionManager.checkScreenBallCollisions(
            ballObjects,
            ballLayout.getLeft(),
            ballLayout.getTop(),
            ballLayout.getRight(),
            ballLayout.getBottom());
    if (!escapedBalls.isEmpty()) {
      for (Ball ball : escapedBalls) {
        destroyBall(ball);
      }
    }
  }

  private void targetHit(Ball ball) {
    ball.onCollide(target);
    // Update player score and score TextView
    final int SCORE_PER_HIT = 5;
    String newScore = "Score: " + player.setScore(player.getScore() + SCORE_PER_HIT);
    scoreView.setText(newScore);
  }

  private void destroyBall(Ball ball) {
    // Destroy ball, remove view from parent layout and object from game ArrayList
    ballLayout.removeView(ball.getView());
    ballObjects.remove(ball);
  }

  @Override
  protected void endGame() {
    System.out.println("Game ended");
  }
}

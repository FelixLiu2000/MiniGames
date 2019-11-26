package com.group0611.uoftgame.games.ballgame;

import android.os.CountDownTimer;
import android.view.View;

import com.group0611.uoftgame.activities.BallGameActivity;

import java.util.Timer;
import java.util.TimerTask;

public class BallGamePresenter {
  private BallGame game;
  private BallGameActivity activity;

  public BallGamePresenter(BallGame game) {
    this.game = game;
  }

  public void bindActivity(BallGameActivity activity) {
    this.activity = activity;
  }

  public void unbindActivity() {
    this.activity = null;
  }

  public void initializePlayer(View view) {
    Player player = new Player(view.getX(), view.getY());
    game.setPlayer(player);
  }

  public void initializeTarget(View view) {
    Target target =
        new Target(
            view.getX(), view.getY(), view.getLayoutParams().width, view.getLayoutParams().height);
    game.setTarget(target);
  }

  public void initializeActiveArea(int left, int top, int right, int bottom) {
    game.setActiveArea(left, top, right, bottom);
  }

  public void initializeOutputViews() {
    renderPower();
    renderAngle();
    renderTime();
    renderScore();
  }

  public void initializeNewBall(View view) {
    Ball newBall = game.shootBall(view.getLayoutParams().width, view.getLayoutParams().height);
    activity.updateViewLocation(view, newBall.getX(), newBall.getY());
  }

  public void updateShotAngle(int angle) {
    game.setShotAngle(angle);
    renderAngle();
  }

  public void updateShotPower(int power) {
    game.setShotPower(power);
    renderPower();
  }

  private void renderScore() {
    activity.updateTextView(activity.getScoreView(), "Score: " + game.getScore());
  }

  private void renderAngle() {
    activity.updateTextView(activity.getAngleView(), "Angle: " + game.getPlayer().getShotAngle());
  }

  private void renderPower() {
    activity.updateTextView(activity.getPowerView(), "Power: " + game.getPlayer().getShotPower());
  }

  private void renderTime() {
    activity.updateTextView(activity.getTimeView(), "Time: " + game.getTimeRemaining());
  }

  private void renderBallObjects() {
    for (int i = 0; i < game.getActiveBallObjects().size(); i++) {
      Ball currentBall = game.getActiveBallObjects().get(i);
      activity.updateViewLocation(
          activity.getBallViews().get(i), currentBall.getX(), currentBall.getY());
    }
  }

  private void gameLoop() {
    final int FPS = 60;
    final long TIMER_REFRESH = 1000 / FPS;
    if (game.getUsesTimeLimit()) {
      startLimitedTimer(game.getTimeLimit(), TIMER_REFRESH);
    } else {
      startUnlimitedTimer(TIMER_REFRESH);
    }
  }

  private void startLimitedTimer(int timeLimit, long refresh) {
    CountDownTimer gameTimer =
        new CountDownTimer(game.getTimeLimit() * 1000, refresh) {
          @Override
          public void onTick(long l) {
            performGameOperations();
            if (game.getUsesLives()) {
              if (game.getLives() == 0) {
                cancel();
                game.endGame();
              }
            }
            if ((int) (Math.ceil(l / 1000)) < game.getTimeRemaining()) {
              game.setTimeRemaining(game.getTimeRemaining() - 1);
              renderTime();
            }
          }

          @Override
          public void onFinish() {
            game.endGame();
          }
        }.start();
  }

  private void startUnlimitedTimer(long refresh) {
    Timer gameTimer = new Timer();
    gameTimer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            performGameOperations();
            if (game.getLives() == 0) {
              cancel(); // Cancels this timer and closes its threads
              game.endGame();
            }
          }
        },
        0,
        refresh);
  }

  private void performGameOperations() {
    game.updateMovements();
    game.updateCollisions();
    renderBallObjects();
    destroyInactiveBalls();
    renderScore();
  }

  public void startGame() {
    game.startGame();
    gameLoop();
  }

  private void destroyInactiveBalls() {
    for (int index : game.getInactiveBallIndices()) {
      activity.removeViewFromLayout(activity.getBallViews().get(index), activity.getBallLayout());
      activity.getBallViews().remove(index);
    }
    game.getInactiveBallIndices().clear();
  }
}

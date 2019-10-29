package com.example.game.games.ballgame;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;

import com.example.game.games.Game;
import com.example.game.utilities.Timer;
import java.util.ArrayList;

public class BallGame extends Game {

  private ArrayList<Ball> ballObjects = new ArrayList<>();
  private Player player;
  private Target target;

  public BallGame(int timeLimit) {
    super(timeLimit);
  }

  @Override
  public int play() {
    gameLoop();
    return 0;
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

  /**
   * Main game loop. Loop from https://dewitters.com/dewitters-gameloop/ private void gameLoop() {
   * final double FRAME_TIME = 1000 / 60; final int MAX_FRAMES_SKIPPED = 5; Timer gameTimer = new
   * Timer(getTimeLimit() * 1000); double nextTick = SystemClock.uptimeMillis(); while
   * (!gameTimer.isStopped()) { int frames = 0; while (SystemClock.uptimeMillis() > nextTick &&
   * frames < MAX_FRAMES_SKIPPED) { updateGame(); nextTick += FRAME_TIME; frames++; }
   *
   * <p>renderGame((float)((SystemClock.uptimeMillis() + FRAME_TIME - nextTick) / FRAME_TIME)); } }
   */
  private void gameLoop() {
    final int FPS = 60;
    final long TIMER_REFRESH = 1000 / FPS;
    CountDownTimer gameTimer =
        new CountDownTimer(getTimeLimit() * 1000, TIMER_REFRESH) {
          @Override
          public void onTick(long l) {
            updateGame();
          }

          @Override
          public void onFinish() {}
        }.start();
  }

  public void performPlayerAction(PlayerActions action) {
    switch (action) {
      case AngleUp:
        if (player.getShotAngle() < 90) {
          player.setShotAngle(player.getShotAngle() + 5);
          System.out.println(player.getShotAngle() + " " + player.getShotPower());
        }
        break;
      case AngleDown:
        if (player.getShotAngle() > 0) {
          player.setShotAngle(player.getShotAngle() - 5);
          System.out.println(player.getShotAngle() + " " + player.getShotPower());
        }
        break;
      case PowerUp:
        if (player.getShotPower() < 50) {
          player.setShotPower(player.getShotPower() + 5);
        }
        break;
      case PowerDown:
        if (player.getShotPower() > 0) {
          player.setShotPower(player.getShotPower() - 5);
        }
        break;
    }
  }

  public void performPlayerAction(PlayerActions action, View view) {
    if (action == PlayerActions.Shoot) {
      view.setX(player.getX());
      view.setY(player.getY());
      Ball newBall = player.shootBall(view.getLayoutParams().width, view.getLayoutParams().height);
      newBall.setObjectView(view);
      ballObjects.add(newBall);
    }
  }

  private void updateGame() {
    for (Ball object : ballObjects) {
      object.update();
      System.out.println("BALL UPDATED");
    }
  }
}

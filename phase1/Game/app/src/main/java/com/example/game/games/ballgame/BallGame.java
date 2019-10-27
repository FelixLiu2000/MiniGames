package com.example.game.games.ballgame;

import android.os.SystemClock;

import com.example.game.games.Game;
import com.example.game.utilities.Timer;

public class BallGame extends Game {

  public BallGame(int timeLimit) {
    super(timeLimit);
  }

  @Override
  public void createGameEnvironment() {
    super.createGameEnvironment();
  }

  @Override
  protected int play() {
    startGame();
    gameLoop();
    return 0;
  }

  private void startGame() {}

  /** Main game loop. Loop from https://dewitters.com/dewitters-gameloop/ */
  private void gameLoop() {
    final double FRAME_TIME = 1000 / 60;
    final int MAX_FRAMES_SKIPPED = 5;
    Timer gameTimer = new Timer(getTimeLimit() * 1000);
    double nextTick = SystemClock.uptimeMillis();
    while (!gameTimer.isStopped()) {
      int frames = 0;
      while (SystemClock.uptimeMillis() > nextTick && frames < MAX_FRAMES_SKIPPED) {
        updateGame();
        nextTick += FRAME_TIME;
        frames++;
      }

      renderGame((SystemClock.uptimeMillis() + FRAME_TIME - nextTick) / FRAME_TIME);
    }
  }

  private void updateGame() {}

  private void renderGame(double delta) {}
}

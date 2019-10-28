package com.example.game.games.ballgame;

import android.os.SystemClock;
import android.view.View;

import com.example.game.games.Game;
import com.example.game.utilities.Timer;
import java.util.ArrayList;

public class BallGame extends Game {

  private ArrayList<BallGameObject> gameObjects;
  private Player player;

  public BallGame(int timeLimit) {
    super(timeLimit);
  }

  @Override
  protected int play() {
    gameLoop();
    return 0;
  }

  public void initializePlayer(View view) {
    player = new Player(view.getX(), view.getY());
  }

  public void initializeTarget(View view) {}

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

  public void performPlayerAction(PlayerActions action) {
    switch (action) {
      case AngleUp:
        if (player.getShotAngle() < 90) {
          player.setShotAngle(player.getShotAngle() + 5);
        }
        break;
      case AngleDown:
        if (player.getShotAngle() > 0) {
          player.setShotAngle(player.getShotAngle() - 5);
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
      case Shoot:
        gameObjects.add(player.shootBall());
        break;
    }
  }

  private void updateGame() {}

  private void renderGame(double delta) {}
}

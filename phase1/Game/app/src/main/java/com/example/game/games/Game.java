package com.example.game.games;

import com.example.game.utilities.Timer;

public abstract class Game {

  private int score = 0;
  private int timeLimit;
  private Timer gameTimer;

  public Game(int timeLimit) {
    this.timeLimit = timeLimit;
  }

  public int getScore() {
    return this.score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void createGameEnvironment() {} // takes code from XML

  public void startGame() {
    // starts game
    this.gameTimer = new Timer(timeLimit * 1000); // convert seconds to milliseconds
    while (!gameTimer.isStopped()) {
      updateGame();
    }
    endGame();
  }

  public abstract void updateGame();

  public void endGame() {
    // end game
  }
}

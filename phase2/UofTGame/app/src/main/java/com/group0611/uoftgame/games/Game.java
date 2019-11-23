package com.group0611.uoftgame.games;

import android.content.Intent;

import com.group0611.uoftgame.utilities.AppManager;

public abstract class Game {

  private int score, timeLimit;
  private AppManager appManager;

  /**
   * Constructs a new timed game.
   *
   * @param timeLimit the time limit for the game, in seconds.
   */
  public Game(int timeLimit, AppManager appManager) {
    this.score = 0;
    this.timeLimit = timeLimit;
    this.appManager = appManager;
  }

  protected int getScore() {
    return this.score;
  }
  protected void setScore(int score) { this.score = score; }

  protected int getTimeLimit() { return timeLimit; }
  protected void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }

  protected AppManager getAppManager() { return this.appManager; }
  protected void setAppManager(AppManager appManager) { this.appManager = appManager; }

  protected abstract void play();

  protected abstract void endGame();
}

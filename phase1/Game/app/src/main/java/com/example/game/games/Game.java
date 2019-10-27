package com.example.game.games;

public abstract class Game {

  private int score = 0;
  private int timeLimit;

  /**
   * Constructs a new timed game.
   * @param timeLimit the time limit for the game, in seconds.
   */
  public Game(int timeLimit) {
    this.timeLimit = timeLimit;
  }

  protected int getScore() {
    return this.score;
  }

  protected void setScore(int score) {
    this.score = score;
  }

  protected int getTimeLimit() {
    return timeLimit;
  }

  public void createGameEnvironment() {} // takes code from XML

  protected abstract int play();
}

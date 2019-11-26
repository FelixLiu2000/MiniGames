package com.group0611.uoftgame.games;

import com.group0611.uoftgame.activities.GameActivity;
import com.group0611.uoftgame.games.ballgame.BallGame;
import com.group0611.uoftgame.games.cardgame.CardGame;
import com.group0611.uoftgame.games.subwaygame.SubwayGame;
import com.group0611.uoftgame.utilities.AppManager;

public abstract class Game {

  private int score, timeLimit, lives;
  private AppManager appManager;
  private GameActivity activity;
  private boolean usesTimeLimit, usesLives, usesMultiplayer;

  /**
   * Constructs a new timed game.
   *
   * @param builder GameBuilder that provides this game's parameters and settings.
   */
  public Game(GameBuilder builder) {
    this.appManager = builder.manager;
    this.activity = builder.activity;
    this.usesTimeLimit = builder.usesTime;
    this.usesLives = builder.usesLives;
    this.usesMultiplayer = builder.usesMultiplayer;

    if (usesTimeLimit) {
      setTimeLimit(builder.timeLimit);
    }
    if (usesLives) {
      setLives(builder.lives);
    }
  }

  public int getScore() {
    return this.score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getTimeLimit() {
    return timeLimit;
  }

  private void setTimeLimit(int timeLimit) {
    this.timeLimit = timeLimit;
  }

  public boolean getUsesTimeLimit() {
    return usesTimeLimit;
  }

  public boolean getUsesLives() {
    return usesLives;
  }

  public int getLives() {
    return lives;
  }

  private void setLives(int lives) {
    this.lives = lives;
  }

  public boolean getUsesMultiplayer() {
    return usesMultiplayer;
  }

  protected AppManager getAppManager() {
    return this.appManager;
  }

  protected void setAppManager(AppManager appManager) {
    this.appManager = appManager;
  }

  protected GameActivity getActivity() {
    return activity;
  }

  protected void setActivity(GameActivity activity) {
    this.activity = activity;
  }

  protected abstract void startGame();

  protected abstract void endGame();

  public static class GameBuilder {
    private Class gameType;
    private AppManager manager;
    private GameActivity activity;
    private boolean usesTime;
    private boolean usesLives;
    private boolean usesMultiplayer;
    private int timeLimit;
    private int lives;

    public GameBuilder(Class gameType, AppManager manager, GameActivity activity) {
      this.gameType = gameType;
      this.manager = manager;
      this.activity = activity;
    }

    public GameBuilder setUsesTime(boolean state) {
      usesTime = state;
      return this;
    }

    public GameBuilder setTimeLimit(int timeLimit) {
      if (timeLimit < 0) {
        throw new IllegalArgumentException("Illegal argument: timeLimit must be positive.");
      }
      this.timeLimit = timeLimit;
      return this;
    }

    public GameBuilder setUsesLives(boolean state) {
      usesLives = state;
      return this;
    }

    public void setLives(int lives) {
      if (lives < 0) {
        throw new IllegalArgumentException("Illegal argument: lives must be positive.");
      }
      this.lives = lives;
    }

    public GameBuilder setUsesMultiplayer(boolean state) {
      usesMultiplayer = state;
      return this;
    }

    public Game build() {
      if (!usesTime && !usesLives) {
        throw new IllegalStateException(
            "Missing game parameter: game must implement a time limit and/or lives");
      } else if (gameType == BallGame.class) {
        return new BallGame(this);
      } else if (gameType == CardGame.class) {
        return new CardGame(this);
      } else if (gameType == SubwayGame.class) {
        return new SubwayGame(this);
      } else {
        throw new IllegalArgumentException(
            "Illegal argument: gameType " + gameType.getSimpleName() + "is not a valid game.");
      }
    }
  }
}

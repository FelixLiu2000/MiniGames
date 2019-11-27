package com.group0611.uoftgame.games;

import com.group0611.uoftgame.activities.GameActivity;
import com.group0611.uoftgame.games.ballgame.BallGame;
import com.group0611.uoftgame.games.cardgame.CardGame;
import com.group0611.uoftgame.games.subwaygame.SubwayGame;
import com.group0611.uoftgame.utilities.AppManager;

public abstract class Game {

  private AppManager appManager;
  private GameActivity activity;

  /**
   * Constructs a new timed game.
   *
   * @param builder GameBuilder that contains this game's parameters and settings.
   */
  public Game(GameBuilder builder) {
    this.appManager = builder.manager;
    this.activity = builder.activity;
  }

  protected AppManager getAppManager() {
    return this.appManager;
  }

  protected GameActivity getActivity() {
    return activity;
  }

  protected abstract void startGame();

  protected abstract void endGame();

  /**
   * Builder class for constructing games of different game modes.. Usage: game = (ExampleGame) new
   * Game.GameBuilder(ExampleGame.class, manager, exampleGameActivity).setTimedGame. ... .build();
   */
  public static class GameBuilder {
    private Class gameType;
    private AppManager manager;
    private GameActivity activity;
    private boolean hasTimedGameMode;
    private boolean hasLivesGameMode;
    private boolean hasMultiplayerGameMode;
    private int timeLimit;
    private int lives;

    public GameBuilder(Class gameType, AppManager manager, GameActivity activity) {
      this.gameType = gameType;
      this.manager = manager;
      this.activity = activity;
    }

    public GameBuilder addTimedGameMode(boolean state) {
      hasTimedGameMode = state;
      return this;
    }

    public GameBuilder setTimeLimit(int timeLimit) {
      if (timeLimit <= 0) {
        throw new IllegalArgumentException("Illegal argument: timeLimit is negative.");
      }
      this.timeLimit = timeLimit;
      return this;
    }

    public GameBuilder addLivesGameMode(boolean state) {
      hasLivesGameMode = state;
      return this;
    }

    public GameBuilder setLives(int lives) {
      if (lives <= 0) {
        throw new IllegalArgumentException("Illegal argument: lives is negative.");
      }
      this.lives = lives;
      return this;
    }

    public GameBuilder addMultiplayerGameMode(boolean state) {
      hasMultiplayerGameMode = state;
      return this;
    }

    public int getLives() {
      return lives;
    }

    public boolean getHasLives() {
      return hasLivesGameMode;
    }

    public int getTimeLimit() {
      return timeLimit;
    }

    public boolean getHasTimed() {
      return hasTimedGameMode;
    }

    public boolean getHasMultiplayer() {
      return hasMultiplayerGameMode;
    }

    public Game build() {
      if (!hasTimedGameMode && !hasLivesGameMode) {
        throw new IllegalStateException(
            "Missing game parameter: game must implement time and/or lives game mode(s)");
      } else if (hasTimedGameMode && timeLimit <= 0) {
        throw new IllegalStateException(
            "Missing game parameter: timed game mode requires time limit to be set.");
      } else if (hasLivesGameMode && lives <= 0) {
        throw new IllegalStateException(
            "Missing game parameter: lives game mode requires number of lives to be set.");
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

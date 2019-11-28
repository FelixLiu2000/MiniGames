package com.group0611.uoftgame.games;

import com.group0611.uoftgame.activities.GameActivity;
import com.group0611.uoftgame.games.ballgame.BallGame;
import com.group0611.uoftgame.games.cardgame.CardGame;
import com.group0611.uoftgame.games.subwaygame.SubwayGame;
import com.group0611.uoftgame.utilities.AppManager;

public abstract class Game {

  private AppManager appManager;
  private GameActivity activity;
  private int startingLives, timeLimit; // At most one of (startingLives, timeLimit) is optional
  private boolean usesLivesGameMode, usesTimedGameMode, usesMultiplayerGameMode;

  /**
   * Constructs a new timed game.
   *
   * @param builder GameBuilder that contains this game's parameters and settings.
   */
  public Game(GameBuilder builder) {
    this.appManager = builder.manager;
    this.activity = builder.activity;
    this.usesLivesGameMode = builder.usesLivesGameMode;
    this.usesTimedGameMode = builder.usesTimedGameMode;
    this.usesMultiplayerGameMode = builder.usesMultiplayerGameMode;
    this.startingLives = builder.startingLives;
    this.timeLimit = builder.timeLimit;
  }

  protected int getStartingLives() {
    return startingLives;
  }

  protected int getTimeLimit() {
    return timeLimit;
  }

  protected boolean getUsesLivesGameMode() {
    return usesLivesGameMode;
  }

  protected boolean getUsesTimedGameMode() {
    return usesTimedGameMode;
  }

  protected boolean getUsesMultiplayerGameMode() {
    return usesMultiplayerGameMode;
  }

  protected AppManager getAppManager() {
    return this.appManager;
  }

  protected GameActivity getActivity() {
    return activity;
  }

  protected abstract void startGame();

  protected abstract void endGame();

  protected abstract int getCurrentPlayerScore();

  // Usage: game = (ExampleGame) new Game.GameBuilder(ExampleGame.class, manager,
  // exampleGameActivity).setTimedGame. ... .build();
  /** Builder class for constructing games that implement different game modes. */
  public static class GameBuilder {
    private Class gameType;
    private AppManager manager;
    private GameActivity activity;
    private boolean usesTimedGameMode;
    private boolean usesLivesGameMode;
    private boolean usesMultiplayerGameMode;
    private int timeLimit;
    private int startingLives;
    private Game game;

    /**
     * Constructs a new GameBuilder used for building a given game type.
     *
     * @param gameType the game being built.
     * @param manager the appManager managing the game.
     * @param activity the game's corresponding activity.
     */
    public GameBuilder(Class gameType, AppManager manager, GameActivity activity) {
      this.gameType = gameType;
      this.manager = manager;
      this.activity = activity;
    }

    /** Sets whether to add time limit functionality to the game being built. */
    public GameBuilder addTimedGameMode(boolean state) {
      usesTimedGameMode = state;
      return this;
    }

    public GameBuilder setTimeLimit(int timeLimit) {
      if (timeLimit <= 0) {
        throw new IllegalArgumentException("Illegal argument: timeLimit is negative.");
      }
      this.timeLimit = timeLimit;
      return this;
    }

    /** Sets whether to add lives functionality to the game being built. */
    public GameBuilder addLivesGameMode(boolean state) {
      usesLivesGameMode = state;
      return this;
    }

    public GameBuilder setStartingLives(int startingLives) {
      if (startingLives <= 0) {
        throw new IllegalArgumentException("Illegal argument: startingLives is negative.");
      }
      this.startingLives = startingLives;
      return this;
    }

    /** Sets whether to add multiplayer functionality to the game being built. */
    public GameBuilder addMultiplayerGameMode(boolean state) {
      usesMultiplayerGameMode = state;
      return this;
    }

    /** Builds an instance of the game. */
    public GameBuilder build() {
      if (!usesTimedGameMode && !usesLivesGameMode) {
        throw new IllegalStateException(
            "Missing game parameter: game must implement time and/or startingLives game mode(s)");
      } else if (usesTimedGameMode && timeLimit <= 0) {
        throw new IllegalStateException(
            "Missing game parameter: timed game mode requires time limit to be set.");
      } else if (usesLivesGameMode && startingLives <= 0) {
        throw new IllegalStateException(
            "Missing game parameter: startingLives game mode requires number of startingLives to be set.");
      } else if (gameType == BallGame.class) {
        game = new BallGame(this);
      } else if (gameType == CardGame.class) {
        game = new CardGame(this);
      } else if (gameType == SubwayGame.class) {
        game = new SubwayGame(this);
      } else {
        throw new IllegalArgumentException(
            "Illegal argument: gameType " + gameType.getSimpleName() + "is not a valid game.");
      }
      return this;
    }

    /** Returns the built game. */
    public Game getGame() {
      return game;
    }
  }
}

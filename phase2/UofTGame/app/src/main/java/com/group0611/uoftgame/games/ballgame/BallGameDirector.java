package com.group0611.uoftgame.games.ballgame;

import com.group0611.uoftgame.activities.BallGameActivity;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.utilities.AppManager;
import com.group0611.uoftgame.utilities.GameDifficulty;
import com.group0611.uoftgame.utilities.GameMode;
import static com.group0611.uoftgame.games.ballgame.GameConstants.*;

/** Constructs predefined BallGame game types using the GameBuilder. */
public class BallGameDirector {
  private BallGameActivity activity;
  private AppManager manager;

  public BallGameDirector(BallGameActivity activity, AppManager manager) {
    this.activity = activity;
    this.manager = manager;
  }

  /**
   * Constructs a new BallGame with a specified gamemode and difficulty.
   *
   * @param mode the mode to be played.
   * @param difficulty difficulty of the game.
   * @param hasMultiplayer whether the game has more than one player.
   * @return a timed/infinite ball game.
   */
  public BallGame constructGame(GameMode mode, GameDifficulty difficulty, boolean hasMultiplayer) {
    if (mode == GameMode.INFINITE) {
      return constructInfiniteGame(difficulty, hasMultiplayer);
    } else if (mode == GameMode.TIMED) {
      return constructTimedGame(difficulty, hasMultiplayer);
    } else {
      throw new IllegalArgumentException("Illegal argument: gamemode " + mode + " does not exist.");
    }
  }

  private BallGame constructTimedGame(GameDifficulty difficulty, boolean hasMultiplayer) {
    int timeLimit, startingLives;
    if (difficulty == GameDifficulty.EASY) {
      timeLimit = TIME_LIMIT_EASY;
      startingLives = LIVES_EASY;
    } else if (difficulty == GameDifficulty.MEDIUM) {
      timeLimit = TIME_LIMIT_MEDIUM;
      startingLives = LIVES_MEDIUM;
    } else if (difficulty == GameDifficulty.HARD) {
      timeLimit = TIME_LIMIT_HARD;
      startingLives = LIVES_HARD;
    } else {
      throw new IllegalArgumentException(
          "Illegal argument: difficulty " + difficulty + " does not exist.");
    }
    return (BallGame)
        new Game.GameBuilder(BallGame.class, this.manager, this.activity)
            .addTimedGameMode(true)
            .setTimeLimit(timeLimit)
            .addLivesGameMode(true)
            .setStartingLives(startingLives)
            .addMultiplayerGameMode(hasMultiplayer)
            .build()
            .getGame();
  }

  private BallGame constructInfiniteGame(GameDifficulty difficulty, boolean hasMultiplayer) {
    int startingLives;
    if (difficulty == GameDifficulty.EASY) {
      startingLives = LIVES_EASY;
    } else if (difficulty == GameDifficulty.MEDIUM) {
      startingLives = LIVES_MEDIUM;
    } else if (difficulty == GameDifficulty.HARD) {
      startingLives = LIVES_HARD;
    } else {
      throw new IllegalArgumentException(
          "Illegal argument: difficulty " + difficulty + " does not exist.");
    }
    return (BallGame)
        new Game.GameBuilder(BallGame.class, this.manager, this.activity)
            .addLivesGameMode(true)
            .setStartingLives(startingLives)
            .addMultiplayerGameMode(hasMultiplayer)
            .build()
            .getGame();
  }
}

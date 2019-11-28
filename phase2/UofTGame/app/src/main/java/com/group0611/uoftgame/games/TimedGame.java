package com.group0611.uoftgame.games;

public interface TimedGame {
  /**
   * Whether the game currently uses a time limit.
   * @return true if the game has a time limit, false otherwise.
   */
  boolean getUsesTimedGameMode();

  /**
   * The amount of time the game runs for.
   * @return the game's time limit.
   */
  int getTimeLimit();
}

package com.group0611.uoftgame.games;

public interface LivesGame {
  /**
   * Whether the game currently uses lives.
   *
   * @return true if the game uses lives, false otherwise.
   */
  boolean getUsesLivesGameMode();

  /**
   * The number of lives a player begins with.
   *
   * @return number of lives the player(s) are initialized with.
   */
  int getStartingLives();

  /**
   * Whether the current player is out of lives.
   *
   * @return true if the current player is out of lives, false otherwise.
   */
  boolean isOutOfLives();
}

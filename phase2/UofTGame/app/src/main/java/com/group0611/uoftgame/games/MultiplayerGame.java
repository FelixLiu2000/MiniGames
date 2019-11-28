package com.group0611.uoftgame.games;

public interface MultiplayerGame {
  /**
   * Whether the game currently has multiplayer functionality.
   * @return true if the game has multiplayer, false otherwise.
   */
  boolean getUsesMultiplayerGameMode();

  /**
   * Gets the score of the player with a given player number.
   * @param playerNumber number of a player (>=1).
   * @return the specified player's score.
   */
  int getPlayerScore(int playerNumber);

  void nextPlayerTurn();

  /**
   * Gets the number corresponding to the game's current player.
   * @return the current player's player number.
   */
  int getCurrentPlayerNumber();
}

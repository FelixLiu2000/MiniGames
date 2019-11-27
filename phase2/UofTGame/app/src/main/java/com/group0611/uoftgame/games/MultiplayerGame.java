package com.group0611.uoftgame.games;

public interface MultiplayerGame {
  boolean getHasMultiplayerGameMode();

  int getPlayer1Score();

  void setPlayer1Score(int score);

  int getPlayer2Score();

  void setPlayer2Score(int score);

  void nextPlayerTurn();
}

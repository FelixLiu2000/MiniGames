package com.group0611.uoftgame.games;

public interface MultiplayerGame {

  boolean hasMultiplayerGameMode();

  int getPlayer1Score();

  void setPlayer1Score(int score);

  int getPlayer2Score();

  void setPlayer2Score(int score);

  void nextPlayerTurn();
}

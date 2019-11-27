package com.group0611.uoftgame.games;

public interface LivesGame {
  boolean hasLivesGameMode();

  int getStartingLives();

  void setStartingLives(int lives);

  boolean isOutOfLives();
}

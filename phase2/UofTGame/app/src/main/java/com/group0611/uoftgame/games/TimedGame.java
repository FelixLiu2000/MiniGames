package com.group0611.uoftgame.games;

public interface TimedGame {
  boolean hasTimedGameMode();

  int getTimeLimit();

  void setTimeLimit(int timeLimit);
}

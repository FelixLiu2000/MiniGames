package com.example.game.utilities;

public class Timer {
  private long endTime;

  public Timer(int millisDuration) {
    long startTime = System.currentTimeMillis();
    this.endTime = startTime + millisDuration;
  }

  public boolean isStopped() {
    return System.currentTimeMillis() >= this.endTime;
  }
}

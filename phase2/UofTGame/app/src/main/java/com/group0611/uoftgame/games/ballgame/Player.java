package com.group0611.uoftgame.games.ballgame;

import android.graphics.PointF;

class Player {
  private PointF location;
  private int shotAngle = GameConstants.SHOT_STARTING_ANGLE;
  private int shotPower = GameConstants.SHOT_STARTING_POWER;
  private int score;
  private int remainingLives;
  private int totalThrows, totalHits, totalMisses;
  private String username;

  Player(float x, float y) {
    this.location = new PointF(x, y);
  }

  int getShotAngle() {
    return shotAngle;
  }

  int getShotPower() {
    return shotPower;
  }

  void setShotAngle(int angle) {
    this.shotAngle = angle;
  }

  void setShotPower(int power) {
    this.shotPower = power;
  }

  String getUsername() {
    return username;
  }

  void setUsername(String username) {
    this.username = username;
  }

  int getRemainingLives() {
    return remainingLives;
  }

  void setRemainingLives(int remainingLives) {
    this.remainingLives = remainingLives;
  }

  /**
   * Shoots a ball from this player's location.
   *
   * @param width the width of the ball.
   * @param height the height of the ball.
   * @return the ball being fired.
   */
  Ball shootBall(int width, int height) {
    totalThrows++;
    return new Ball(
        this.location.x, this.location.y, width, height, getShotAngle(), getShotPower());
  }

  int getScore() {
    return this.score;
  }

  void setScore(int newScore) {
    this.score = newScore;
  }

  int getTotalThrows() {
    return totalThrows;
  }

  int getTotalHits() {
    return totalHits;
  }

  void setTotalHits(int totalHits) {
    this.totalHits = totalHits;
  }

  int getTotalMisses() {
    return totalMisses;
  }

  void setTotalMisses(int totalMisses) {
    this.totalMisses = totalMisses;
  }
}

package com.group0611.uoftgame.games.ballgame;

import android.graphics.PointF;

class Player {
  private PointF location;
  private int shotAngle = GameConstants.SHOT_STARTING_ANGLE;
  private int shotPower = GameConstants.SHOT_STARTING_POWER;
  private int score;
  private int lives;
  private String username;

  Player(float x, float y) {
    this.location = new PointF(x, y);
  }

  float getX() {
    return location.x;
  }

  float getY() {
    return location.y;
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

  int getLives() {
    return lives;
  }

  void setLives(int lives) {
    this.lives = lives;
  }

  Ball shootBall(int width, int height) {
    return new Ball(
        this.location.x, this.location.y, width, height, getShotAngle(), getShotPower());
  }

  int getScore() {
    return this.score;
  }

  void setScore(int newScore) {
    this.score = newScore;
  }
}

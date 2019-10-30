package com.example.game.games.ballgame;

import android.graphics.PointF;
import android.view.View;

import java.util.ArrayList;

class Player {
  private PointF location;
  private int shotAngle = BallGame.SHOT_STARTING_ANGLE;
  private int shotPower = BallGame.SHOT_STARTING_POWER;
  private int score;

  Player(float x, float y) {
    this.location = new PointF(x, y);
  }

  float getX() {
    return location.x;
  }

  float getY() {
    return location.y;
  }

  private int getShotAngle() {
    return shotAngle;
  }

  private int getShotPower() {
    return shotPower;
  }

  void setShotAngle(int angle) {
    this.shotAngle = angle;
  }

  void setShotPower(int power) {
    this.shotPower = power;
  }

  Ball shootBall(int width, int height) {
    return new Ball(
        this.location.x, this.location.y, width, height, getShotAngle(), getShotPower());
  }

  int getScore() {
    return this.score;
  }

  int setScore(int newScore) {
    this.score = newScore;
    return this.score;
  }
}

package com.example.game.games.ballgame;

import android.graphics.PointF;
import android.view.View;

import java.util.ArrayList;

class Player {
  private final int DEFAULT_POWER = 10;
  private PointF location;
  private int shotAngle = 0;
  private int shotPower = DEFAULT_POWER;

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

  Ball shootBall(int width, int height) {
    return new Ball(
        this.location.x, this.location.y, width, height, getShotAngle(), getShotPower());
  }
}

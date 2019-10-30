package com.example.game.games.ballgame;

abstract class BallGameObject {
  private float x, y, width, height;

  BallGameObject(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  abstract void update();

  float getX() {
    return x;
  }

  float getY() {
    return y;
  }

  float getWidth() {
    return width;
  }

  float getHeight() {
    return height;
  }

  void setLocation(float x, float y) {
    this.x = x;
    this.y = y;
  }
}

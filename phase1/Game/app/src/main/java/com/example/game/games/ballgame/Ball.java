package com.example.game.games.ballgame;

import android.view.View;

class Ball extends BallGameObject implements Collidable<Target> {
  private double speedX, speedY = 0;
  private final double GRAVITY = 0.1;

  Ball(float x, float y, float width, float height, int angle, int power) {
    super(x, y, width, height);
    calculateInitialSpeed(angle, power);
  }

  private void calculateInitialSpeed(int angle, int power) {
    this.speedX = power * Math.cos(angle);
    this.speedY = power * Math.sin(angle);
  }

  private void move() {
    speedY -= GRAVITY;
    setLocation((float)(getX() + speedX), (float)(getY() + speedY));
  }

  @Override
  void update() {
    move();
  }

  @Override
  public void onCollide(Target collidingObject) {

  }

  @Override
  public void render(View view) {

  }
}

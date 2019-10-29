package com.example.game.games.ballgame;

import android.view.View;

class Ball extends BallGameObject implements Collidable<Target>, Renderable {
  private double speedX, speedY = 0;
  private View view;

  Ball(float x, float y, float width, float height, int angle, int power) {
    super(x, y, width, height);
    calculateInitialSpeed(angle, power);
  }

  private void calculateInitialSpeed(int angle, int power) {
    this.speedX = power * Math.cos(Math.toRadians(angle));
    this.speedY = -(power * Math.sin(Math.toRadians(angle)));
    System.out.println(speedX + " SPD " + speedY);
  }

  private void move() {
    final double GRAVITY = 0.1;
    speedY += GRAVITY;
    setLocation((float) (getX() + speedX), (float) (getY() + speedY));
    view.setX(getX());
    view.setY(getY());
  }

  @Override
  void update() {
    move();
  }

  @Override
  public void onCollide(Target collidingObject) {}

  @Override
  public View getObjectView() {
    return view;
  }

  @Override
  public void setObjectView(View view) {
    this.view = view;
  }
}

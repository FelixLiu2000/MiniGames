package com.group0611.uoftgame.games.ballgame;

import android.graphics.RectF;

class Ball extends BallGameObject implements Collidable<Target> {
  private double speedX, speedY = 0;
  private RectF boundingBox;
  private Class collidableType;
  private boolean hasCollided = false;

  Ball(float x, float y, float width, float height, int angle, int power) {
    super(x, y, width, height);
    calculateInitialSpeed(angle, power);
    setBoundingBox(x, y, width, height);
    setCollidableType(Target.class);
  }

  private void calculateInitialSpeed(int angle, int power) {
    this.speedX = power * Math.cos(Math.toRadians(angle));
    this.speedY = -(power * Math.sin(Math.toRadians(angle)));
  }

  private void move() {
    speedY += GameConstants.GRAVITY;
    setLocation((float) (getX() + speedX), (float) (getY() + speedY));
  }

  @Override
  void update() {
    move();
  }

  @Override
  void setLocation(float x, float y) {
    super.setLocation(x, y);
    setBoundingBox(x, y);
  }

  @Override
  public void onCollide(Target collidingObject) {
    System.out.println("BALL received COLLISION!");
    hasCollided = true;
  }

  @Override
  public boolean hasCollided() {
    return hasCollided;
  }

  @Override
  public RectF getBoundingBox() {
    return boundingBox;
  }

  private void setBoundingBox(float x, float y) {
    setBoundingBox(x, y, this.getWidth(), this.getHeight());
  }

  @Override
  public void setBoundingBox(float x, float y, float width, float height) {
    if (this.boundingBox != null) {
      this.boundingBox.set(x, y, x + width, y + height);
    } else {
      this.boundingBox = new RectF(x, y, x + width, y + height);
    }
  }

  @Override
  public Class getCollidableType() {
    return collidableType;
  }

  @Override
  public void setCollidableType(Class collidableType) {
    this.collidableType = collidableType;
  }
}

package com.group0611.uoftgame.games.ballgame;

import android.graphics.RectF;
import android.view.View;

class Target extends BallGameObject implements Collidable<Ball> {
  private float speed;
  private Class collidableType;
  private RectF boundingBox;
  private boolean hasCollided;
  private boolean isMovingLeft = true;
  private float movementRadius;
  private float currentDistanceTravelled = 0;
  private float startingX;

  Target(float x, float y, float width, float height) {
    super(x, y, width, height);
    setCollidableType(Ball.class);
    setBoundingBox(x, y, width, height);
    setSpeed(GameConstants.TARGET_SPEED);
    // Set the starting x location and calculate the radius to move within
    startingX = x;
    movementRadius = GameConstants.TARGET_MOVEMENT_MULTIPLIER * boundingBox.width();
  }

  @Override
  void update() { move(); }

  private void move() {
    if (isMovingLeft) { // When target is moving left
      // If the target has moved far enough
      if (currentDistanceTravelled >= movementRadius) {
        // Reset location to radius boundary
        setLocation(startingX - movementRadius, getY());
        // Begin movement back to starting position
        isMovingLeft = false;
        currentDistanceTravelled = 0;
      } else {
        // Move the target by a set speed and record distance travelled
        setLocation(getX() - speed, getY());
        currentDistanceTravelled += speed;
      }
    } else { // When target is moving right
      // If the target has moved far enough
      if (currentDistanceTravelled >= movementRadius) {
        // Reset location to radius boundary
        setLocation(startingX, getY());
        // Begin movement back to opposite boundary
        isMovingLeft = true;
        currentDistanceTravelled = 0;
      } else {
        // Move the target by a set speed and record distance travelled
        setLocation(getX() + speed, getY());
        currentDistanceTravelled += speed;
      }
    }
  }

  @Override
  public void onCollide(Ball collidingObject) {
    System.out.println("TARGET WAS HIT!");
    this.hasCollided = true;
  }

  @Override
  public boolean hasCollided() {
    return hasCollided;
  }

  @Override
  void setLocation(float x, float y) {
    super.setLocation(x, y);
    setBoundingBox(x, y);
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
  public void setCollidableType(Class collidableType) {
    this.collidableType = collidableType;
  }

  @Override
  public Class getCollidableType() {
    return collidableType;
  }

  private void setSpeed(float speed) {
    this.speed = speed;
  }
}

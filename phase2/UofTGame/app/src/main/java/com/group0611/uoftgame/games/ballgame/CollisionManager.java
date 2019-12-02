package com.group0611.uoftgame.games.ballgame;

import android.graphics.RectF;

import java.util.ArrayList;

class CollisionManager {

  /**
   * Perform collision checking for any balls that have collided with the target.
   *
   * @param ballObjects the active ball objects that may be colliding.
   * @param target the target that the balls may be colliding with.
   * @return the balls that have collided with the target.
   */
  ArrayList<Ball> checkOnTargetBallCollisions(ArrayList<Ball> ballObjects, Target target) {
    ArrayList<Ball> collidedBalls = new ArrayList<>();
    for (Ball obj1 : ballObjects) {
      if (obj1 != null) {
        if (checkCollided(obj1, target)) {
          collidedBalls.add(obj1);
        }
      }
    }
    return collidedBalls;
  }

  /**
   * Perform collision checking for any balls that have collided with the left side of the target.
   *
   * @param ballObjects the active ball objects that may be colliding.
   * @param target the target that the balls may be colliding with.
   * @return the balls that have collided with the target.
   */
  ArrayList<Ball> checkOffTargetBallCollisions(
      ArrayList<Ball> ballObjects,
      Target target,
      int screenLeft,
      int screenTop,
      int screenRight,
      int screenBottom) {
    ArrayList<Ball> collidedBalls = new ArrayList<>();
    for (Ball obj1 : ballObjects) {
      if (obj1 != null) {
        if (checkCollisionFromLeft(obj1, target)
            || checkScreenBallCollision(obj1, screenLeft, screenTop, screenRight, screenBottom)) {
          collidedBalls.add(obj1);
        }
      }
    }
    return collidedBalls;
  }

  /**
   * Perform collision checking for a ball that may have collided with the screen bounds.
   *
   * @param ball the active ball to be checked.
   * @param screenLeft the left of the screen.
   * @param screenTop the top of the screen.
   * @param screenRight the right of the screen.
   * @param screenBottom the bottom of the screen.
   * @return whether the ball has collided.
   */
  private boolean checkScreenBallCollision(
      Ball ball, float screenLeft, float screenTop, float screenRight, float screenBottom) {
    if (ball != null) {
      return checkCollided(ball, new RectF(screenLeft, screenTop, screenRight, screenBottom));
    }
    return false;
  }

  private boolean checkCollided(Collidable obj1, Collidable obj2) {
    return RectF.intersects(obj1.getBoundingBox(), obj2.getBoundingBox());
  }

  private boolean checkCollided(Collidable collidingObj, RectF outerBounds) {
    RectF collidingBB = collidingObj.getBoundingBox();
    return collidingBB.right <= outerBounds.left
        || collidingBB.left >= outerBounds.right
        || collidingBB.top >= outerBounds.bottom;
  }

  private boolean checkCollisionFromLeft(Collidable collidingObj, Collidable receivingObj) {
    final int COLLISION_MARGIN = 3;
    return checkCollided(collidingObj, receivingObj)
        && collidingObj.getBoundingBox().top - COLLISION_MARGIN
            >= receivingObj.getBoundingBox().top;
  }
}

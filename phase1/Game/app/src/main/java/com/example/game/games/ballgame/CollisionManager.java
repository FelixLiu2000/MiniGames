package com.example.game.games.ballgame;

import android.graphics.RectF;

import java.util.ArrayList;

class CollisionManager {

  /**
   * Perform collision checking for any balls that have collided with the top of the target.
   *
   * @param ballObjects the active ball objects that may be colliding.
   * @param target the target that the balls may be colliding with.
   * @return the balls that have collided with the target.
   */
  static ArrayList<Ball> checkTargetBallCollisions(
      ArrayList<Ball> ballObjects, Target target) {
    ArrayList<Ball> collidedBalls = new ArrayList<>();
    for (Ball obj1 : ballObjects) {
      if (obj1 != null) {
        if (checkCollisionFromTop(obj1, target)) {
          collidedBalls.add(obj1);
        }
      }
    }
    return collidedBalls;
  }

  /**
   * Perform collision checking for any balls that have collided with the screen bounds.
   * @param ballObjects the active ball objects to be checked.
   * @param screenLeft the left of the screen.
   * @param screenTop the top of the screen.
   * @param screenRight the right of the screen.
   * @param screenBottom the bottom of the screen.
   * @return the list of collided balls detected.
   */
  static ArrayList<Ball> checkScreenBallCollisions(
      ArrayList<Ball> ballObjects,
      float screenLeft,
      float screenTop,
      float screenRight,
      float screenBottom) {
    ArrayList<Ball> collidedBalls = new ArrayList<>();
    for (Ball ball : ballObjects) {
      if (ball != null) {
        if (checkCollided(ball, new RectF(screenLeft, screenTop, screenRight, screenBottom))) {
          collidedBalls.add(ball);
        }
      }
    }
    return collidedBalls;
  }

  private static boolean checkCollided(Collidable obj1, Collidable obj2) {
    return RectF.intersects(obj1.getBoundingBox(), obj2.getBoundingBox());
  }

  private static boolean checkCollided(Collidable collidingObj, RectF outerBounds) {
    RectF collidingBB = collidingObj.getBoundingBox();
    return collidingBB.right <= outerBounds.left ||
            collidingBB.left >= outerBounds.right || collidingBB.top >= outerBounds.bottom;
  }

  private static boolean checkCollisionFromTop(Collidable collidingObj, Collidable receivingObj) {
    final int COLLISION_MARGIN = 6;
    return checkCollided(collidingObj, receivingObj)
        && collidingObj.getBoundingBox().bottom - COLLISION_MARGIN <= receivingObj.getBoundingBox().top;
  }
}

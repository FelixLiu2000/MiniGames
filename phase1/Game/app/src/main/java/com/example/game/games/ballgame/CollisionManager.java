package com.example.game.games.ballgame;

import android.graphics.RectF;

import java.util.ArrayList;

class CollisionManager {


  /**
   * Perform collision checking for any balls that have collided with the top of the target.
   *
   * @param ballObjects the active ball objects that may be colliding.
   * @param target the target that the balls may be colliding with.
   *
   * @return the balls that have collided with the target.
   */
  static ArrayList<Ball> checkTargetBallCollisions(ArrayList<Ball> ballObjects, Target target) {
    ArrayList<Ball> collidedBalls = new ArrayList<Ball>();
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
   * Perform all general collision checking between a list of collidable objects.
   *
   * @param collidableObjects the collidable objects that is being checked.
   */
  @SuppressWarnings("unchecked") // check for type was done in method
  static void checkAllCollisions(ArrayList<Collidable> collidableObjects) {
    for (Collidable obj1 : collidableObjects) {
      if (obj1 != null) {
        for (Collidable obj2 : collidableObjects) {
          // ensures that obj1 implements Collidable<obj2.class> to allow onCollide(obj2) call
          if (obj1 != obj2
              && obj2.getClass() == obj1.getCollidableType()
              && checkCollided(obj1, obj2)) {

          }
        }
      }
    }
  }

  private static boolean checkCollided(Collidable obj1, Collidable obj2) {
    return RectF.intersects(obj1.getBoundingBox(), obj2.getBoundingBox());
  }

  private static boolean checkCollisionFromTop(Collidable objColliding, Collidable objReceiving) {
    return checkCollided(objColliding, objReceiving)
        && objColliding.getBoundingBox().top >= objReceiving.getBoundingBox().top;
  }
}

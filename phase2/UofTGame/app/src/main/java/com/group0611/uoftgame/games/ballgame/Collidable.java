package com.group0611.uoftgame.games.ballgame;

import android.graphics.RectF;

interface Collidable<T> {
  Class getCollidableType();

  void setCollidableType(Class collidableType);

  void onCollide(T collidingObject);

  boolean hasCollided();

  RectF getBoundingBox();

  void setBoundingBox(float x, float y, float width, float height);
}

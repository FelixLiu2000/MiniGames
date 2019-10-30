package com.example.game.games.ballgame;

import android.graphics.RectF;
import android.view.View;

class Target extends BallGameObject implements Collidable<Ball>, Renderable {
  private Class collidableType;
  private RectF boundingBox;
  private View view;
  private boolean hasCollided;

  Target(float x, float y, float width, float height) {
    super(x, y, width, height);
    setCollidableType(Ball.class);
    setBoundingBox(x, y, width, height);
  }

  @Override
  void update() {}

  @Override
  public void onCollide(Ball collidingObject) {
    System.out.println("TARGET received COLLISION!");
    this.hasCollided = true;
  }

  @Override
  public boolean hasCollided() {
    return hasCollided;
  }

  @Override
  public View getView() {
    return view;
  }

  @Override
  public void setObjectView(View view) {
    this.view = view;
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
}

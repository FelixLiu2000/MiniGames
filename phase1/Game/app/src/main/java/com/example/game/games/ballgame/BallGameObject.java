package com.example.game.games.ballgame;

import android.graphics.RectF;

abstract class BallGameObject {
    private RectF boundingBox;

    BallGameObject(float x, float y, float width, float height) {
        this.boundingBox = new RectF(x, y, x + width, x + height);
    }

    abstract void update();

    float getX() {
        return boundingBox.left;
    }

    float getY() {
        return boundingBox.top;
    }

    void setLocation(float x, float y) {
        this.boundingBox.set(x, y, this.boundingBox.right, this.boundingBox.bottom);
    }
}

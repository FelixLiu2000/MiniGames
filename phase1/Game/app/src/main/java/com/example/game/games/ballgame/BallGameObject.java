package com.example.game.games.ballgame;

import android.graphics.RectF;
import android.view.View;

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

    float getWidth() {
        return boundingBox.width();
    }

    float getHeight() {
        return boundingBox.height();
    }

    RectF getBoundingBox() {
        return boundingBox;
    }

    void setLocation(float x, float y) {
        this.boundingBox.set(x, y, this.boundingBox.right, this.boundingBox.bottom);
    }
}

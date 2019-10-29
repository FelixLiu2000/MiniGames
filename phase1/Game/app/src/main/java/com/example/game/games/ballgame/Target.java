package com.example.game.games.ballgame;


import android.view.View;

class Target extends BallGameObject implements Collidable<Ball>, Renderable {
    Target(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    private View view;

    @Override
    void update() {

    }

    @Override
    public void onCollide(Ball collidingObject) {

    }

    @Override
    public View getObjectView() {
        return view;
    }

    @Override
    public void setObjectView(View view) {
        this.view = view;
    }
}

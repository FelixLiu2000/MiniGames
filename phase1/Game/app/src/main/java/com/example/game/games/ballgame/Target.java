package com.example.game.games.ballgame;


class Target extends BallGameObject implements Collidable<Ball> , Renderable {
    public Target(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    void update() {

    }

    @Override
    public void onCollide(Ball collidingObject) {

    }

    @Override
    public void render() {

    }
}

package com.example.game.games.ballgame;

interface Collidable<T> {
    void onCollide(T collidingObject);
}

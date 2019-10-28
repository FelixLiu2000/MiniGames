package com.example.game;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SubwayGameActivity extends AppCompatActivity {
    ArrayList<ImageView> obstacles = new ArrayList<>();
    private ImageView runner;
    private SubwayGame game;
    // runner's x and y coordinates
    private float runnerX;
    private float runnerY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_game);
        runnerX = runner.getX();
        runnerY = runner.getY();
    }


    /** move runner right when right button is clicked */
    public void moveRight(View view) {
//        float yPosition = view.getY();
//        float xPosition = view.getX();
        // if runner is in lane 1
        if (runnerX == 16) {
            // move to lane 2
            TranslateAnimation runnerRight = new TranslateAnimation(runnerX, 204, runnerY, runnerY);
        // if runner is in lane 2
        } else if (runnerX == 204) {
            // move to lane 3
            TranslateAnimation runnerRight = new TranslateAnimation(runnerX, 340, runnerY, runnerY);
        }

    }

    /** move runner left when left button is clicked */
    public void moveLeft(View view) {
//        float yPosition = view.getY();
//        float xPosition = view.getX();
        // if runner is in lane 3
        if (runnerX == 340) {
            // move to lane 2
            TranslateAnimation runnerLeft = new TranslateAnimation(runnerX, 204, runnerY, runnerY);
        // if  runner is in lane 2
        } else if (runnerX == 204) {
            // move to lane 1
            TranslateAnimation runnerLeft = new TranslateAnimation(runnerX, 16, runnerY, runnerY);
        }
    }

    /** move all obstacles down every second */
    public void moveDown() {
        // loop through obstacles
        for (int i=0; i<obstacles.size(); i++) {
            ImageView obstacle = obstacles.get(i);
            // move down
            TranslateAnimation obstacleDown = new TranslateAnimation(obstacle.getX(),
                    obstacle.getX(),
                    obstacle.getY(),
                    obstacle.getY()+1);
        }
    }
}

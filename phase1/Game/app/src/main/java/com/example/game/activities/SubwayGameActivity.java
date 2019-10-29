package com.example.game.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.R;
import com.example.game.games.subwaygame.SubwayGame;

import java.util.ArrayList;

public class SubwayGameActivity extends AppCompatActivity {
    public ArrayList<ImageView> obstacles = new ArrayList<>();
    private ImageView runner;
    private SubwayGame game;
    // runner's x and y coordinates
    private float runnerX;
    private float runnerY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_game);
        runner = findViewById(R.id.subwayRunner);
        runnerX = runner.getX();
        runnerY = runner.getY();
        game = new SubwayGame(this);
    }


    /** move runner right when right button is clicked */
    public void moveRight(View view) {


        // if runner is in lane 1
        if (runnerX <= 30+16 || runnerX <= 30-16) {
            // move to lane 2
            TranslateAnimation runnerRight = new TranslateAnimation(runnerX, 204, runnerY, runnerY);
            runnerRight.setFillAfter(true);
            runnerRight.setDuration(500);
            runner.startAnimation(runnerRight);
            System.out.println("Moved right and the runner was in lane 1");
            runnerX += 204;

        // if runner is in lane 2
        } else if (runnerX <= 204+30 || runnerX >= 204-30) {
            // move to lane 3
            TranslateAnimation runnerRight = new TranslateAnimation(runnerX, 340, runnerY, runnerY);
            runnerRight.setFillAfter(true);
            runnerRight.setDuration(500);
            runner.startAnimation(runnerRight);
            System.out.println("Moved right and the runner was in lane 2");
            runnerX += 340;
        }

    }

    /** move runner left when left button is clicked */
    public void moveLeft(View view) {
//        float yPosition = view.getY();
//        float xPosition = view.getX();
        // if runner is in lane 3
        if (runnerX <= 340+30 || runnerX >= 340-30) {
            // move to lane 2
            TranslateAnimation runnerLeft = new TranslateAnimation(runnerX, -204, runnerY, runnerY);
            runnerLeft.setFillAfter(true);
            runnerLeft.setDuration(500);
            runner.startAnimation(runnerLeft);
            System.out.println("Moved left and the runner was in lane 3");
            runnerX -= 204;
        // if  runner is in lane 2
        } else if (runnerX <= 204+30 || runnerX >= 204-30) {
            // move to lane 1
            TranslateAnimation runnerLeft = new TranslateAnimation(runnerX, -16, runnerY, runnerY);
            runnerLeft.setFillAfter(true);
            runnerLeft.setDuration(500);
            runner.startAnimation(runnerLeft);
            System.out.println("Moved left and the runner was in lane 2");
            runnerX -= 16;
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

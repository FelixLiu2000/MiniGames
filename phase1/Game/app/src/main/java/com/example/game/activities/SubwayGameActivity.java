package com.example.game.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

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
    // runner'x lane
    public int runnerLane;
    private TextView currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_game);
        currentScore = findViewById(R.id.score);
        runner = findViewById(R.id.subwayRunner);
        runnerX = runner.getX();
        runnerY = runner.getY();
        runnerLane = 2;
        game = new SubwayGame(60,this);
//        System.out.println("Initial X and Y are: " + runnerX + runnerY);
    }

    /** move runner right when right button is clicked */
    public void moveRight(View view) {
        // if runner is in lane 1 or 2
        if (runnerX == 0) {
            // move to rightmost lane
            changeLanes("Right", runnerX, runnerY, 350);
            runnerLane = 3;
        } else if (runnerX == -350) {
            changeLanes("Right", runnerX, runnerY, 0);
            runnerLane = 2;
        }
    }

    /** move runner left when left button is clicked */
    public void moveLeft(View view) {
        // if runner is in lane 2 or 3
        if (runnerX == 0) {
            // move to leftmost lane
//            System.out.print(runnerX);
            changeLanes("Left", runnerX, runnerY, -350);
            runnerLane = 1;
        } else if (runnerX == 350) {
            changeLanes("Left", runnerX, runnerY, -0);
            runnerLane = 2;
        }
    }

    /** creates the animation that translates the runner into the leftmost or rightmost lane */
    public void changeLanes(String direction, float xCord, float yCord, int toX) {
        TranslateAnimation runnerDirection = new TranslateAnimation(0, toX, yCord, yCord);
        runnerDirection.setFillAfter(true);
        runnerDirection.setDuration(500);
        runner.startAnimation(runnerDirection);
        if (direction.equals("Right")) {
            runnerX += 350;
        } else runnerX -= 350;
    }

    /** move all obstacles down */
    public void moveDown() {
        // loop through obstacles
        for (int i=0; i<obstacles.size(); i++) {
            ImageView obstacle = obstacles.get(i);
            float obstacleY = obstacle.getY();
            // move down
            TranslateAnimation obstacleDown = new TranslateAnimation(0,
                    0,
                    0,
                    100);
            obstacleDown.setFillAfter(true);
            obstacleDown.setDuration(0);
            obstacle.startAnimation(obstacleDown);
            obstacleY += 100;
            obstacle.setY(obstacleY);
        }
    }

    public void updateScore(int score) {
        currentScore.setText("Current Score: " + score);
    }
}

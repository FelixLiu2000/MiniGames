package com.group0611.uoftgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.games.subwaygame.SubwayGame;
import com.group0611.uoftgame.utilities.AppManager;

import java.util.ArrayList;

public class SubwayGameActivity extends AppCompatActivity implements GameActivity {
    public ArrayList<ImageView> movingObjects = new ArrayList<>();
    private ImageView runner;
    private SubwayGame game;
    // runner's x and y coordinates
    private float runnerX;
    private float runnerY;
    // runner'x lane
    public int runnerLane;
    private TextView currentScore;
  private Intent currentIntent, toResultsPageIntent, toDashboardIntent;

  @Override
  public Intent getCurrentIntent() {
    return currentIntent;
  }

  private void setCurrentIntent(Intent currentIntent) {
    this.currentIntent = currentIntent;
  }

  @Override
  public Intent getToResultsPageIntent() {
    return toResultsPageIntent;
  }

  private void setToResultsPageIntent(Intent toResultsPageIntent) {
    this.toResultsPageIntent = toResultsPageIntent;
  }

  @Override
  public Intent getToDashboardIntent() {
    return toDashboardIntent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCurrentIntent(getIntent());
    AppManager appManager = (AppManager) getCurrentIntent().getSerializableExtra("appManager");
    setContentView(R.layout.activity_subway_game);
    currentScore = findViewById(R.id.score);
    runner = findViewById(R.id.subwayRunner);
    runnerX = runner.getX();
    runnerY = runner.getY();
    runnerLane = 2;

    int timeLimit = appManager.getCurrentPlayer().getTimeChoice()[0];
    game =
        (SubwayGame)
            new Game.GameBuilder(SubwayGame.class, appManager, this)
                .addTimedGameMode(true)
                .setTimeLimit(timeLimit)
                .build()
                .getGame();
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
        for (int i=0; i<movingObjects.size(); i++) {
            ImageView obstacle = movingObjects.get(i);
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
            System.out.println("Obstacle moved: " + obstacle.getY());
        }
    }

    /**
     * Updates the displayed score.
     * @param score the current score of the game
     */
    public void displayNewScore(int score) {
        currentScore.setText("Current Score: " + score);
    }

    public void leaveGame(AppManager appManager) {
        setToResultsPageIntent(new Intent(SubwayGameActivity.this, ResultsPageActivity.class));
        getToResultsPageIntent().putExtra("appManager", appManager);
        startActivity(getToResultsPageIntent());
    }

    @Override
    public void onBackPressed() {
        game.getSubwayGameTimer().cancel();
        finish();
    }
}

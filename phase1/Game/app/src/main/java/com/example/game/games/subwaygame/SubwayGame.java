package com.example.game.games.subwaygame;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import com.example.game.R;
import com.example.game.activities.SubwayGameActivity;
import com.example.game.games.Game;

public class SubwayGame extends Game {
  private SubwayGameActivity activity;
  //    private ArrayList<SubwayObstacle> obstacles = new ArrayList<>();
  private int score;

  public SubwayGame(SubwayGameActivity activity) {
    super(60);
    this.score = 10;
    this.activity = activity;
    play();
  }

  //    private void startRound() {
  //        SubwayRunner runner = new SubwayRunner();
  //    }

  private int play() {
    //        startRound();
    // create 60 second timer
    CountDownTimer timer =
        new CountDownTimer(60000, 1000) {
          @Override
          public void onTick(long millisUntilFinished) {
            System.out.println("Timer is ticking!" + millisUntilFinished);
            // check for collisions every second
            checkCollision();
            // move all obstacles down every second
            activity.moveDown();
            // create new obstacle every 3 seconds
            double nearestThousand = Math.ceil(millisUntilFinished/1000) * 1000;
            if (nearestThousand % 3000 == 0) {
              createObstacle();
            }
          }

          @Override
          public void onFinish() {
            System.out.println("Game Over!");
          }
        }.start();
    return score;
  }

  /** create a new obstacle and add it to obstacles ArrayList in SubwayGameActivity */
  private void createObstacle() {
    System.out.println("obstacle created");
    ImageView newObstacle = new ImageView(activity);
    // set obstacle position
    setPosition(newObstacle);
    //        SubwayObstacle newObstacle = new SubwayObstacle(obstacleLane);
    //        obstacles.add(newObstacle);
  }

  /** Set location of the new obstacle */
  private void setPosition(ImageView newObstacle) {
    // automatically set y variable to 0
    newObstacle.setY(0);
    // randomly pick a lane
    int obstacleLane = pickLane();
    // set obstacle's x position based on the lane
    if (obstacleLane == 1) {
      newObstacle.setX(16);
    } else if (obstacleLane == 2) {
      newObstacle.setX(204);
    } else newObstacle.setX(340); // lane 3
  }

  /** randomly determine which lane the new obstacle is in */
  private int pickLane() {
    return (int) (Math.random() * 4);

    //        int lane;
    //        double random = Math.random();
    //        if (random < 0.33) {
    //            lane = 1;
    //        } else if (random < 0.66) {
    //            lane = 2;
    //        } else lane = 3;
    //
    //        return lane;
  }

  /** check if runner and obstacle are in the same position and decrease score by 1 if they are */
  private void checkCollision() {
    // get position of runner
    View runner = activity.findViewById(R.id.subwayRunner);
    float runnerX = runner.getX();
    float runnerY = runner.getY();
    // loop through obstacles
    for (int i = 0; i < activity.obstacles.size(); i++) {
      // get position of obstacle
      View obstacle = activity.obstacles.get(i);
      float obstacleX = obstacle.getX();
      float obstacleY = obstacle.getY();
      // if position of runner and obstacle is the same
      if (obstacleX == runnerX && obstacleY == runnerY) {
        // decrease score
        decreaseScore();
      }
    }
  }

  /** decrease the score by 1 */
  private void decreaseScore() {
    this.score -= 1;
  }

  /** A wrapper method to implement abstract method from Game */
  @Override
  public void updateGame() {
    play();
  }
}

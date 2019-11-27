package com.group0611.uoftgame.games.subwaygame;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.activities.SubwayGameActivity;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.games.MultiplayerGame;
import com.group0611.uoftgame.games.TimedGame;

public class SubwayGame extends Game implements TimedGame, MultiplayerGame {
  private int player1Score, player2Score;

  public SubwayGame(GameBuilder builder) {
    super(builder);
    this.player1Score = 10;
    startGame();
  }

  @Override
  public boolean getHasTimedGameMode() {
    return super.getHasTimedGameMode();
  }

  @Override
  public boolean getHasMultiplayerGameMode() {
    return super.getHasMultiplayerGameMode();
  }

  @Override
  public int getTimeLimit() {
    return super.getTimeLimit();
  }

  @Override
  public int getPlayer1Score() {
    return player1Score;
  }

  @Override
  public void setPlayer1Score(int score) {
    this.player1Score = score;
  }

  @Override
  public int getPlayer2Score() {
    return player2Score;
  }

  @Override
  public void setPlayer2Score(int score) {
    this.player2Score = score;
  }

  @Override
  public void nextPlayerTurn() {
    // TODO: Implement next player turn behaviour
  }

  @Override
  protected SubwayGameActivity getActivity() {
    return (SubwayGameActivity) super.getActivity();
  }

  protected void startGame() {
    // create 60 second timer
    new CountDownTimer(getTimeLimit(), 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        //        System.out.println("Timer is ticking! " + millisUntilFinished);
        // check for collisions every second
        checkCollision();
        // move all obstacles down every second
        getActivity().moveDown();
        // create new obstacle every 4 seconds
        double nearestThousand = Math.ceil(millisUntilFinished / 1000) * 1000;
        if (nearestThousand % 4000 == 0) {
          createObstacle();
        }

        updateTime(millisUntilFinished);
      }

      @Override
      public void onFinish() {
        endGame();
      }
    }.start();
  }

  private void updateTime(long timeLeft) {
    ((TextView) getActivity().findViewById(R.id.timeleft)).setText("Time Left: " + timeLeft / 1000);
  }

  /** create a new obstacle and add it to obstacles ArrayList in SubwayGameActivity */
  private void createObstacle() {
    //    System.out.println("obstacle created");
    ImageView newObstacle = new ImageView(getActivity());
    newObstacle.setImageResource(R.drawable.circle_card);
    // set obstacle position
    getActivity().obstacles.add(newObstacle);
    ((ConstraintLayout) getActivity().findViewById(R.id.Layout)).addView(newObstacle);
    setSize(newObstacle);
    setPosition(newObstacle);
  }

  private void setSize(ImageView newObstacle) {
    newObstacle.getLayoutParams().height = 70;
    newObstacle.getLayoutParams().width = 70;
  }
  /** Set location of the new obstacle */
  private void setPosition(ImageView newObstacle) {
    // set y variable to 0
    newObstacle.setY(0);
    // randomly pick a lane
    int obstacleLane = pickLane();
    // set obstacle's x position based on the lane
    if (obstacleLane == 1) {
      newObstacle.setX(160);
    } else if (obstacleLane == 2) {
      newObstacle.setX(500);
    } else {
      newObstacle.setX(860); // lane 3
    }
  }

  /** randomly determine which lane the new obstacle is in */
  private int pickLane() {
    return (int) (Math.random() * 4);
  }

  /**
   * check if runner and obstacle are in the same position and decrease player1Score by 1 if they
   * are
   */
  private void checkCollision() {
    // loop through obstacles
    for (int i = 0; i < getActivity().obstacles.size(); i++) {
      // get position of obstacle
      View obstacle = getActivity().obstacles.get(i);
      float obstacleX = obstacle.getX();
      float obstacleY = obstacle.getY();
      // check if runner and obstacle are in the same lane
      boolean sameLane = checkLane(obstacleX);
      //      System.out.println("sameLane is " + sameLane);
      // check if runner and obstacle have the same y coordinate
      boolean sameY = checkCoordY(obstacleY);
      //      System.out.println("sameY is " + sameY);
      if (sameLane && sameY)
        // decrease player1Score
        decreaseScore();
      getActivity().updateScore(player1Score);
    }
  }

  private boolean checkCoordY(float obstacleY) {
    return (obstacleY == 1200);
  }

  private boolean checkLane(float obstacleX) {
    if (getActivity().runnerLane == 1 && obstacleX == 160) { // if both are in lane 1
      return true;
    } else if (getActivity().runnerLane == 2 && obstacleX == 500) { // if both are in lane 2
      return true;
    } else if (getActivity().runnerLane == 3 && obstacleX == 860) { // if both are in lane 3
      return true;
    } else return false;
  }

  /** decrease the player1Score by 1 */
  private void decreaseScore() {
    if (this.player1Score > 0) {
      this.player1Score -= 1;
      System.out.println("Current player1Score is: " + this.player1Score);
    }
  }

  @Override
  protected void endGame() {
    System.out.println("Game Over!");
    System.out.println("Final player1Score is: " + player1Score);
    this.getAppManager().getCurrentPlayer().setCurrentGameScore(this.player1Score);
    this.getActivity().leaveGame(this.getAppManager());
  }

  /** A wrapper method to implement abstract method from Game */
  public void updateGame() {
    startGame();
  }
}

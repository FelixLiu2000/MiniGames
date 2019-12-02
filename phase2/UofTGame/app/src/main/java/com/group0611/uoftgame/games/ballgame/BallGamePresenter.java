package com.group0611.uoftgame.games.ballgame;

import android.os.CountDownTimer;
import android.view.View;

import com.group0611.uoftgame.activities.BallGameActivity;

public class BallGamePresenter {
  private BallGame game;
  private BallGameActivity activity;
  private CountDownTimer gameTimer;

  public BallGamePresenter(BallGame game, BallGameActivity activity) {
    this.game = game;
    bindActivity(activity);
  }

  public void bindActivity(BallGameActivity activity) {
    this.activity = activity;
  }

  public void unbindActivity() {
    this.activity = null;
  }

  public CountDownTimer getGameTimer() { return this.gameTimer; }

  public void initializePlayer(View view) {
    Player[] players;
    if (game.getUsesMultiplayerGameMode()) {
      players = new Player[2];
      players[1] = new Player(view.getX(), view.getY());
    } else {
      players = new Player[1];
    }
    players[0] = new Player(view.getX(), view.getY());
    game.setPlayers(players);
  }

  public void initializeTarget(View view) {
    Target target =
        new Target(
            view.getX(), view.getY(), view.getLayoutParams().width, view.getLayoutParams().height);
    game.setTarget(target);
  }

  public void initializeActiveArea(int left, int top, int right, int bottom) {
    game.setActiveArea(left, top, right, bottom);
  }

  private void initializeOutputViews() {
    setViewVisibilities();
    renderPower();
    renderAngle();
    renderTime();
    renderScore();
    renderLives();
    renderCurrentPlayerName();
  }

  public void initializeNewBall(View view) {
    Ball newBall = game.shootBall(view.getLayoutParams().width, view.getLayoutParams().height);
    activity.updateViewLocation(view, newBall.getX(), newBall.getY());
  }

  public void updateShotAngle(int angle) {
    game.setShotAngle(angle);
    renderAngle();
  }

  public void updateShotPower(int power) {
    game.setShotPower(power);
    renderPower();
  }

  private void renderScore() {
    activity.updateTextView(
        activity.getPlayer1ScoreView(), "P1 Score: " + game.getPlayer(1).getScore());
    if (game.getUsesMultiplayerGameMode()) {
      activity.updateTextView(
          activity.getPlayer2ScoreView(), "P2 Score: " + game.getPlayer(2).getScore());
    }
  }

  private void renderAngle() {
    activity.updateTextView(
        activity.getAngleView(),
        "Angle: " + game.getPlayer(game.getCurrentPlayerNumber()).getShotAngle());
  }

  private void renderPower() {
    activity.updateTextView(
        activity.getPowerView(),
        "Power: " + game.getPlayer(game.getCurrentPlayerNumber()).getShotPower());
  }

  private void renderTime() {
    if (game.getUsesTimedGameMode()) {
      activity.updateTextView(activity.getTimeView(), "Time: " + game.getTimeRemaining());
    }
  }

  private void renderLives() {
    if (game.getUsesLivesGameMode()) {
      activity.updateTextView(
          activity.getPlayer1LivesView(), "P1 Lives: " + game.getPlayer(1).getRemainingLives());
      if (game.getUsesMultiplayerGameMode()) {
        activity.updateTextView(
            activity.getPlayer2LivesView(), "P2 Lives: " + game.getPlayer(2).getRemainingLives());
      }
    }
  }

  private void renderCurrentPlayerName() {
    activity.updateTextView(
        activity.getPlayerNameView(),
        String.format(
            "Current Player: %s (PLAYER %d)",
            game.getPlayer(game.getCurrentPlayerNumber()).getUsername(),
            game.getCurrentPlayerNumber()));
  }

  private void setViewVisibilities() {
    if (game.getUsesMultiplayerGameMode()) {
      activity.getPlayer2ScoreView().setVisibility(View.VISIBLE);
    }
    if (game.getUsesLivesGameMode()) {
      activity.getPlayer1LivesView().setVisibility(View.VISIBLE);
      if (game.getUsesMultiplayerGameMode()) {
        activity.getPlayer2LivesView().setVisibility(View.VISIBLE);
      }
    }
    if (game.getUsesTimedGameMode()) {
      activity.getTimeView().setVisibility(View.VISIBLE);
    }
  }

  private void renderBallObjects() {
    for (int i = 0; i < game.getActiveBallObjects().size(); i++) {
      Ball currentBall = game.getActiveBallObjects().get(i);
      activity.updateViewLocation(
          activity.getBallViews().get(i), currentBall.getX(), currentBall.getY());
    }
  }

  private void gameLoop() {
    final int FPS = 60;
    final long TIMER_REFRESH = 1000 / FPS;
    int timerDurationInSeconds = game.getTimeLimit();
    // If game has no time limit, set a long duration for timer (timer will loop on completion)
    if (!game.getUsesTimedGameMode()) {
      timerDurationInSeconds = 1000000;
    }
    gameTimer =
        new CountDownTimer(timerDurationInSeconds * 1000, TIMER_REFRESH) {
          @Override
          public void onTick(long millisRemaining) {
            performGameOperations();
            if (game.getUsesLivesGameMode() && game.isOutOfLives()) {
              cancel(); // Cancels this timer and closes its threads
              if (game.getUsesMultiplayerGameMode() && game.getCurrentPlayerNumber() == 1) {
                // Restart timer and trigger next turn
                triggerNextTurn();
                // Restart countdown timer
                gameLoop();
              } else {
                game.endGame();
              }
            }
            // Update time remaining
            if (game.getUsesTimedGameMode()) {
              game.setTimeRemaining((int)(millisRemaining/1000));
            }
          }

          @Override
          public void onFinish() {
            // Restart timer if game has no time limit
            if (!game.getUsesTimedGameMode()) {
              start();
              // If the game has completed player 1's turn and can advance to player 2's turn
            } else if (game.getUsesMultiplayerGameMode() && game.getCurrentPlayerNumber() == 1) {
              triggerNextTurn();
              start();
            } else {
              // Still call next turn to give player bonus points, but end game afterwards
              triggerNextTurn();
              game.endGame();
            }
          }
        }.start();
  }

  private void performGameOperations() {
    game.updateMovements();
    game.updateCollisions();
    renderBallObjects();
    destroyInactiveBalls();
    renderScore();
    renderLives();
    renderTime();
  }

  private void triggerNextTurn() {
    game.nextPlayerTurn();
    // Reset angle and power seekbars to player's last values
    activity
        .getPowerControlView()
        .setProgress(game.getPlayer(game.getCurrentPlayerNumber()).getShotPower());
    activity
        .getAngleControlView()
        .setProgress(game.getPlayer(game.getCurrentPlayerNumber()).getShotPower());
    // Display new player's name
    renderCurrentPlayerName();
  }

  public void startGame() {
    initializeOutputViews();
    game.startGame();
    gameLoop();
  }

  private void destroyInactiveBalls() {
    for (int index : game.getInactiveBallIndices()) {
      activity.removeViewFromLayout(activity.getBallViews().get(index), activity.getBallLayout());
      activity.getBallViews().remove(index);
    }
    game.getInactiveBallIndices().clear();
  }
}

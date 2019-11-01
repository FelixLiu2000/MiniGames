package com.example.game.utilities;

import android.content.Context;
import android.graphics.Color;

import com.example.game.activities.BallGameActivity;
import com.example.game.activities.CardGameActivity;
import com.example.game.activities.SubwayGameActivity;

import java.io.Serializable;

public class AppManager implements Serializable {

  Context logInContext;
  private Class gameToPlay;
  private Player currentPlayer;

  public AppManager(Context context) {
    this.logInContext = context;
  }

  public void setGameToPlay(Class gameToPlay) { this.gameToPlay = gameToPlay; }
  public Class getGameToPlay() { return this.gameToPlay; }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public void createPlayer(String firstName, String lastName, String userName, String password) {
    this.currentPlayer = new Player(firstName, lastName, userName, password);
  }

  //TODO: RESET
  public void pickGameToPlay() {
    if (this.currentPlayer.getCurrentRoundProgress() == 0) {
      setGameToPlay(SubwayGameActivity.class);
    } else if (this.currentPlayer.getCurrentRoundProgress() == 1) {
      setGameToPlay(CardGameActivity.class);
    } else if (this.currentPlayer.getCurrentRoundProgress() == 2) {
      setGameToPlay(BallGameActivity.class);
    } else if (this.currentPlayer.getCurrentRoundProgress() == 3) {
      // reset
    }
  }

  //TODO: TIMER CHOICE
  public void saveCustomizationChanges(String gameDashboardBackgroundColor, String gameDashboardDisplayName) {
    int chosenColorInt = this.currentPlayer.getGameDashboardBackgroundColor();
    if (gameDashboardBackgroundColor.equals("WHITE")){
      chosenColorInt = Color.WHITE;
    } else if (gameDashboardBackgroundColor.equals("RED")){
      chosenColorInt = Color.RED;
    } else if (gameDashboardBackgroundColor.equals("GREEN")) {
      chosenColorInt = Color.GREEN;
    } else if (gameDashboardBackgroundColor.equals("BLUE")) {
      chosenColorInt = Color.BLUE;
    } else if (gameDashboardBackgroundColor.equals("YELLOW")) {
      chosenColorInt = Color.YELLOW;
    }
    this.currentPlayer.setGameDashboardBackgroundColor(chosenColorInt);
    this.currentPlayer.setCurrentDisplayNameChoice(gameDashboardDisplayName);

  }

  public String getCurrentPlayerCurrentDashboardColor() {
    int currentColor = currentPlayer.getGameDashboardBackgroundColor();
    if (currentColor == Color.WHITE) {
      return "WHITE";
    } else if (currentColor == Color.RED) {
      return "RED";
    } else if (currentColor == Color.GREEN) {
      return "GREEN";
    } else if (currentColor == Color.BLUE) {
      return "BLUE";
    } else if (currentColor == Color.YELLOW) {
      return "YELLOW";
    }
    return "WHITE";
  }

  public String getCurrentPlayerDisplayName() {
    return currentPlayer.getCurrentDisplayNameChoice();
  }

  public void updatePlayerTotalScore() {
    int updatedScore = this.currentPlayer.getTotalScore() + this.currentPlayer.getCurrentGameScore();
    this.currentPlayer.setTotalScore(updatedScore);
  }

  public void updatePlayerRoundProgress() {
    int updatedRoundProgress = this.currentPlayer.getCurrentRoundProgress() + 1;
    this.currentPlayer.setCurrentRoundProgress(updatedRoundProgress);
  }

  public void updatePlayerRoundScore() {
    int updatedRoundScore = this.currentPlayer.getCurrentRoundScore() + this.currentPlayer.getCurrentGameScore();
    this.currentPlayer.setCurrentRoundScore(updatedRoundScore);
  }

  public void updatePlayerTotalRounds() {
    int updatedTotalRounds = this.currentPlayer.getTotalRoundsPlayed() + 1;
    this.currentPlayer.setTotalRoundsPlayed(updatedTotalRounds);
  }

  public void updatePlayerHighScore() {
    int currentHighScore = this.currentPlayer.getHighScore();
    int currentRoundFinalScore = this.currentPlayer.getCurrentRoundScore();
    if (currentRoundFinalScore > currentHighScore) {
      this.currentPlayer.setHighScore(currentRoundFinalScore);
    }
  }
}

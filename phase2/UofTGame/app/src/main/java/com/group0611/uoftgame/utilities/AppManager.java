package com.group0611.uoftgame.utilities;

import android.content.Context;
import android.graphics.Color;

import com.group0611.uoftgame.activities.BallGameActivity;
import com.group0611.uoftgame.activities.CardGameActivity;
import com.group0611.uoftgame.activities.SubwayGameActivity;
import com.group0611.uoftgame.games.subwaygame.SubwayGame;

import java.io.Serializable;

public class AppManager implements Serializable {

  Context logInContext;
  private Class gameToPlay;
  private Player mainPlayer, playerOne, playerTwo;

  public AppManager(Context context) {
    this.logInContext = context;
  }

  public void setGameToPlay(Class gameToPlay) { this.gameToPlay = gameToPlay; }
  public Class getGameToPlay() { return this.gameToPlay; }

  public void setMainPlayer(Player mainPlayer) {
    this.mainPlayer = mainPlayer;
  }
  public Player getMainPlayer() {
    return this.mainPlayer;
  }

  public void createPlayer(String firstName, String lastName, String userName, String password) {
    this.mainPlayer = new Player(firstName, lastName, userName, password);
  }

  //TODO: RESET
  public void pickGameToPlay() {
    if (this.mainPlayer.getCurrentRoundProgress() == 0) {
      setGameToPlay(SubwayGameActivity.class);
    } else if (this.mainPlayer.getCurrentRoundProgress() == 1) {
      setGameToPlay(CardGameActivity.class);
    } else if (this.mainPlayer.getCurrentRoundProgress() == 2) {
      setGameToPlay(BallGameActivity.class);
    } else if (this.mainPlayer.getCurrentRoundProgress() == 3) {
      // reset
    }
  }

  //TODO: TIMER CHOICE
  public void saveCustomizationChanges(String gameDashboardBackgroundColor, String gameDashboardDisplayName, String chosenDifficulty) {
    int chosenColorInt = this.mainPlayer.getGameDashboardBackgroundColor();
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
    this.mainPlayer.setGameDashboardBackgroundColor(chosenColorInt);
    this.mainPlayer.setCurrentDisplayNameChoice(gameDashboardDisplayName);
    this.mainPlayer.setDifficulty(chosenDifficulty);
    this.mainPlayer.setTimeChoice(chooseTimeChoice());

  }

  private int[] chooseTimeChoice() {
    if (mainPlayer.getDifficulty().equals("EASY")) {
      return mainPlayer.getEasyTimes();
    } else if (mainPlayer.getDifficulty().equals("MEDIUM")) {
      return mainPlayer.getMediumTimes();
    } else if (mainPlayer.getDifficulty().equals("HARD")) {
      return mainPlayer.getHardTimes();
    }
    return mainPlayer.getEasyTimes();
  }

  public String getMainPlayerCurrentDashboardColor() {
    int currentColor = mainPlayer.getGameDashboardBackgroundColor();
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

  public String getMainPlayerDisplayName() {
    return mainPlayer.getCurrentDisplayNameChoice();
  }

  public String getMainPlayerDifficulty() {
    return mainPlayer.getDifficulty();
  }

  public void updatePlayerTotalScore() {
    int updatedScore = this.mainPlayer.getTotalScore() + this.mainPlayer.getCurrentGameScore();
    this.mainPlayer.setTotalScore(updatedScore);
  }

  public void updatePlayerRoundProgress() {
    int updatedRoundProgress = this.mainPlayer.getCurrentRoundProgress() + 1;
    this.mainPlayer.setCurrentRoundProgress(updatedRoundProgress);
  }

  public void updatePlayerRoundScore() {
    int updatedRoundScore = this.mainPlayer.getCurrentRoundScore() + this.mainPlayer.getCurrentGameScore();
    this.mainPlayer.setCurrentRoundScore(updatedRoundScore);
  }

  public void updatePlayerTotalRounds() {
    int updatedTotalRounds = this.mainPlayer.getTotalRoundsPlayed() + 1;
    this.mainPlayer.setTotalRoundsPlayed(updatedTotalRounds);
  }

  public boolean updatePlayerHighScore() {
    int currentHighScore = this.mainPlayer.getHighScore();
    int currentRoundFinalScore = this.mainPlayer.getCurrentRoundScore();
    if (currentRoundFinalScore > currentHighScore) {
      this.mainPlayer.setHighScore(currentRoundFinalScore);
      return true;
    }
    return false;
  }
}

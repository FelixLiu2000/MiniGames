package com.example.game.utilities;

import android.content.Context;
import android.graphics.Color;

import com.example.game.activities.BallGameActivity;
import com.example.game.activities.CardGameActivity;

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

  public void pickGameToPlay() {
    if (this.currentPlayer.getCurrentRoundProgress() == 0) {
      //setGameToPlay(SubwayGameActivity.class);
    } else if (this.currentPlayer.getCurrentRoundProgress() == 1) {
      setGameToPlay(CardGameActivity.class);
    } else if (this.currentPlayer.getCurrentRoundProgress() == 2) {
      setGameToPlay(BallGameActivity.class);
    } else if (this.currentPlayer.getCurrentRoundProgress() == 3) {
      // reset
    }
  }

  public void saveCustomizationChanges(String gameDashboardBackgroundColor) {
    int chosenColorInt = this.currentPlayer.getGameDashboardBackgroundColor();
    if (gameDashboardBackgroundColor == "WHITE"){
      chosenColorInt = Color.WHITE;
    } else if (gameDashboardBackgroundColor == "RED"){
      chosenColorInt = Color.RED;
    } else if (gameDashboardBackgroundColor.equals("GREEN")) {
      chosenColorInt = Color.GREEN;
    } else if (gameDashboardBackgroundColor == "BLUE") {
      chosenColorInt = Color.BLUE;
    } else if (gameDashboardBackgroundColor == "YELLOW") {
      chosenColorInt = Color.YELLOW;
    }

    this.currentPlayer.setGameDashboardBackgroundColor(chosenColorInt);

  }
}

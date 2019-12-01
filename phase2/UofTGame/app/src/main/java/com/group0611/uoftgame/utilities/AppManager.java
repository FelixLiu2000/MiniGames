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
  private Player currentPlayer, playerOne, playerTwo;
  private boolean gameIsMultiPlayer, comingFromAddPlayer;
  public AppManager(Context context) {
    this.logInContext = context;
    this.currentPlayer = null;
    this.playerOne = null;
    this.playerTwo = null;
  }

  public void setGameToPlay(Class gameToPlay) { this.gameToPlay = gameToPlay; }
  public Class getGameToPlay() { return this.gameToPlay; }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public void setPlayerOne (Player playerOne) { this.playerOne = playerOne;}
  public Player getPlayerOne() { return playerOne;}

  public void setPlayerTwo (Player playerTwo) { this.playerTwo = playerTwo; }
  public Player getPlayerTwo() { return playerTwo; }

  public void setComingFromAddPlayer (boolean comingFromAddPlayer) { this.comingFromAddPlayer = comingFromAddPlayer; }
  public boolean getComingFromAddPlayer () { return comingFromAddPlayer; }

  public void setGameIsMultiPlayer (boolean gameIsMultiPlayer) { this.gameIsMultiPlayer = gameIsMultiPlayer; }
  public boolean getGameIsMultiPlayer () { return gameIsMultiPlayer; }

  public Player createPlayer(String firstName, String lastName, String userName, String password) {
    return (new Player(firstName, lastName, userName, password));
  }

  public void saveCustomizationChanges(String gameDashboardBackgroundColor, DisplayNameChoices gameDashboardDisplayName, GameDifficulty chosenDifficulty) {
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
    this.currentPlayer.setGameDifficulty(chosenDifficulty);
    this.currentPlayer.setDisplayNameChoice(gameDashboardDisplayName);

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

  public DisplayNameChoices getCurrentPlayerDisplayName() {
    return currentPlayer.getDisplayNameChoice();
  }

  public GameDifficulty getCurrentPlayerDifficulty() {
    return currentPlayer.getGameDifficulty();
  }

  public GameMode getCurrentPlayerGameMode() { return currentPlayer.getGameMode(); }

  public void updatePlayerTotalScore() {
  }

  public void updatePlayerRoundScore() {
  }

  public void updatePlayerTotalRounds() {
  }

  public void updatePlayerHighScore() {
  }
}

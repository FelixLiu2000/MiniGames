package com.group0611.uoftgame.utilities;

import android.content.Context;
import android.graphics.Color;

import com.group0611.uoftgame.activities.BallGameActivity;
import com.group0611.uoftgame.activities.CardGameActivity;
import com.group0611.uoftgame.activities.SubwayGameActivity;
import com.group0611.uoftgame.games.subwaygame.SubwayGame;

import java.io.Serializable;
import java.util.HashMap;

public class AppManager implements Serializable {

  Context logInContext;
  private Class gameToPlay;
  private Player currentPlayer, playerOne, playerTwo;
  private boolean gameIsMultiPlayer, comingFromAddPlayer, currentPlayerIsPlayerTwo;
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

  public void setCurrentPlayerIsPlayerTwo (boolean currentPlayerIsPlayerTwo) { this.currentPlayerIsPlayerTwo = currentPlayerIsPlayerTwo; }
  public boolean getCurrentPlayerIsPlayerTwo () { return currentPlayerIsPlayerTwo; }

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

  public void switchCurrentPlayer() {
    if (currentPlayerIsPlayerTwo) {
      currentPlayer = playerOne;
    } else {
      currentPlayer = playerTwo;
    }
    currentPlayerIsPlayerTwo = !currentPlayerIsPlayerTwo;
  }


  public void updatePlayerTotalScore(Player player, int score) {
    player.setTotalScore(player.getTotalScore() + score);
  }

  public void updatePlayerTotalGamesPlayed(Player player) {
    player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1 );
  }

  public void updatePlayerHighScore(Player player, int score) {
    if (score > player.getHighScore()) {
      player.setHighScore(score);
    }
  }

  public void updatePlayerMainStats(Player player, int score) {
    updatePlayerTotalScore(player, score);
    updatePlayerTotalGamesPlayed(player);
    updatePlayerHighScore(player, score);
  }

  public void updatePlayerCardGameStats(Player player, int totalScore, int totalMatches, int totalMisMatches, int totalMatchAttempts) {
    updatePlayerMainStats(player, totalScore);
    HashMap<String, Integer> playerStats = player.getCardGameStats();
    playerStats.put("Total Times Played", playerStats.get("Total Times Played") + 1 );
    playerStats.put("Total Score", playerStats.get("Total Score") + totalScore);
    playerStats.put("Total Match Attempts", playerStats.get("Total Match Attempts") + totalMatchAttempts);
    playerStats.put("Total Mismatches", playerStats.get("Total Mismatches") + totalMisMatches);
    playerStats.put("Total Matches", playerStats.get("Total Matches") + totalMatches);
    if (totalScore > playerStats.get("High Score")) {
      playerStats.put("High Score", totalScore);
    }

    player.setCardGameStats(playerStats);
    SaveManager.save(player);
  }

  public void updatePlayerSubwayGameStats(Player player, int totalScore, int totalCoins,  int totalObstaclesHit){
    updatePlayerMainStats(player, totalCoins);
    HashMap<String, Integer> playerStats = player.getSubwayGameStats();
    playerStats.put("Total Times Played", playerStats.get("Total Times Played") + 1 );
    playerStats.put("Total Score", playerStats.get("Total Score") + totalScore);
    playerStats.put("Total Coins", playerStats.get("Total Coins") + totalCoins);
    playerStats.put("Total Obstacle Hits", playerStats.get("Total Obstacle Hits") + totalObstaclesHit);
    if (totalScore > playerStats.get("High Score")) {
      playerStats.put("High Score", totalScore);
    }

    player.setSubwayGameStats(playerStats);
    SaveManager.save(player);
  }

  public void updatePlayerBallGameStats(Player player, int totalScore, int totalThrows, int totalHits, int totalMisses) {
    updatePlayerMainStats(player, totalScore);
    HashMap<String, Integer> playerStats = player.getBallGameStats();
    playerStats.put("Total Times Played", playerStats.get("Total Times Played") + 1 );
    playerStats.put("Total Score", playerStats.get("Total Score") + totalScore);
    playerStats.put("Total Hits", playerStats.get("Total Hits") + totalHits);
    playerStats.put("Total Misses", playerStats.get("Total Misses") + totalMisses);
    playerStats.put("Total Throws", playerStats.get("Total Throws") + totalThrows);
    if (totalScore > playerStats.get("High Score")) {
      playerStats.put("High Score", totalScore);
    }

    player.setBallGameStats(playerStats);
    SaveManager.save(player);
  }

  public void updateTwoPlayerStats(boolean playerOneWon) {
    if (playerOneWon) {
      playerOne.getTwoPlayerStats().put("Total Wins", playerOne.getTwoPlayerStats().get("Total wins") + 1);
      playerTwo.getTwoPlayerStats().put("Total Losses", playerTwo.getTwoPlayerStats().get("Total Losses") + 1);
    } else {
      playerOne.getTwoPlayerStats().put("Total Losses", playerOne.getTwoPlayerStats().get("Total Losses") + 1);
      playerTwo.getTwoPlayerStats().put("Total Wins", playerTwo.getTwoPlayerStats().get("Total Wins") + 1);
    }
    playerOne.getTwoPlayerStats().put("Total Two Player Games", playerOne.getTwoPlayerStats().get("Total Two Player Games") + 1);
    playerTwo.getTwoPlayerStats().put("Total Two Player Games", playerTwo.getTwoPlayerStats().get("Total Two Player Games") + 1);
  }
}

package com.group0611.uoftgame.utilities;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class Player implements Serializable {

  private String firstName, lastName, username, password;
  private int totalScore, highScore, gameDashboardBackgroundColor, totalGamesPlayed;
  private GameDifficulty gameDifficulty;
  private DisplayNameChoices displayNameChoice;
  private GameMode gameMode;
  private HashMap<String, Integer> cardGameStats, ballGameStats, subwayGameStats, twoPlayerStats;


  Player(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.displayNameChoice = DisplayNameChoices.USERNAME;
    this.totalScore = 0;
    this.highScore = 0;
    this.totalGamesPlayed = 0;
    this.gameDashboardBackgroundColor = Color.WHITE;
    this.gameDifficulty = GameDifficulty.EASY;
    this.gameMode = GameMode.TIMED;
    this.cardGameStats = new HashMap<String, Integer>();
    this.subwayGameStats = new HashMap<String, Integer>();
    this.ballGameStats = new HashMap<String, Integer>();
    this.twoPlayerStats = new HashMap<String, Integer>();
    this.cardGameStats.put("Total Score", 0);
    this.cardGameStats.put("Total Match Attempts", 0);
    this.cardGameStats.put("High Score", 0);
    this.cardGameStats.put("Total Mismatches", 0);
    this.cardGameStats.put("Total Matches", 0);
    this.cardGameStats.put("Total Times Played", 0);
    this.ballGameStats.put("Total Score", 0);
    this.ballGameStats.put("High Score", 0);
    this.ballGameStats.put("Total Hits", 0);
    this.ballGameStats.put("Total Throws", 0);
    this.ballGameStats.put("Total Misses", 0);
    this.ballGameStats.put("Total Times Played", 0);
    this.subwayGameStats.put("Total Score", 0);
    this.subwayGameStats.put("Total Coins", 0);
    this.subwayGameStats.put("High Score", 0);
    this.subwayGameStats.put("Total Obstacle Hits", 0);
    this.subwayGameStats.put("Total Times Played", 0);
    this.twoPlayerStats.put("Total Two Player Games", 0);
    this.twoPlayerStats.put("Total Wins", 0);
    this.twoPlayerStats.put("Total Losses", 0);
  }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) {this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) {this.lastName = lastName; }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public DisplayNameChoices getDisplayNameChoice() { return this.displayNameChoice; }
  public void setDisplayNameChoice(DisplayNameChoices displayNameChoice) { this.displayNameChoice = displayNameChoice; }

  public int getTotalScore() {
    return totalScore;
  }
  public void setTotalScore(int totalScore) {
    this.totalScore = totalScore;
  }

  public int getHighScore() {
    return highScore;
  }
  public void setHighScore(int highScore) {
    this.highScore = highScore;
  }

  public int getTotalGamesPlayed() { return totalGamesPlayed; }
  public void setTotalGamesPlayed(int totalGamesPlayed) { this.totalGamesPlayed = totalGamesPlayed; }

  public int getGameDashboardBackgroundColor() { return this.gameDashboardBackgroundColor; }
  public void setGameDashboardBackgroundColor(int newGameDashboardBackgroundColor) { this.gameDashboardBackgroundColor = newGameDashboardBackgroundColor; }

  public GameDifficulty getGameDifficulty() { return gameDifficulty; }
  public void setGameDifficulty(GameDifficulty gameDifficulty) {this.gameDifficulty = gameDifficulty; }

  public GameMode getGameMode() { return gameMode; }
  public void setGameMode (GameMode gameMode) { this.gameMode = gameMode; }

  public HashMap<String, Integer> getBallGameStats() { return ballGameStats; }
  public void setBallGameStats(HashMap<String, Integer> ballGameStats) { this.ballGameStats = ballGameStats; }

  public HashMap<String, Integer> getCardGameStats() { return cardGameStats; }
  public void setCardGameStats(HashMap<String, Integer> cardGameStats) { this.cardGameStats = cardGameStats; }

  public HashMap<String, Integer> getSubwayGameStats() { return subwayGameStats; }
  public void setSubwayGameStats(HashMap<String, Integer> subwayGameStats) { this.subwayGameStats = subwayGameStats; }

  public HashMap<String, Integer> getTwoPlayerStats() { return twoPlayerStats; }
  public void setTwoPlayerStats(HashMap<String, Integer> twoPlayerStats) { this.twoPlayerStats = twoPlayerStats; }
}

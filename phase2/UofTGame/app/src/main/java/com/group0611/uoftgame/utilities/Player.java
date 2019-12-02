package com.group0611.uoftgame.utilities;

import android.graphics.Color;

import java.io.InterruptedIOException;
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
  private HashMap<String, Integer> cardGameStats, ballGameStats, subwayGameStats, twoPlayerStats, previousGameStats;


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
    this.previousGameStats = new HashMap<String, Integer>();
    HashMapHelper.multiKeyPut(this.cardGameStats, new String[]{"Total Score", "Total Match Attempts",
            "High Score", "Total Mismatches", "Total Matches", "Total Times Played"}, 0);
    HashMapHelper.multiKeyPut(this.ballGameStats, new String[]{"Total Score", "High Score", "Total Hits",
            "Total Throws", "Total Misses", "Total Times Played"}, 0);
    HashMapHelper.multiKeyPut(this.subwayGameStats, new String[]{"Total Score", "Total Coins", "High Score",
            "Total Obstacle Hits", "Total Times Played"}, 0);
    HashMapHelper.multiKeyPut(this.twoPlayerStats, new String[]{"Total Two Player Games", "Total Wins",
            "Total Losses"}, 0);
    HashMapHelper.multiKeyPut(this.previousGameStats, new String[]{"Total Score", "High Score",
            "Total Attempts", "Total Successes", "Total Failures", "Game ID", "Winner"}, 0);
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

  DisplayNameChoices getDisplayNameChoice() { return this.displayNameChoice; }
  void setDisplayNameChoice(DisplayNameChoices displayNameChoice) { this.displayNameChoice = displayNameChoice; }

  public int getTotalScore() {
    return totalScore;
  }
  void setTotalScore(int totalScore) {
    this.totalScore = totalScore;
  }

  public int getHighScore() {
    return highScore;
  }
  void setHighScore(int highScore) {
    this.highScore = highScore;
  }

  public int getTotalGamesPlayed() { return totalGamesPlayed; }
  public void setTotalGamesPlayed(int totalGamesPlayed) { this.totalGamesPlayed = totalGamesPlayed; }

  public int getGameDashboardBackgroundColor() { return this.gameDashboardBackgroundColor; }
  void setGameDashboardBackgroundColor(int newGameDashboardBackgroundColor) { this.gameDashboardBackgroundColor = newGameDashboardBackgroundColor; }

  GameDifficulty getGameDifficulty() { return gameDifficulty; }
  void setGameDifficulty(GameDifficulty gameDifficulty) {this.gameDifficulty = gameDifficulty; }

  public GameMode getGameMode() { return gameMode; }
  public void setGameMode (GameMode gameMode) { this.gameMode = gameMode; }

  public HashMap<String, Integer> getBallGameStats() { return ballGameStats; }
  void setBallGameStats(HashMap<String, Integer> ballGameStats) { this.ballGameStats = ballGameStats; }

  public HashMap<String, Integer> getCardGameStats() { return cardGameStats; }
  void setCardGameStats(HashMap<String, Integer> cardGameStats) { this.cardGameStats = cardGameStats; }

  public HashMap<String, Integer> getSubwayGameStats() { return subwayGameStats; }
  void setSubwayGameStats(HashMap<String, Integer> subwayGameStats) { this.subwayGameStats = subwayGameStats; }

  public HashMap<String, Integer> getTwoPlayerStats() { return twoPlayerStats; }
  public void setTwoPlayerStats(HashMap<String, Integer> twoPlayerStats) { this.twoPlayerStats = twoPlayerStats; }

  public HashMap<String, Integer> getPreviousGameStats() { return previousGameStats; }
  void setPreviousGameStats(HashMap<String, Integer> previousGameStats) { this.previousGameStats = previousGameStats; }
}

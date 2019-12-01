package com.group0611.uoftgame.utilities;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

  private String firstName, lastName, username, password;
  private int totalScore, highScore, totalRoundsPlayed, currentRoundProgress,
          currentRoundScore, gameDashboardBackgroundColor, currentGameScore;
  private GameDifficulty gameDifficulty;
  private DisplayNameChoices displayNameChoice;

  Player(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.displayNameChoice = DisplayNameChoices.USERNAME;
    this.totalScore = 0;
    this.highScore = 0;
    this.totalRoundsPlayed = 0;
    this.currentRoundProgress = 0;
    this.currentRoundScore = 0;
    this.gameDashboardBackgroundColor = Color.WHITE;
    this.currentGameScore = 0;
    this.gameDifficulty = GameDifficulty.EASY;
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

  public int getTotalRoundsPlayed() {
    return totalRoundsPlayed;
  }
  void setTotalRoundsPlayed(int roundPlayed) {
    this.totalRoundsPlayed = roundPlayed;
  }

  public int getCurrentRoundProgress() {
    return currentRoundProgress;
  }
  public void setCurrentRoundProgress(int currentGameRounds) { this.currentRoundProgress = currentGameRounds; }

  public int getCurrentRoundScore() {
    return currentRoundScore;
  }
  public void setCurrentRoundScore(int currentRoundScore) { this.currentRoundScore = currentRoundScore; }

  public int getGameDashboardBackgroundColor() { return this.gameDashboardBackgroundColor; }
  public void setGameDashboardBackgroundColor(int newGameDashboardBackgroundColor) { this.gameDashboardBackgroundColor = newGameDashboardBackgroundColor; }

  public int getCurrentGameScore() { return this.currentGameScore; }
  public void setCurrentGameScore(int currentGameScore) { this.currentGameScore = currentGameScore; }

  public GameDifficulty getGameDifficulty() { return gameDifficulty; }
  public void setGameDifficulty(GameDifficulty gameDifficulty) {this.gameDifficulty = gameDifficulty; }
}

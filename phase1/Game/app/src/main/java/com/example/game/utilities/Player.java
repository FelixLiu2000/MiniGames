package com.example.game.utilities;

import android.graphics.Color;

import java.io.Serializable;

public class Player implements Serializable {
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private int totalScore;
  private int highScore;
  private int totalRoundsPlayed;
  private int currentRoundProgress;
  private int currentRoundScore;
  private int gameDashboardBackgroundColor;

  Player(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.totalScore = 0;
    this.highScore = 0;
    this.totalRoundsPlayed = 0;
    this.currentRoundProgress = 2;
    this.currentRoundScore = 0;
    this.gameDashboardBackgroundColor = Color.WHITE;
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

  public int getTotalRoundsPlayed() {
    return totalRoundsPlayed;
  }
  public void setTotalRoundsPlayed(int roundPlayed) {
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

  public void setGameDashboardBackgroundColor(int gameDashboardBackgroundColor) {
    this.gameDashboardBackgroundColor = gameDashboardBackgroundColor;
  }
}

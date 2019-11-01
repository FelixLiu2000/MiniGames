package com.example.game.utilities;

import android.graphics.Color;

import java.io.Serializable;

public class Player implements Serializable {

  private String firstName, lastName, username, password, currentDisplayNameChoice;
  private int totalScore, highScore, totalRoundsPlayed, currentRoundProgress,
          currentRoundScore, gameDashboardBackgroundColor, currentGameScore, timeChoice;

  Player(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.currentDisplayNameChoice = "USERNAME";
    this.totalScore = 0;
    this.highScore = 0;
    this.totalRoundsPlayed = 0;
    this.currentRoundProgress = 0;
    this.currentRoundScore = 0;
    this.gameDashboardBackgroundColor = Color.WHITE;
    this.currentGameScore = 0;
    this.timeChoice = 60000;
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

  public String getCurrentDisplayNameChoice() { return this.currentDisplayNameChoice; }
  public void setCurrentDisplayNameChoice(String newCurrentDisplayNameChoice) { this.currentDisplayNameChoice = newCurrentDisplayNameChoice; }

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
  public void setGameDashboardBackgroundColor(int newGameDashboardBackgroundColor) {
    this.gameDashboardBackgroundColor = newGameDashboardBackgroundColor;
  }

  public int getCurrentGameScore() { return this.currentGameScore; }
  public void setCurrentGameScore(int currentGameScore) { this.currentGameScore = currentGameScore; }

  public int getTimeChoice() { return this.timeChoice; }
  public void setTimeChoice(int timeChoice ) { this.timeChoice = timeChoice; }

}

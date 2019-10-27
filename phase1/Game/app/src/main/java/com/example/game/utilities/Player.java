package com.example.game.utilities;

import java.io.Serializable;

public class Player implements Serializable {
  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private int totalScore;
  private int highScore;
  private int totalRoundsPlayed;
  private int currentRoundProgress;
  private int currentRoundScore;

  public Player(String firstName, String lastName, String userName, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.password = password;
    this.totalScore = 10;
    this.highScore = 100;
    this.totalRoundsPlayed = 1000;
    this.currentRoundProgress = 0;
    this.currentRoundScore = 0;
  }

  public String getName() {
    return userName;
  }

  public void setName(String name) {
    this.userName = name;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getCurrentRoundProgress() {
    return currentRoundProgress;
  }

  public void setCurrentRoundProgress(int currentGameRounds) {
    this.currentRoundProgress = currentGameRounds;
  }

  public int getCurrentRoundScore() {
    return currentRoundScore;
  }

  public void setCurrentRoundScore(int currentRoundScore) {
    this.currentRoundScore = currentRoundScore;
  }
}

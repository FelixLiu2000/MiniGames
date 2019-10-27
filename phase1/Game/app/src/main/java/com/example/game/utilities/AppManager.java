package com.example.game.utilities;

import android.content.Context;

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
    Player currentPlayer = new Player(firstName, lastName, userName, password);
    this.currentPlayer = currentPlayer;
  }

  public void pickGameToPlay() {
    if (this.currentPlayer.getCurrentRoundProgress() == 0) {
      setGameToPlay(CardGameActivity.class);
    } else if (this.currentPlayer.getCurrentRoundProgress() == 1) {
      // call game 2
    } else if (this.currentPlayer.getCurrentRoundProgress() == 2) {
      // call game 3
    } else if (this.currentPlayer.getCurrentRoundProgress() == 3) {
      // reset
    }
  }
}

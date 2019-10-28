package com.example.game.utilities;

import android.content.Context;

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
}

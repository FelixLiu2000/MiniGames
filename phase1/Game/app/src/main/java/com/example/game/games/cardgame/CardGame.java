package com.example.game.games.cardgame;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.activities.CardGameActivity;
import com.example.game.games.Game;

public class CardGame extends Game { //  extends GameManager

  public CardGame(int timeLimit) {
    super(timeLimit);
  }

  public void setRoundStats() {
    // updates the stats during play
  }

  @Override
  public void createGameEnvironment() {
    // takes code from XML file
  }

  public void startGame() {
    setScore(0);
  }

  public void updateGame() {}

  public int play() {
    return 0;
  }

  protected void setScore() {}


  public boolean check(int card1, int card2) {
    // check if two cards are matches and increase total
    if (card1 == card2) {
      setScore(getScore() + 1);
    }
    return false;
  }

  public void endGame() {}
  //        return stats;
  //    }
}

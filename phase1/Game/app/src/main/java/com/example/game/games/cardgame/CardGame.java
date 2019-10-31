package com.example.game.games.cardgame;


import android.os.CountDownTimer;

import com.example.game.R;
import com.example.game.activities.CardGameActivity;
import com.example.game.games.Game;

import java.util.ArrayList;

public class CardGame extends Game {

  public CardGame(int timeLimit) {
    super(timeLimit);
  }

  private int cardsLeft = 12;

  @Override
  public void createGameEnvironment() {
    // takes code from XML file
  }

  public void startGame() {
    setScore(0);
  }

  public int play() {
    return 0;
  }

  public boolean check(int card1, int card2) {
    // check if two cards are matches and increase total
    if (card1 >= 200){
      card1 -= 100;
    } else if (card2 >= 200){
      card2 -= 100;
    }
    if (card1 == card2) {
      setScore(getScore() + 1);
      cardsLeft -= 2;
      return true;
    }
    return false;
  }

  public boolean boardEmpty(){
    return cardsLeft == 0;
  }

  public void resetGame(){
    cardsLeft = 12;
  }

}

package com.example.game.games.cardgame;

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

  /*    @Override
  public void startGame() {
      setScore(0);
      run(); // calls parent run function
  }*/

  //    public void run() {
  //    }
  @Override
  public void updateGame() {}

  @Override
  public void endGame() {}
  //    public int endRound() {
  //        return stats;
  //    }

  // TODO: INCORPORATE PLAY INTO STARTGAME()
  public void play() {
    // calls methods needed for play
  }

  public boolean check(int card1, int card2) {
    // check if two cards are matches
    return true;
  }

  public void flip(int card1) {
    // shows the other side of the card
  }
}

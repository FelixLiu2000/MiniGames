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

  public void startGame() {
    setScore(0);
  }

  /*    @Override
  public void startGame() {
      setScore(0);
      run(); // calls parent run function
  }*/

  //    public void run() {
  //    }
  public void updateGame() {}

  public void endGame() {}
  //    public int endRound() {
  //        return stats;
  //    }

  // TODO: INCORPORATE PLAY INTO STARTGAME()
  public int play() {
    // calls methods needed for play
    // can move this
    return 0;
  }

  public boolean check(int card1, int card2) {
    // check if two cards are matches and increase total
    if (card1 == card2) {
      setScore(getScore() + 1);
    }
    return false;
  }
}

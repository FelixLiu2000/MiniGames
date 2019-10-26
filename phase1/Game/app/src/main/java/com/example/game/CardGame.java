package com.example.game;
import utilities.Game;

public class CardGame extends Game { //  extends GameManager

    CardGame(){
        super();
    }
    public void setRoundStats() {
        //updates the stats during play
    }

    @Override
    public void createGameEnvironment() {
        //takes code from XML file
    }

    @Override
    public void startGame() {
        setScore(0);
        timer(); // calls parent timer function
    }

//    public void timer() {
//    }

    @Override
    public void endGame() {}
//    public int endRound() {
//        return stats;
//    }

    // TODO: INCORPORATE PLAY INTO STARTGAME()
    public void play() {
    //calls methods needed for play
    }

    public boolean check(int card1, int card2) {
    // check if two cards are matches
        return true;
    }

    public void flip(int card1) {
    // shows the other side of the card
    }
}

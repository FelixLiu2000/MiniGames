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
        //can move this
        CardGameActivity activity = new CardGameActivity();
        if (activity.getClicked()) {
            flip(activity.getCard_num());
        }

    }

    public boolean check(int card1, int card2) {
        // check if two cards are matches and increase total
        if (card1 == card2) {
            stats++;
        }
    return false;
    }

    public void flip(int card) {
        //return other side of the card
    }
}

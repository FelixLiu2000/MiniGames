package com.example.game;

public class CardGame extends GameManager {
    int stats;
    //stats is total number of card matches

    public void setRoundStats() {
        //updates the stats during play

    }

    public void createGameEnvironment() {
        //takes code from XML file

    }

    public void startRound() {
        stats = 0;
        timer();
    }

    public void timer() {
        long start = System.currentTimeMillis();
        long end = start + 60*1000; // 60 seconds * 1000 ms/sec
        while (System.currentTimeMillis() < end)
        {
            // update game timer
        }
        endRound();
    }

    public int endRound() {
        // timer() ends the round
        return stats;
    }

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

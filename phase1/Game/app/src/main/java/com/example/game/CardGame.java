package com.example.game;

public class CardGame extends GameManager {
    int stats;

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
        return stats;
    }

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

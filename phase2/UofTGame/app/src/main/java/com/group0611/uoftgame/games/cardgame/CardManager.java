package com.group0611.uoftgame.games.cardgame;

import com.group0611.uoftgame.activities.CardGameActivity;
import java.util.ArrayList;
import com.group0611.uoftgame.utilities.AppManager;

public class CardManager {

    private CardGameActivity activity;
    private ArrayList<Integer> cardArray1 = new ArrayList<>();
    private ArrayList<Integer> cardArray2 = new ArrayList<>();
    private int clickedFirst, clickedSecond;
    private int cardNum = 1;
    private int timeLimit;
    private AppManager appManager;

    /**
     * Constructor method for the card Manager and sets the activity.
     * @param activity The cardGameActivity
     */
    public CardManager(CardGameActivity activity){
        this.activity = activity;
    }

    /**
     * Sets the first card clicked given input.
     * @param clickedFirst The card that was clicked first.
     */
    public void setClickedFirst(int clickedFirst) {
        this.clickedFirst = clickedFirst;
    }

    /**
     * Returns the first card clicked.
     * @return the card that was clicked first.
     */
    public int getClickedFirst() {
        return clickedFirst;
    }

    /**
     * Sets the second card clicked given input.
     * @param clickedSecond The card that was clicked second.
     */
    public void setClickedSecond(int clickedSecond) {
        this.clickedSecond = clickedSecond;
    }

    /**
     * Returns the second card clicked.
     * @return the card that was clicked second.
     */
    public int getClickedSecond() {
        return clickedSecond;
    }

    /**
     * Sets the number of cards left given an input.
     * @param cardNum the number of cards left.
     */
    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    /**
     * Returns the number of cards left.
     * @return Number of cards left.
     */
    public int getCardNum() {
        return cardNum;
    }

    /**
     * Sets the card array depending on size of the board. Creates two arrays for each pair and
     * adds them all to a single array.
     */
    public void setCardsArray() {
        // arranges the cards on the board
        int half = (activity.cardHeight * activity.cardWidth) / 2;
        for (int i = 0; i < half; i++){
            cardArray1.add(100 + i);
            cardArray2.add(200 + i);
        }
        cardArray1.addAll(cardArray2);
    }

    public ArrayList<Integer> getCardsArray(){
        return cardArray1;
    }

    /**
     * Sets all of the buttons on the board so they cannot be clicked.
     */
    public void disableCards(){
        int boardSize = this.activity.cardHeight * this.activity.cardWidth;
        // disables card buttons
        for (int i = 0; i < boardSize; i++) {
            activity.buttons.get(i).setEnabled(false);
        }
    }
    /**
     * Checks if the two cards are matches by comparing their numerical representations. If they are a
     * match, call the match function.
     *
     * @param card1 The first card clicked
     * @param card2 The second card clicked.
     * @return boolean True if cards are a match
     */
    public boolean check(int card1, int card2) {
        // check if two cards are matches and increase total
        if (card1 >= 200) {
            card1 -= 100;
        } else if (card2 >= 200) {
            card2 -= 100;
        }
        if (card1 == card2) {
            return true;
        }
        return false;
    }
}

package com.group0611.uoftgame.games.cardgame;


import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.activities.CardGameActivity;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.utilities.AppManager;

import java.util.ArrayList;
import java.util.Collections;

public class CardGame extends Game {

  private CardGameActivity activity;
  private int cardsLeft = 12;
  private ArrayList<Integer> cardArray1 = new ArrayList<>();
  private ArrayList<Integer> cardArray2 = new ArrayList<>();
  int clickedFirst, clickedSecond;
  int cardNum = 1;
  private int currentScore = 0;
  private CountDownTimer cardGameTimer;

  public CardGame(int timeLimit, AppManager appManager, CardGameActivity activity) {
    super(timeLimit, appManager);
    this.activity = activity;
    Collections.shuffle(cardArray1);
    setCardsArray();
    play();
  }

  public CountDownTimer getCardGameTimer() {
    return cardGameTimer;
  }

  public void play() {
    // start countdown timer that will appear on screen (and end the game)
    cardGameTimer = new CountDownTimer(getAppManager().getCurrentPlayer().getTimeChoice()[1], 1000) {
      public void onTick(long millisUntilFinished) {
        String timeLeft = String.valueOf(millisUntilFinished / 1000);
        String timeText = "Time Remaining: " + timeLeft;
        setTime(timeText);
      }
      // when the timer is finished, switch the text to say Time is Up
      public void onFinish() {
        endGame();
      }
    }.start();
  }

  private boolean check(int card1, int card2) {
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

  private boolean boardEmpty() {
    // finds out if current game round is over
    return cardsLeft == 0;
  }

  private void resetGame() {
    // reassigns cardsLeft after board is empty
    cardsLeft = 12;
  }

  private void setTime(String timeLeftText) {
    // sets time in the UI
    this.activity.setTime(timeLeftText);
  }

  private void setCardsArray() {
    // arranges the cards on the board
    int half = cardsLeft / 2;
    for (int i = 0; i < half; i++){
      cardArray1.add(100 + i);
      cardArray2.add(200 + i);
    }
    cardArray1.addAll(cardArray2);
  }


  /**
   * When one card is clicked, flip the card and disable the card to be clicked.
   * - If the card is the second card to be checked, disable all other cards.
   * @param card The integer representation of the image that was clicked.
   * @param iv The card button that was clicked.
   */
  public void flip(int card, ImageView iv) {
    // set the image resource for each button, so the item can be compared
    // this is how the numbers in the array (100 vs. 200) each get connected to the same image
    if (cardArray1.get(card) == 100 || cardArray1.get(card) == 200) {
      iv.setImageResource(R.drawable.orange_circle);
    } else if (cardArray1.get(card) == 101 || cardArray1.get(card) == 201){
      iv.setImageResource(R.drawable.pink_ring);
    } else if (cardArray1.get(card) == 102 || cardArray1.get(card) == 202){
      iv.setImageResource(R.drawable.green_ring);
    } else if (cardArray1.get(card) == 103 || cardArray1.get(card) == 203){
      iv.setImageResource(R.drawable.red_circle);
    } else if (cardArray1.get(card) == 104 || cardArray1.get(card) == 204){
      iv.setImageResource(R.drawable.yellow_square);
    } else if (cardArray1.get(card) == 105 || cardArray1.get(card) == 205){
      iv.setImageResource(R.drawable.blue_square);
    }

    // checks which card has been selected and sets to temporary variables, to be used in Activity
    // or sent to back end
    if (cardNum == 1){
      // updates number of card being checked to look for second card
      cardNum = 2;
      clickedFirst = card;

      // sets the card that was just flipped so that it cannot be clicked again
      iv.setEnabled(false);
    } else if (cardNum == 2) {
      // updates number of cards so that it goes back to looking for first card
      cardNum = 1;
      clickedSecond = card;

      // after two cards have been clicked, set all the cards so they cannot be clicked again
      disableCards();

      // this puts a delay on the call to update (which either deletes or flips back the
      // cards so that the player can see which cards have been flipped and try and
      // remember the cards)
      // Source: https://stackoverflow.com/questions/42379301/how-to-use-postdelayed-correctly-in-android-studio
      final Handler handler = new Handler();
      handler.postDelayed(new Runnable(){
        @Override
        public void run(){
          // checks if images are a match and either flips cards back over or removes them
          // from the board
          update();
        }
      }, 1000);
    }
  }

  /**
   * After both cards have been collected, update the game board.
   * - If cards are a match, remove those cards and update the score.
   * - If the cards are not a match, turn the cards back over and enable them.
   * - If the game board is empty, refill the board and enable all cards.
   */
  private void update() {
    if(check(cardArray1.get(clickedFirst), cardArray1.get(clickedSecond))){
      // if the two cards are equal (checked on back-end) set the first card/button to invisible
      for (int i = 0; i < activity.buttons.size(); i++) {
        if (clickedFirst == i) {
          activity.buttons.get(i).setVisibility(View.INVISIBLE);
        }
      }
      // if the two cards are equal, set the second card/button to invisible
      for (int i = 0; i < activity.buttons.size(); i++) {
        if (clickedSecond == i) {
          activity.buttons.get(i).setVisibility(View.INVISIBLE);
        }
      }
      currentScore += 1;
      String updatedScore = "Score: " + currentScore;
      activity.score.setText(updatedScore);
      if (boardEmpty()){
        // if round has been completed, create another
        for (int i = 0; i < activity.buttons.size(); i++) {
          activity.buttons.get(i).setImageResource(R.drawable.memory_card);
          activity.buttons.get(i).setVisibility(View.VISIBLE);
          activity.buttons.get(i).setEnabled(true);
          Collections.shuffle(cardArray1);
          resetGame();
        }
      }
    } else {
      // if the two cards are not the same, set the image resource back to the green square
      // (back of card), and enable the card so that it can be clicked again
      for (int i = 0; i < activity.buttons.size(); i++) {
        activity.buttons.get(i).setImageResource(R.drawable.memory_card);
      }
    }
    for (int i = 0; i < activity.buttons.size(); i++) {
      activity.buttons.get(i).setEnabled(true);
    }

  }

  private void disableCards() {
    // disables card buttons
    for (int i = 0; i < activity.buttons.size(); i++) {
      activity.buttons.get(i).setEnabled(false);
    }
  }

  @Override
  protected void endGame() {
    // shows player the game is over, score is saved, game is exited
    String timeText = "Time Is Up!";
    setTime(timeText);
    disableCards();
    this.getAppManager().getCurrentPlayer().setCurrentGameScore(this.currentScore);
    this.activity.leaveGame(this.getAppManager());
  }
}

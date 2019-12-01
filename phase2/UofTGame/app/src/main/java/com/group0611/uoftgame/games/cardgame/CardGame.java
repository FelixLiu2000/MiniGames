package com.group0611.uoftgame.games.cardgame;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.activities.CardGameActivity;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.games.MultiplayerGame;
import com.group0611.uoftgame.games.TimedGame;

import java.util.ArrayList;
import java.util.Collections;

public class CardGame extends Game implements TimedGame, MultiplayerGame {

  private int cardsLeft = 12;
  private ArrayList<Integer> cardArray;
  // round score
  private int currentScore = 0;
  private int boardSize = 0;
  private CountDownTimer cardGameTimer;
  private ArrayList<Integer> playerScores = new ArrayList<>();
  private int currentPlayerNumber = 1;
  private int totalTries = 0;
  // total score accumulated over rounds (this is = setPlayerScore currently)
  private int totalScore = 0;
  private int level = 1;
  private CardManager cardManager;

  /**
   * Constructor for the CardGame. Initializes the activity, shuffle the cards, set the card array
   * and call the startGame method.
   *
   * @param builder GameBuilder that contains the game's parameters.
   */
  public CardGame(GameBuilder builder) {
    super(builder);
    this.cardManager = new CardManager(getActivity());
    cardManager.setCardsArray();
    cardArray = cardManager.getCardsArray();
    Collections.shuffle(cardArray);
    setPlayerScore(1, 0);
    startGame();
  }

  @Override
  public boolean getUsesTimedGameMode() {
    return super.getUsesTimedGameMode();
  }

  @Override
  public boolean getUsesMultiplayerGameMode() {
    return super.getUsesMultiplayerGameMode();
  }

  @Override
  public int getTimeLimit() {
    return super.getTimeLimit();
  }

  @Override
  public int getCurrentPlayerNumber() {
    return currentPlayerNumber;
  }

  @Override
  public int getPlayerScore(int playerNumber) {
    return playerScores.get(playerNumber - 1);
  }

  @Override
  protected int getCurrentPlayerScore() {
    return getPlayerScore(getCurrentPlayerNumber());
  }

  /**
   * Sets each players current score.
   * @param playerNumber Number indicator of which player.
   * @param newScore Players new score.
   */
  private void setPlayerScore(int playerNumber, int newScore) {
    if (playerNumber - 1 >= playerScores.size()) {
      playerScores.add(playerNumber - 1, newScore);
    } else {
      playerScores.set(playerNumber - 1, newScore);
    }
  }

  @Override
  public void nextPlayerTurn() {
    if (playerScores.size()== 1){
      endGame();
    }
    else if (getCurrentPlayerNumber() == 1){
      currentPlayerNumber = 2;
      cardManager.setCardsArray();
      cardArray = cardManager.getCardsArray();
      Collections.shuffle(cardArray);
      setPlayerScore(2, playerScores.get(1));
      startGame();
    } else {
      currentPlayerNumber = 1;
      cardManager.setCardsArray();
      cardArray = cardManager.getCardsArray();
      Collections.shuffle(cardArray);
      setPlayerScore(1, playerScores.get(0));
      startGame();
    }
  }

  public CountDownTimer getCardGameTimer() {
    return cardGameTimer;
  }

  /** Initializes the timer and called to start the game. When the timer runs out, end the game. */
  @Override
  public void startGame() {
    // start countdown timer that will appear on screen (and end the game)
    cardGameTimer =
        new CountDownTimer(getTimeLimit(), 1000) {
          public void onTick(long millisUntilFinished) {
            String timeLeft = String.valueOf(millisUntilFinished / 1000);
            String timeText = "Time Remaining: " + timeLeft;
            setTime(timeText);
          }
          // when the timer is finished, switch the text to say Time is Up
          public void onFinish() {
            if (getActivity().infiniteGame) {
              endLevel();
            } else {
              nextPlayerTurn();
            }
          }
        }.start();
  }

  /**
   * Get's called when there is a match. Calls the effects of finding a match, including updating
   * score, updating number of cards left and calling the correct sound effect.
   */
  private void match() {
    setPlayerScore(getCurrentPlayerNumber(), getCurrentPlayerScore() + 1);
    cardsLeft -= 2;
    totalTries += 1;
    getActivity().correctSound();
  }

  /**
   * Checks if the board is empty, that is there are 0 cards left showing.
   *
   * @return Boolean True iff empty
   */
  private boolean boardEmpty() {
    // finds out if current game round is over
    return cardsLeft == 0;
  }

  /**
   * Before the time runs out, the cards all re-appear. This sets the cardsLeft value to the total
   * given the board width and height.
   */
  private void resetGame() {
      // if round has been completed, create another
      for (int i = 0; i < boardSize; i++) {
          getActivity().buttons.get(i).setImageResource(R.drawable.course);
          getActivity().buttons.get(i).setVisibility(View.VISIBLE);
          getActivity().buttons.get(i).setEnabled(true);
          Collections.shuffle(cardArray);
      }
      cardsLeft = 12;
  }

  @Override
  protected CardGameActivity getActivity() {
    return (CardGameActivity) super.getActivity();
  }

  /**
   * Calls the setTime function from CardGameActivity which sets the Textview time to the given time
   * left in game.
   *
   * @param timeLeftText String representation of time left in game.
   */
  private void setTime(String timeLeftText) {
    // sets time in the UI
    getActivity().setTime(timeLeftText);
  }

  /**
   * When one card is clicked, flip the card and disable the card to be clicked. - If the card is
   * the second card to be checked, disable all other cards.
   *
   * @param card The integer representation of the image that was clicked.
   * @param iv The card button that was clicked.
   */
  public void flip(int card, ImageView iv) {
    // set the image resource for each button, so the item can be compared
    // this is how the numbers in the array (100 vs. 200) each get connected to the same image
    if (cardArray.get(card) == 100 || cardArray.get(card) == 200) {
      iv.setImageResource(R.drawable.orange_circle);
    } else if (cardArray.get(card) == 101 || cardArray.get(card) == 201) {
      iv.setImageResource(R.drawable.pink_ring);
    } else if (cardArray.get(card) == 102 || cardArray.get(card) == 202) {
      iv.setImageResource(R.drawable.green_ring);
    } else if (cardArray.get(card) == 103 || cardArray.get(card) == 203) {
      iv.setImageResource(R.drawable.red_circle);
    } else if (cardArray.get(card) == 104 || cardArray.get(card) == 204) {
      iv.setImageResource(R.drawable.yellow_square);
    } else if (cardArray.get(card) == 105 || cardArray.get(card) == 205) {
      iv.setImageResource(R.drawable.blue_square);
    }

    // checks which card has been selected and sets to temporary variables, to be used in Activity
    // or sent to back end
    if (cardManager.getCardNum() == 1) {
      // updates number of card being checked to look for second card
      cardManager.setCardNum(2);
      cardManager.setClickedFirst(card);

      // sets the card that was just flipped so that it cannot be clicked again
      iv.setEnabled(false);
    } else if (cardManager.getCardNum() == 2) {
      // updates number of cards so that it goes back to looking for first card
      cardManager.setCardNum(1);
      cardManager.setClickedSecond(card);

      // after two cards have been clicked, set all the cards so they cannot be clicked again
      cardManager.disableCards();
      delayUpdate();
    }
  }

  private void delayUpdate(){
      // this puts a delay on the call to update (which either deletes or flips back the
      // cards so that the player can see which cards have been flipped and try and
      // remember the cards)
      // Source:
      // https://stackoverflow.com/questions/42379301/how-to-use-postdelayed-correctly-in-android-studio

      final Handler handler = new Handler();
      handler.postDelayed(
              new Runnable() {
                  @Override
                  public void run() {
                      // checks if images are a match and either flips cards back over or removes them
                      // from the board
                      update();
                  }
              },
              1000);
  }

  /**
   * After both cards have been collected, update the game board. - If cards are a match, remove
   * those cards and update the score. - If the cards are not a match, turn the cards back over and
   * enable them. - If the game board is empty, refill the board and enable all cards.
   */
  private void update() {
    boardSize = getActivity().cardHeight * getActivity().cardWidth;
    cardArray = cardManager.getCardsArray();
    if (cardManager.check(cardArray.get(cardManager.getClickedFirst()), cardArray.get(cardManager.getClickedSecond()))) {
      match();
      // if the two cards are equal (checked on back-end) set the first card/button to invisible
      for (int i = 0; i < boardSize; i++) {
        if (cardManager.getClickedFirst() == i) {
          getActivity().buttons.get(i).setVisibility(View.INVISIBLE);
        }
      }
      // if the two cards are equal, set the second card/button to invisible
      for (int i = 0; i < boardSize; i++) {
        if (cardManager.getClickedSecond() == i) {
          getActivity().buttons.get(i).setVisibility(View.INVISIBLE);
        }
      }
      updateScore(true);
      if (boardEmpty()) {
        resetGame();
      }
    } else {
      // if the two cards are not the same, set the image resource back to the green square
      // (back of card), and enable the card so that it can be clicked again
      for (int i = 0; i < boardSize; i++) {
        getActivity().buttons.get(i).setImageResource(R.drawable.course);
      }
      updateScore(false);
    }
    for (int i = 0; i < boardSize; i++) {
      getActivity().buttons.get(i).setEnabled(true);
    }
  }

  /**
   * Updates the score depending on which level of the game is being played. This implements the
   * score reduction method for incorrect guesses.
   * @param correct whether or not a match was found.
   */
  private void updateScore(boolean correct){
    if (correct){
      currentScore += 1;
      String updatedScore = "Score: " + currentScore;
      getActivity().score.setText(updatedScore);
    } else if (level > 1){
      currentScore -= 1;
      String updatedScore = "Score: " + currentScore;
      getActivity().score.setText(updatedScore);
    } else{
      String updatedScore = "Score: " + currentScore;
      getActivity().score.setText(updatedScore);
    }
    if (currentScore == 0 && level > 1){
      endGame();
    }
  }

  /**
   * Ends the first level and sets the second level's starting score. Then calls to start the
   * second level.
   */
  private void endLevel(){
    boardSize = this.getActivity().cardHeight * this.getActivity().cardWidth;
    cardArray = cardManager.getCardsArray();
    totalScore += currentScore;
    level += 1;
    resetGame();
    System.out.println(totalTries);
  }

  /**
   * Sets the final score, disables cards and leaves Game.
   */
  @Override
  protected void endGame() {
    // shows player the game is over, score is saved, game is exited
    String timeText = "Time Is Up!";
    setTime(timeText);
    cardManager.disableCards();
    this.getAppManager().getCurrentPlayer().setCurrentGameScore(getCurrentPlayerScore());
    getActivity().leaveGame(this.getAppManager());
  }
}

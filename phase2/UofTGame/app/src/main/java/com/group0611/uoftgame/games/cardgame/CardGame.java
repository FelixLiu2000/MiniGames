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
import com.group0611.uoftgame.games.cardgame.CardPlayer;
import com.group0611.uoftgame.utilities.GameMode;

import java.util.ArrayList;
import java.util.Collections;

public class CardGame extends Game implements TimedGame, MultiplayerGame {

  private int cardsLeft = 12;
  private ArrayList<Integer> cardArray;
  // round score
  private int currentScore = 0;
  private int boardSize = 0;
  private CountDownTimer cardGameTimer;
  private ArrayList<CardPlayer> players = new ArrayList<>();
  private int currentPlayerNumber = 1;
  private int totalTries = 0;
  // total score accumulated over rounds (this is = setPlayerScore currently)
  private int round = 0;
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
    initializePlayer();
    setPlayerScore(1, 0);
    setLevel(1);
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
    return getPlayer(playerNumber).getScore();
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
    getPlayer(playerNumber).setScore(newScore);
  }

  private void setLevel(int playerNumber){
    getPlayer(playerNumber).setLevel(1);
  }

  private void initializePlayer() {
    CardPlayer[] players;
    if (getActivity().getGameIsMultiplayer()) {
      players = new CardPlayer[2];
      players[1] = new CardPlayer();
    } else {
      players = new CardPlayer[1];
    }
    players[0] = new CardPlayer();
    setPlayers(players);
  }
  /**
   * Sets the game's player(s) and initializes their starting lives and usernames.
   *
   * @param players the players to be assigned.
   */
  private void setPlayers(CardPlayer[] players) {
    for (CardPlayer player : players) {
      player.setUsername(getAppManager().getCurrentPlayer().getUsername());
      this.players.add(player);
    }
  }

  /**
   * Gets the player with a given player number.
   *
   * @param playerNumber the number of the player (indexed at 1).
   */
  private CardPlayer getPlayer(int playerNumber) {
    if (playerNumber == 1 || (getActivity().getGameIsMultiplayer() && playerNumber <= players.size())) {
      return players.get(playerNumber - 1);
    } else {
      throw new IllegalArgumentException(
              "Illegal argument: player with number " + playerNumber + " not found.");
    }
  }

  @Override
  public void nextPlayerTurn() {
    if (getCurrentPlayerNumber() == 1){
      setPlayerScore(1, getPlayerScore(1));
      getPlayer(1).setTotalScore(getPlayer(1).getTotalScore() + getPlayerScore(1));
      currentPlayerNumber = 2;
      getActivity().score.setText("Next Player");
      clearBoard();
      round += 1;
      final Handler handler = new Handler();
      handler.postDelayed(
              new Runnable() {
                @Override
                public void run() {
                  // checks if images are a match and either flips cards back over or removes them
                  // from the board
                  setPlayerScore(2, 0);
                  resetGame();
                  startGame();
                }
              },
              5000);
    } else if (getCurrentPlayerNumber() == 2){
      setPlayerScore(2, getPlayerScore(1));
      getPlayer(2).setTotalScore(getPlayer(2).getTotalScore() + getPlayerScore(2));
      currentPlayerNumber = 1;
      getActivity().score.setText("Next Player");
      clearBoard();
      round +=1;
      final Handler handler = new Handler();
      handler.postDelayed(
              new Runnable() {
                @Override
                public void run() {
                  // checks if images are a match and either flips cards back over or removes them
                  // from the board
                  setPlayerScore(1, 0);
                  resetGame();
                  startGame();
                }
              },
              5000);
    } else {
      endGame();
    }
  }

  private void clearBoard(){
    boardSize = getActivity().cardHeight * getActivity().cardWidth;
    for (int i = 0; i < boardSize; i++) {
        getActivity().buttons.get(i).setVisibility(View.INVISIBLE);
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
            if (getActivity().getGameIsMultiplayer()) {
              if (round == 1) {
                endGame();
              } else if (getActivity().getGameMode() == GameMode.INFINITE && round == 0){
                endLevel();
              }
              else{
                nextPlayerTurn();
              }
            }
            else if (getActivity().getGameMode() == GameMode.INFINITE) {
              endLevel();
            } else if (getActivity().getGameMode() == GameMode.TIMED) {
              endGame();
            }
          }
        }.start();
  }

  /**
   * Get's called when there is a match. Calls the effects of finding a match, including updating
   * score, updating number of cards left and calling the correct sound effect.
   */
  private void match() {
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
      setPlayerScore(getCurrentPlayerNumber(), getCurrentPlayerScore() + 1);
      String updatedScore = "Score: " + getCurrentPlayerScore();
      getActivity().score.setText(updatedScore);
      getPlayer(currentPlayerNumber).setTotalMatches(getPlayer(currentPlayerNumber).getTotalMatches() + 1);
    } else if (getPlayer(currentPlayerNumber).getLevel() > 1){
      setPlayerScore(getCurrentPlayerNumber(), getCurrentPlayerScore() - 1);
      String updatedScore = "Score: " + getCurrentPlayerScore();
      getActivity().score.setText(updatedScore);
      getPlayer(currentPlayerNumber).setTotalMisMatches(getPlayer(currentPlayerNumber).getTotalMisMatches() + 1);
    } else{
      String updatedScore = "Score: " + getCurrentPlayerScore();
      getActivity().score.setText(updatedScore);
      getPlayer(currentPlayerNumber).setTotalMisMatches(getPlayer(currentPlayerNumber).getTotalMisMatches() + 1);
    }
    getPlayer(currentPlayerNumber).setTotalMatchAttempts(getPlayer(currentPlayerNumber).getTotalMatchAttempts() + 1);
    if (getCurrentPlayerScore() == 0 && getPlayer(currentPlayerNumber).getLevel() > 1){
      nextPlayerTurn();
    }
  }

  /**
   * Ends the first level and sets the second level's starting score. Then calls to start the
   * second level.
   */
  private void endLevel(){
    boardSize = this.getActivity().cardHeight * this.getActivity().cardWidth;
    cardArray = cardManager.getCardsArray();
    getPlayer(currentPlayerNumber).setTotalScore(getPlayer(currentPlayerNumber).getTotalScore()+ getCurrentPlayerScore());
    getPlayer(currentPlayerNumber).setLevel(getPlayer(currentPlayerNumber).getLevel() + 1);
    resetGame();
    System.out.println(totalTries);
  }

  /**
   * Sets the final score, disables cards and leaves Game.
   */
  @Override
  protected void endGame() {
    getPlayer(currentPlayerNumber).setTotalScore(getPlayer(currentPlayerNumber).getTotalScore()+ getCurrentPlayerScore());
    // shows player the game is over, score is saved, game is exited
    String timeText = "Time Is Up!";
    setTime(timeText);
    cardManager.disableCards();
    CardPlayer playerOne = getPlayer(1);
    // Give app manager player one game stats
    this.getAppManager()
            .updatePlayerCardGameStats(
                    this.getAppManager().getPlayerOne(),
                    playerOne.getTotalScore(),
                    playerOne.getTotalMatches(),
                    playerOne.getTotalMisMatches(),
                    playerOne.getTotalMatchAttempts());
    // Give app manager player two game stats
    if (getActivity().getGameIsMultiplayer()) {
      CardPlayer playerTwo = getPlayer(2);
      this.getAppManager()
              .updatePlayerBallGameStats(
                      this.getAppManager().getPlayerTwo(),
                      playerTwo.getTotalScore(),
                      playerTwo.getTotalMatches(),
                      playerTwo.getTotalMisMatches(),
                      playerTwo.getTotalMatchAttempts());
      // Notify app manager of whether player one beat player two
      this.getAppManager().updateTwoPlayerStats(playerOne.getTotalScore() > playerTwo.getTotalScore());
    }

    getActivity().leaveGame(this.getAppManager());
  }
}

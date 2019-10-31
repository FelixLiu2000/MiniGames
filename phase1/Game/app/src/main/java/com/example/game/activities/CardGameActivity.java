package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.games.cardgame.CardGame;

import java.util.ArrayList;
import java.util.Collections;

public class CardGameActivity extends AppCompatActivity {
  // Structure of memory game loosely adapted from https://stackoverflow.com/questions/51002449/developing-a-memory-game
  private ArrayList<ImageView> buttons = new ArrayList<>();
  protected TextView score;
  // Initializes an array of integers that will refer to the images to be compared. The
  // cards that end in the same numbers will be considered matches (ex. 11 matches 21).
  private ArrayList<Integer> cardArray1 = new ArrayList<>();
  private ArrayList<Integer> cardArray2 = new ArrayList<>();
  int clickedFirst, clickedSecond;
  int cardNum = 1;
  CardGame cardGame = new CardGame(60);
  TextView time;
  int stat = 0;
  int size = 12;

  @Override
  // Source: https://developer.android.com/reference/android/widget/Button
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Collections.shuffle(cardArray1);
    setContentView(R.layout.activity_card_game);
    score = findViewById(R.id.score);
    time = findViewById(R.id.time);
    time.setText(String.valueOf(0));
    setCardsArray();

    // Adds all the images to an array so they can be iterated through.
    // Source: https://stackoverflow.com/questions/15112742/how-to-set-an-imageview-array
    buttons.add((ImageView) findViewById(R.id.card_11));
    buttons.add((ImageView) findViewById(R.id.card_12));
    buttons.add((ImageView) findViewById(R.id.card_13));
    buttons.add((ImageView) findViewById(R.id.card_14));
    buttons.add((ImageView) findViewById(R.id.card_21));
    buttons.add((ImageView) findViewById(R.id.card_22));
    buttons.add((ImageView) findViewById(R.id.card_23));
    buttons.add((ImageView) findViewById(R.id.card_24));
    buttons.add((ImageView) findViewById(R.id.card_31));
    buttons.add((ImageView) findViewById(R.id.card_32));
    buttons.add((ImageView) findViewById(R.id.card_33));
    buttons.add((ImageView) findViewById(R.id.card_34));

    // For each card button, set a indexing tag and turn on the Click Listener.
    for (int i = 0; i < buttons.size(); i++) {
      buttons.get(i).setTag(i);
      buttons.get(i).setOnClickListener(
              new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  System.out.println(v);
                  flip((int) v.getTag(), (ImageView)v);
                }
              });
    }

    //Start countdown timer that will appear on screen (and end the game).
    new CountDownTimer(60000, 1000) {
      public void onTick(long millisUntilFinished) {
        String timeLeft = String.valueOf(millisUntilFinished / 1000);
        String timeText = "Time Remaining: " + timeLeft;
        time.setText(timeText);
      }
      // When the timer is finished, switch the text to say Time is Up.
      public void onFinish() {
        String timeText = "Time Is Up";
        time.setText(timeText);
      }
    }.start();
  }

  /**
   * When one card is clicked, flip the card and disable to the card to be clicked.
   * - If the card is the second card to be checked, disable all other cards.
   * @param card The integer representation of the image that was clicked.
   * @param iv The card button that was clicked.
   */
  public void flip(int card, ImageView iv) {
    // Set the image resource for each button, so the item to be compared. This is how the
    // numbers in the array (100 vs. 200) each get connected to the same image.
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

    // Checks which card has been selected and sets to temporary variables, to be used in Activity
    // or sent to back end.
    if(cardNum == 1){
      // updates number of card being checked to look for second card.
      cardNum = 2;
      clickedFirst = card;

      // Sets the card that was just flipped so that it cannot be clicked again.
      iv.setEnabled(false);
    } else if (cardNum == 2) {
      // Updates number of card so that it goes back to looking for first card.
      cardNum = 1;
      clickedSecond = card;

      // After two cards have been clicked, set all the cards so they cannot be clicked again,
      // this would be cheating in a memory game.
      for (int i = 0; i < buttons.size(); i++) {
        buttons.get(i).setEnabled(false);
      }
      // This puts a delay the call to update (which either deletes or flips back the
      // cards so that, the player can see which cards have been flipped and try and
      // remember the cards.
      // Source: https://stackoverflow.com/questions/42379301/how-to-use-postdelayed-correctly-in-android-studio
      final Handler handler = new Handler();
      handler.postDelayed(new Runnable(){
          @Override
          public void run(){
            // Checks if images are a match and either flips cards back over or removes them
            // from the board.
              update();
          }
          }, 1000);
      }
  }

  /**
   * Creates two temporary cardArrays for 100 level images that match on to 200 level images
   * depending on the size of the board. Concatenate both ArrayLists into the first.
   */
  public void setCardsArray(){
    int half = size / 2;
    for (int i = 0; i < half; i++){
      cardArray1.add(100 + i);
      cardArray2.add(200 + i);
    }
    cardArray1.addAll(cardArray2);
  }

  /**
   * After both cards have been collected: update the game board.
   * - If cards are a match, remove those cards and updates the score.
   * - If the cards are not a match, turn the cards back over and enable them.
   * - If the game board is empty, refill the board and enable all cards.
   */
  public void update(){
    if(cardGame.check(cardArray1.get(clickedFirst), cardArray1.get(clickedSecond))){
      // If the two cards are equal (checked on back-end) set the first card/button to invisible.
      for (int i = 0; i < buttons.size(); i++) {
        if (clickedFirst == i) {
          buttons.get(i).setVisibility(View.INVISIBLE);
        }
      }
      // If the two cards are equal, set the second card/button to invisible.
      for (int i = 0; i < buttons.size(); i++) {
        if (clickedSecond == i) {
          buttons.get(i).setVisibility(View.INVISIBLE);
        }
      }
      stat += 1;
      String updatedScore = "Score: " + stat;
      score.setText(updatedScore);
      if (cardGame.boardEmpty()){
          for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setImageResource(R.drawable.memory_card);
                buttons.get(i).setVisibility(View.VISIBLE);
                buttons.get(i).setEnabled(true);
                Collections.shuffle(cardArray1);
                cardGame.resetGame();
          }
      }
    } else {
      // If the two cards are not the same, set the image resource back to the green square
      // (back of card), and enable the card so that it can be clicked again.
      for (int i = 0; i < buttons.size(); i++) {
        buttons.get(i).setImageResource(R.drawable.memory_card);
      }
    }
      for (int i = 0; i < buttons.size(); i++) {
          buttons.get(i).setEnabled(true);
      }

  }
}

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
import java.util.Arrays;
import java.util.Collections;

public class CardGameActivity extends AppCompatActivity {
  private ArrayList<ImageView> buttons = new ArrayList<>();
  protected TextView score;
  private ImageView card_view;
  // Initializes an array of integers that will refer to the images to be compared. The
  // cards that end in the same numbers will be considered matches (ex. 11 matches 21).
  private Integer[] cardsArray = {11, 12, 13, 14, 15, 16, 21, 22, 23, 24, 25, 26};
  int firstCard, secondCard;
  int clickedFirst, clickedSecond;
  int cardNum = 1;
  CardGame cardGame = new CardGame(60);
  TextView time;
  int stat = 0;

//  public CardGameActivity() {
//    Collections.shuffle(Arrays.asList(cardsArray));
//  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Collections.shuffle(Arrays.asList(cardsArray));
    setContentView(R.layout.activity_card_game);
    score = findViewById(R.id.score);
    time = findViewById(R.id.time);
    time.setText(String.valueOf(0));

    // Adds all the images to an array so they can be iterated through.
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

    for (int i = 0; i < buttons.size(); i++) {
      buttons.get(i).setTag(i);
      card_view = buttons.get(i);
      buttons.get(i).setOnClickListener(
              new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  System.out.println(v);
                  flip((int) v.getTag(), (ImageView)v);
                }
              });
    }
    new CountDownTimer(60000, 1000) {
      public void onTick(long millisUntilFinished) {
        String timeLeft = String.valueOf(millisUntilFinished / 1000);
        String timeText = "Time Remaining: " + timeLeft;
        time.setText(timeText);
      }

      public void onFinish() {
        String timeText = "Time Is Up";
        time.setText(timeText);
      }
    }.start();
  }
  public ImageView getCard_view() {
    return card_view;
  }

  public void flip(int card, ImageView iv) {
    // Set the image resource for each button, so the item to be compared. This is how the
    // numbers in the array (100 vs. 200) each get connected to the same image.
    if (cardsArray[card] == 11 || cardsArray[card] == 21) {
      iv.setImageResource(R.drawable.orange_circle);
    } else if (cardsArray[card] == 12 || cardsArray[card] == 22){
      iv.setImageResource(R.drawable.pink_ring);
    } else if (cardsArray[card] == 13 || cardsArray[card] == 23){
      iv.setImageResource(R.drawable.green_ring);
    } else if (cardsArray[card] == 14 || cardsArray[card] == 24){
      iv.setImageResource(R.drawable.red_circle);
    } else if (cardsArray[card] == 15 || cardsArray[card] == 25){
      iv.setImageResource(R.drawable.yellow_square);
    } else if (cardsArray[card] == 16 || cardsArray[card] == 26){
      iv.setImageResource(R.drawable.blue_square);
    }

    // checks which card has been selected and sets to temporary variables, to be used in Activity
    // or sent to back end.
    if(cardNum == 1){
      firstCard = cardsArray[card];
      if(firstCard > 20){
        firstCard = firstCard - 10;
      }
      // updates number of card being checked to look for second card.
      cardNum = 2;
      clickedFirst = card;

      // Sets the card that was just flipped so that it cannot be clicked again.
      iv.setEnabled(false);
    } else if (cardNum == 2) {
      secondCard = cardsArray[card];
      if (secondCard > 20) {
        secondCard = secondCard - 10;
      }
      // updates number of card so that it goes back to looking for first card.
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
  public void update(){
    if(cardGame.check(firstCard, secondCard)){
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
                Collections.shuffle(Arrays.asList(cardsArray));
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

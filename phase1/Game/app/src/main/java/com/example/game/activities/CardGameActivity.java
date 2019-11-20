package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.games.cardgame.CardGame;
import com.example.game.utilities.AppManager;

import java.util.ArrayList;

public class CardGameActivity extends AppCompatActivity {
  AppManager appManager;
  Intent intentCardGameActivity;
  Intent intentCardGameToResultsPage;
  // Structure of memory game loosely adapted from https://stackoverflow.com/questions/51002449/developing-a-memory-game
  public ArrayList<ImageView> buttons = new ArrayList<>();
  public TextView score;
  TextView time;
  CardGame cardGame;

  @Override
  // Source: https://developer.android.com/reference/android/widget/Button
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    intentCardGameActivity = getIntent();
    appManager = (AppManager) intentCardGameActivity.getSerializableExtra("appManager");
    setContentView(R.layout.activity_card_game);
    score = findViewById(R.id.score);
    time = findViewById(R.id.time);
    time.setText(String.valueOf(0));
    addButtons();
    cardGame = new CardGame(60, appManager, this);
    addButtonOnClick();
  }

  public void addButtons() {
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
  }

  public void addButtonOnClick() {
    // for each card button, set an indexing tag and turn on the Click Listener
    for (int i = 0; i < buttons.size(); i++) {
      buttons.get(i).setTag(i);
      buttons.get(i).setOnClickListener(
              new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  System.out.println(v);
                  cardGame.flip((int) v.getTag(), (ImageView)v);
                }
              });
    }
  }

  public void setTime(String timeLeftText) {
    // sends amount of time left to display
    time.setText(timeLeftText);
  }

  public void leaveGame(AppManager appManager) {
    // uses class objects to go to end screen at time end
    intentCardGameToResultsPage = new Intent(CardGameActivity.this, ResultsPageActivity.class);
    intentCardGameToResultsPage.putExtra("appManager", appManager);
    startActivity(intentCardGameToResultsPage);
  }
}

package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.game.R;

import java.util.ArrayList;

public class CardGameActivity extends AppCompatActivity {
  private ArrayList<ImageView> buttons = new ArrayList<>();
  protected TextView score;
  private boolean clicked = false;
  private int card_num;

  public CardGameActivity() {

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_card_game);
    score = findViewById(R.id.score);

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


    for(int i = 0; i < buttons.size(); i ++) {
      buttons.get(i).setTag(i);
      buttons.get(i).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          clicked = true;
          card_num = (int) v.getTag();
        }
      });
    }
  }
  public boolean getClicked(){
    return clicked;
  }
  public int getCard_num(){
    return card_num;
  }
}

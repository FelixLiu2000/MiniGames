package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CardGameActivity extends AppCompatActivity {
    private ArrayList<ImageView> buttons = new ArrayList<>();
    protected TextView score;
    public boolean clicked = false;
    public int card_num;

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
    public boolean getClicked(boolean clicked){
        return clicked;
    }
    public int getCard_num(int card_num){
        return card_num;
    }
}

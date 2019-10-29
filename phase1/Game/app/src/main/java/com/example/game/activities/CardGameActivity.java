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
    protected TextView time;
    private boolean clicked = false;
    private ImageView card_view;
    private Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNum = 1;
    CardGame cardGame = new CardGame(60);


    public CardGameActivity() {
        Collections.shuffle(Arrays.asList(cardsArray));
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

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setTag(i);
            card_view = buttons.get(i);
            buttons.get(i).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println(v);
                            flip((int) v.getTag(), (ImageView)v);
                            clicked = true;
                        }
                    });
        }
    }
    public ImageView getCard_view() {
        return card_view;
    }

    public void flip(int card, ImageView iv) {
        // return other side of the card
        if (cardsArray[card] == 101 || cardsArray[card] == 201) {
            iv.setImageResource(R.drawable.orange_circle);
        } else if (cardsArray[card] == 102 || cardsArray[card] == 202){
            iv.setImageResource(R.drawable.pink_ring);
        } else if (cardsArray[card] == 103 || cardsArray[card] == 203){
            iv.setImageResource(R.drawable.green_ring);
        } else if (cardsArray[card] == 104 || cardsArray[card] == 204){
            iv.setImageResource(R.drawable.red_circle);
        } else if (cardsArray[card] == 105 || cardsArray[card] == 205){
            iv.setImageResource(R.drawable.yellow_square);
        } else if (cardsArray[card] == 106 || cardsArray[card] == 206){
            iv.setImageResource(R.drawable.blue_square);
        }

        // checks which card has been selected to send to back end
        if(cardNum == 1){
            firstCard = cardsArray[card];
            if(firstCard > 200){
                firstCard = firstCard - 100;
            }
            cardNum = 2;
            clickedFirst = card;

            iv.setEnabled(false);
        } else if (cardNum == 2) {
            secondCard = cardsArray[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNum = 1;
            clickedSecond = card;

            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setEnabled(false);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    //check if the selected images are equal
                    update();
                }
            }, 1000);
            update();
        }
    }

    public void update(){
        if(cardGame.check(firstCard, secondCard)){
            if(clickedFirst == 0){
                findViewById(R.id.card_11).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                findViewById(R.id.card_12).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                findViewById(R.id.card_13).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                findViewById(R.id.card_14).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                findViewById(R.id.card_21).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                findViewById(R.id.card_22).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                findViewById(R.id.card_23).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                findViewById(R.id.card_24).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                findViewById(R.id.card_31).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                findViewById(R.id.card_32).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10) {
                findViewById(R.id.card_33).setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11) {
                findViewById(R.id.card_34).setVisibility(View.INVISIBLE);
            }
            if(clickedSecond == 0){
                findViewById(R.id.card_11).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                findViewById(R.id.card_12).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                findViewById(R.id.card_13).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                findViewById(R.id.card_14).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                findViewById(R.id.card_21).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                findViewById(R.id.card_22).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                findViewById(R.id.card_23).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                findViewById(R.id.card_24).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                findViewById(R.id.card_31).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                findViewById(R.id.card_32).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10) {
                findViewById(R.id.card_33).setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11) {
                findViewById(R.id.card_34).setVisibility(View.INVISIBLE);
            }
        } else{
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setImageResource(R.drawable.memory_card);
            }
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setEnabled(true);
        }

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView)time.findViewById(R.id.score)).
                        setText("Time Remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                ((TextView)time.findViewById(R.id.score)).setText("Time Is Up!");
            }
        }.start();
    }
}


package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.widget.ImageView;

//import androidx.appcompat.widget.AppCompatImageView;

import com.group0611.uoftgame.R;
//import com.group0611.uoftgame.activities.SubwayGameActivity;

public class Coin extends MovingObject {
    private int score;

    public Coin(Context context){
        super(context);
    }

    @Override
    public void setImage() {
        this.setImageResource(R.drawable.orange_circle);

    }

    /** decrease the score by 1 */
    int changeScore() {
        this.score += 1;
        System.out.println("Current score is: " + this.score);
        return this.score;
    }
}

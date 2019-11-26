package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.widget.ImageView;

//import androidx.appcompat.widget.AppCompatImageView;

import com.group0611.uoftgame.R;
//import com.group0611.uoftgame.activities.SubwayGameActivity;

public class Obstacle extends MovingObject {
//    public SubwayGameActivity activity;
    public ImageView image;
    protected int score;

    public Obstacle(Context context) {
        super(context);
//        this.image = new ImageView(activity);

        score = 10;
    }

    public void setImage() {
        this.image.setImageResource(R.drawable.circle_card);

    }

    /** decrease the score by 1 */
    public int changeScore() {
        if (this.score > 0) {
            this.score -= 1;
            System.out.println("Current score is: " + this.score);
        }
        return this.score;
    }
}

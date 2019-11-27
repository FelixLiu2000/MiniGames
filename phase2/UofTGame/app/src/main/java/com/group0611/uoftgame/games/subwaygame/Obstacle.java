package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.widget.ImageView;
import com.group0611.uoftgame.R;

public class Obstacle extends MovingObject {
    protected int score;

    public Obstacle(Context context) {
        super(context);
        score = 10;
    }

    public void setImage() {
        this.setImageResource(R.drawable.circle_card);

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

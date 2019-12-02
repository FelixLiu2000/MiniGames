package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.widget.ImageView;
import com.group0611.uoftgame.R;

/**
 * An Obstacle is a MovingObject that decreases the player's score by 1
 */
public class Obstacle extends MovingObject {

    public Obstacle(Context context) {
        super(context);
    }

    /**
     * Sets the Obstacle's image as a bucket.
     */
    public void setImage() {
        this.setImageResource(R.drawable.bucket);
    }

    /**
     * Sets the Obstacle's height and width parameters.
     */
    void setSize(){
        this.getLayoutParams().height = 130;
        this.getLayoutParams().width = 130;
    }

    /**
     * Decrease the score by 1
     * @return -1
     */
    public int changeScore() {
        return -1;
    }
}

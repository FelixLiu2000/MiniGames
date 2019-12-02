package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import com.group0611.uoftgame.R;

/**
 Coins are MovingObjects that increase the player's score
 */
public class Coin extends MovingObject {;

    public Coin(Context context){
        super(context);
    }

    /**
        Sets the image of Coins to the coin image
     */
    public void setImage() {
        this.setImageResource(R.drawable.coin);

    }

    /**
     * Sets the size of the image of Coins
     */
    void setSize(){
        this.getLayoutParams().height = 100;
        this.getLayoutParams().width = 100;
    }

    /**
     * Increase the score by 1
     * @return 1
     */
    int changeScore() {
        return 1;
    }
}

package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import com.group0611.uoftgame.R;

public class Coin extends MovingObject {;

    public Coin(Context context){
        super(context);
    }

//    @Override
    public void setImage() {
        this.setImageResource(R.drawable.coin);

    }

    void setSize(){
        this.getLayoutParams().height = 100;
        this.getLayoutParams().width = 100;
    }

    /** increase the score by 1 */
    int changeScore() {
        return 1;
    }
}

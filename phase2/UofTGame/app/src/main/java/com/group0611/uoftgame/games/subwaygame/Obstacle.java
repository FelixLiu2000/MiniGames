package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;
import android.widget.ImageView;
import com.group0611.uoftgame.R;

public class Obstacle extends MovingObject {

    public Obstacle(Context context) {
        super(context);
    }

    public void setImage() {
        this.setImageResource(R.drawable.bucket);

    }

    void setSize(){
        this.getLayoutParams().height = 130;
        this.getLayoutParams().width = 130;
    }

    /** decrease the score by 1 */
    public int changeScore() {
        return -1;
    }
}

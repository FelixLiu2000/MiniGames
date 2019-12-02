package com.group0611.uoftgame.games.subwaygame;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

/** The base class for all objects that move in SubwayGame, i.e, Coins and Obstacles */
public abstract class MovingObject extends AppCompatImageView {

  public MovingObject(Context context) {
    super(context);
  }

  /** This method should set the image to an icon sized image*/
  abstract void setImage();

  /** The size of MovingObjects should be square */
  abstract void setSize();

  /** Set location of the new obstacle */
  void setPosition() {
    this.setY(0);
    // randomly pick a lane
    int obstacleLane = pickLane();
    // set obstacle's x position based on the lane
    if (obstacleLane == 1) {
      this.setX(160);
    } else if (obstacleLane == 2) {
      this.setX(500);
    } else {
      this.setX(860); // lane 3
    }
  }

  /** randomly determine which lane the new obstacle is in */
  int pickLane() {
    return (int) (Math.random() * 4);
  }

  /** change the score by 1 */
  abstract int changeScore();
}

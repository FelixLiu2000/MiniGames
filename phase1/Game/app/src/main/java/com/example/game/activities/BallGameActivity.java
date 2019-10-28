package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.example.game.R;
import com.example.game.games.ballgame.BallGame;
import com.example.game.games.ballgame.PlayerActions;

import java.util.ArrayList;

public class BallGameActivity extends AppCompatActivity {
  private BallGame ballGame = new BallGame(60);
  private ArrayList<ImageButton> imageButtons = new ArrayList<>();
  private Button shootButton;
  private ImageView playerView, targetView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ball_game);
    // Set landscape orientation
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    // Set fullscreen
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove title
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    initializeGameViews();
  }

  private void initializeGameViews() {
    playerView = findViewById(R.id.picPlayer);
    targetView = findViewById(R.id.picTarget);
    ballGame.initializePlayer(playerView);
    ballGame.initializeTarget(targetView);
    initializeGameButtons();
  }

  private void initializeGameButtons() {
    ImageButton newButton = findViewById(R.id.btnAngleUp);
    newButton.setTag(PlayerActions.AngleUp);
    imageButtons.add(newButton);
    newButton = findViewById(R.id.btnAngleDown);
    newButton.setTag(PlayerActions.AngleDown);
    imageButtons.add(newButton);
    newButton = findViewById(R.id.btnPowerUp);
    newButton.setTag(PlayerActions.PowerUp);
    imageButtons.add(newButton);
    newButton = findViewById(R.id.btnPowerDown);
    newButton.setTag(PlayerActions.PowerDown);
    imageButtons.add(newButton);
    shootButton = findViewById(R.id.btnShoot);
    shootButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            ballGame.performPlayerAction(PlayerActions.Shoot);
          }
        });
    for (ImageButton button : imageButtons) {
      button.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ballGame.performPlayerAction((PlayerActions) v.getTag());
            }
          });
    }
  }
}

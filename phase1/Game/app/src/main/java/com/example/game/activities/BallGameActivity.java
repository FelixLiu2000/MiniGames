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
  //private ImageView playerView, targetView;
  private LinearLayout ballLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Set landscape orientation
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    // Set fullscreen
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove title
    getSupportActionBar().hide();
    setContentView(R.layout.activity_ball_game);
    initializeGameButtons();
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    if (hasFocus) {
      initializeGameViews();
      startGame();
    }
  }

  private void initializeGameViews() {
    ImageView playerView = findViewById(R.id.picPlayer);
    ImageView targetView = findViewById(R.id.picTarget);
    ballGame.initializePlayer(playerView);
    ballGame.initializeTarget(targetView);
    // Give game the layout used for ball views
    ballLayout = findViewById(R.id.ballLayout);
    ballGame.initializeBallLayout(ballLayout);
  }

  private void initializeGameButtons() {
    ImageButton btnAngleUp = findViewById(R.id.btnAngleUp);
    btnAngleUp.setTag(PlayerActions.AngleUp);
    imageButtons.add(btnAngleUp);
    ImageButton btnAngleDown = findViewById(R.id.btnAngleDown);
    btnAngleDown.setTag(PlayerActions.AngleDown);
    imageButtons.add(btnAngleDown);
    ImageButton btnPowerUp = findViewById(R.id.btnPowerUp);
    btnPowerUp.setTag(PlayerActions.PowerUp);
    imageButtons.add(btnPowerUp);
    ImageButton btnPowerDown = findViewById(R.id.btnPowerDown);
    btnPowerDown.setTag(PlayerActions.PowerDown);
    imageButtons.add(btnPowerDown);
    shootButton = findViewById(R.id.btnShoot);

    shootButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            ImageView ballView = new ImageView(BallGameActivity.this);
            ballView.setImageDrawable(getDrawable(R.drawable.ball_projectile));
            ballView.setLayoutParams(new LinearLayout.LayoutParams(32, 32));
            ballLayout.addView(ballView);

            ballGame.performPlayerAction(PlayerActions.Shoot, ballView);
          }
        });
    for (ImageButton button : imageButtons) {
      button.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ballGame.performPlayerAction((PlayerActions) view.getTag());
            }
          });
    }
  }

  private void startGame() {
    ballGame.play();
  }
}

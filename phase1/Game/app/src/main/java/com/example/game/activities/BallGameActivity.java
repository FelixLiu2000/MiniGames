package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.example.game.R;
import com.example.game.games.ballgame.BallGame;

import java.util.ArrayList;

public class BallGameActivity extends AppCompatActivity {
  private BallGame ballGame = new BallGame(60);
  // private ImageView playerView, targetView;
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
      initializeGameSeekBars();
      startGame();
    }
  }

  private void initializeGameViews() {
    ImageView playerView = findViewById(R.id.picPlayer);
    ImageView targetView = findViewById(R.id.picTarget);
    ballGame.initializePlayer(playerView);
    ballGame.initializeTarget(targetView);
    // Give game control of layout used for ball views (also used for screen boundaries)
    ballLayout = findViewById(R.id.ballLayout);
    ballGame.initializeLayouts(ballLayout);
    // Give game text control of output views (score, power, angle)
    ballGame.initializeOutputViews(
        (TextView) findViewById(R.id.txtScore),
        (TextView) findViewById(R.id.txtPower),
        (TextView) findViewById(R.id.txtAngle));
  }

  private void initializeGameButtons() {
    Button shootButton = findViewById(R.id.btnShoot);

    shootButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            ImageView ballView = new ImageView(BallGameActivity.this);
            ballView.setImageDrawable(getDrawable(R.drawable.ball_projectile));
            ballView.setLayoutParams(new LinearLayout.LayoutParams(32, 32));
            ballLayout.addView(ballView);

            ballGame.shootBall(ballView);
          }
        });
  }

  private void initializeGameSeekBars() {
    SeekBar angleSeekBar = findViewById(R.id.seekAngle);
    angleSeekBar.setProgress(BallGame.SHOT_STARTING_ANGLE, true);
    angleSeekBar.setMax(BallGame.SHOT_MAX_ANGLE);
    angleSeekBar.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int angle, boolean b) {
            if (ballGame != null) {
              ballGame.setShotAngle(angle);
            }
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    SeekBar powerSeekBar = findViewById(R.id.seekPower);
    powerSeekBar.setProgress(BallGame.SHOT_STARTING_POWER, true);
    powerSeekBar.setMax(BallGame.SHOT_MAX_POWER);
    powerSeekBar.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int power, boolean b) {
            if (ballGame != null) {
              ballGame.setShotPower(power);
            }
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {}
        });
  }

  private void startGame() {
      ballGame.play();
  }
}

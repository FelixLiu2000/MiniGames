package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.games.Game;
import com.group0611.uoftgame.games.ballgame.BallGame;
import com.group0611.uoftgame.games.ballgame.BallGamePresenter;
import com.group0611.uoftgame.games.ballgame.GameConstants;
import com.group0611.uoftgame.utilities.AppManager;

import java.util.ArrayList;

public class BallGameActivity extends AppCompatActivity implements GameActivity {

  private Intent currentIntent;
  private Intent toResultsPageIntent;
  private BallGamePresenter ballGamePresenter;
  // private BallGame ballGame;
  // private ImageView playerView, targetView;
  private LinearLayout ballLayout;
  private TextView scoreView;
  private TextView timeView;
  private TextView angleView;
  private TextView powerView;
  private ArrayList<ImageView> ballViews = new ArrayList<>();

  public TextView getScoreView() {
    return scoreView;
  }

  public TextView getTimeView() {
    return timeView;
  }

  public TextView getAngleView() {
    return angleView;
  }

  public TextView getPowerView() {
    return powerView;
  }

  public ArrayList<ImageView> getBallViews() {
    return ballViews;
  }

  public LinearLayout getBallLayout() {
    return ballLayout;
  }

  @Override
  public Intent getCurrentIntent() {
    return currentIntent;
  }

  @Override
  public void setCurrentIntent(Intent currentIntent) {
    this.currentIntent = currentIntent;
  }

  @Override
  public Intent getToResultsPageIntent() {
    return toResultsPageIntent;
  }

  @Override
  public void setToResultsPageIntent(Intent toResultsPageIntent) {
    this.toResultsPageIntent = toResultsPageIntent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCurrentIntent(getIntent());
    AppManager appManager = (AppManager) getCurrentIntent().getSerializableExtra("appManager");
    // ballGame = new BallGame(appManager.getCurrentPlayer().getTimeChoice()[2] / 1000, appManager,
    // this);
    int timeLimit = appManager.getCurrentPlayer().getTimeChoice()[2] / 1000;
    ballGamePresenter =
        new BallGamePresenter(
            (BallGame)
                new Game.GameBuilder(BallGame.class, appManager, this)
                    .setUsesTime(true)
                    .setTimeLimit(timeLimit)
                    .build());
    ballGamePresenter.bindActivity(this);
    // Set landscape orientation
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    // Set fullscreen
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove title
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
    setContentView(R.layout.activity_ball_game);
    initializeGameButtons();
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    if (hasFocus) {
      initializeGameViews();
      initializeGameSeekBars();
      ballGamePresenter.initializeOutputViews();
      ballGamePresenter.startGame();
    }
  }

  private void initializeGameViews() {
    ImageView playerView = findViewById(R.id.picPlayer);
    ImageView targetView = findViewById(R.id.picTarget);
    // ballGame.initializePlayer(playerView);
    // ballGame.initializeTarget(targetView);
    ballGamePresenter.initializePlayer(playerView);
    ballGamePresenter.initializeTarget(targetView);
    // Give game control of layout used for ball views (also used for screen boundaries)
    ballLayout = findViewById(R.id.ballLayout);
    // ballGame.initializeLayouts(ballLayout);
    ballGamePresenter.initializeActiveArea(
        ballLayout.getLeft(), ballLayout.getTop(), ballLayout.getRight(), ballLayout.getBottom());
    // Give game text control of output views (score, power, angle)
    // ballGame.initializeOutputViews(TextView) findViewById(R.id.txtScore), (TextView)
    // findViewById(R.id.txtTime), (TextView) findViewById(R.id.txtPower), (TextView)
    // findViewById(R.id.txtAngle));
    scoreView = findViewById(R.id.txtScore);
    timeView = findViewById(R.id.txtTime);
    angleView = findViewById(R.id.txtAngle);
    powerView = findViewById(R.id.txtPower);
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
            ballViews.add(ballView);
            // ballGame.shootBall(ballView);
            ballGamePresenter.initializeNewBall(ballView);
          }
        });
  }

  private void initializeGameSeekBars() {
    SeekBar angleSeekBar = findViewById(R.id.seekAngle);
    angleSeekBar.setProgress(GameConstants.SHOT_STARTING_ANGLE, true);
    angleSeekBar.setMax(GameConstants.SHOT_MAX_ANGLE);
    angleSeekBar.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int angle, boolean b) {
            ballGamePresenter.updateShotAngle(angle);
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    SeekBar powerSeekBar = findViewById(R.id.seekPower);
    powerSeekBar.setProgress(GameConstants.SHOT_STARTING_POWER, true);
    powerSeekBar.setMax(GameConstants.SHOT_MAX_POWER);
    powerSeekBar.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int power, boolean b) {
            ballGamePresenter.updateShotPower(power);
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {}
        });
  }

  public void updateViewLocation(View view, float x, float y) {
    if (view == null) {
      throw new IllegalArgumentException("Illegal argument: view not found.");
    } else {
      view.setX(x);
      view.setY(y);
    }
  }

  public void updateTextView(TextView view, String str) {
    if (view == null) {
      throw new IllegalArgumentException("Illegal argument: view not found.");
    } else {
      view.setText(str);
    }
  }

  public void removeViewFromLayout(View view, ViewGroup layout) {
    if (layout == null || view == null) {
      throw new IllegalArgumentException(
          "Illegal argument: layout or corresponding view not found.");
    } else {
      layout.removeView(view);
    }
  }

  @Override
  public void leaveGame(AppManager appManager) {
    setToResultsPageIntent(new Intent(BallGameActivity.this, ResultsPageActivity.class));
    getToResultsPageIntent().putExtra("appManager", appManager);
    startActivity(getToResultsPageIntent());
  }
}

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

  private Intent currentIntent, toResultsPageIntent, toDashboardIntent;
  private BallGamePresenter ballGamePresenter;
  // private BallGame ballGame;
  // private ImageView playerView, targetView;
  private LinearLayout ballLayout;
  private TextView player1ScoreView, player2ScoreView;
  private TextView player1LivesView, player2LivesView;
  private TextView timeView;
  private TextView angleView;
  private TextView powerView;
  private TextView playerNameView;
  private SeekBar angleControlView, powerControlView;
  private ArrayList<ImageView> ballViews = new ArrayList<>();

  public TextView getPlayer1ScoreView() {
    return player1ScoreView;
  }

  public TextView getPlayer2ScoreView() {
    return player2ScoreView;
  }

  public TextView getPlayer1LivesView() {
    return player1LivesView;
  }

  public TextView getPlayer2LivesView() {
    return player2LivesView;
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

  public TextView getPlayerNameView() {
    return playerNameView;
  }

  public ArrayList<ImageView> getBallViews() {
    return ballViews;
  }

  public LinearLayout getBallLayout() {
    return ballLayout;
  }

  public SeekBar getAngleControlView() {
    return angleControlView;
  }

  public SeekBar getPowerControlView() {
    return powerControlView;
  }

  @Override
  public Intent getCurrentIntent() {
    return currentIntent;
  }

  private void setCurrentIntent(Intent currentIntent) {
    this.currentIntent = currentIntent;
  }

  @Override
  public Intent getToResultsPageIntent() {
    return toResultsPageIntent;
  }

  private void setToResultsPageIntent(Intent toResultsPageIntent) {
    this.toResultsPageIntent = toResultsPageIntent;
  }

  @Override
  public Intent getToDashboardIntent() {
    return toDashboardIntent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCurrentIntent(getIntent());
    AppManager appManager = (AppManager) getCurrentIntent().getSerializableExtra("appManager");
    // ballGame = new BallGame(appManager.getCurrentPlayer().getTimeChoice()[2] / 1000, appManager,
    // this);
    int timeLimit = 20;
    BallGame ballgame =
        (BallGame)
            new Game.GameBuilder(BallGame.class, appManager, this)
                .addTimedGameMode(true)
                .setTimeLimit(timeLimit)
                .addLivesGameMode(true)
                .setStartingLives(10)
                .addMultiplayerGameMode(true)
                .build()
                .getGame();
    ballGamePresenter = new BallGamePresenter(ballgame);
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
    player1ScoreView = findViewById(R.id.txtPlayer1Score);
    player2ScoreView = findViewById(R.id.txtPlayer2Score);
    player1LivesView = findViewById(R.id.txtPlayer1Lives);
    player2LivesView = findViewById(R.id.txtPlayer2Lives);
    timeView = findViewById(R.id.txtTime);
    angleView = findViewById(R.id.txtAngle);
    powerView = findViewById(R.id.txtPower);
    playerNameView = findViewById(R.id.txtCurrentPlayer);
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
    angleControlView = findViewById(R.id.seekAngle);
    angleControlView.setMax(GameConstants.SHOT_MAX_ANGLE);
    angleControlView.setProgress(GameConstants.SHOT_STARTING_ANGLE, true);
    angleControlView.setOnSeekBarChangeListener(
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
    powerControlView = findViewById(R.id.seekPower);
    powerControlView.setMax(GameConstants.SHOT_MAX_POWER);
    powerControlView.setProgress(GameConstants.SHOT_STARTING_POWER, true);
    powerControlView.setOnSeekBarChangeListener(
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
      throw new IllegalArgumentException("Illegal argument: view is null.");
    } else {
      view.setX(x);
      view.setY(y);
    }
  }

  public void updateTextView(TextView view, String str) {
    if (view == null) {
      throw new IllegalArgumentException("Illegal argument: view is null.");
    } else {
      view.setText(str);
    }
  }

  public void removeViewFromLayout(View view, ViewGroup layout) {
    if (layout == null || view == null) {
      throw new IllegalArgumentException("Illegal argument: layout or corresponding view is null.");
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

  @Override
  public void onBackPressed() {
    ballGamePresenter.getGameTimer().cancel();
    finish();
  }
}

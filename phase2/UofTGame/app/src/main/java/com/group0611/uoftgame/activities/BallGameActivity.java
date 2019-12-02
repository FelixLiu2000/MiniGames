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
import com.group0611.uoftgame.games.ballgame.BallGameDirector;
import com.group0611.uoftgame.games.ballgame.BallGamePresenter;
import com.group0611.uoftgame.games.ballgame.GameConstants;
import com.group0611.uoftgame.utilities.AppManager;
import com.group0611.uoftgame.utilities.GameDifficulty;
import com.group0611.uoftgame.utilities.GameMode;

import java.util.ArrayList;

public class BallGameActivity extends AppCompatActivity implements GameActivity {

  private Intent currentIntent, toResultsPageIntent, toDashboardIntent;
  private BallGamePresenter ballGamePresenter;
  // Layout containing all ball views
  private LinearLayout ballLayout;
  // Player and game status/output text views
  private TextView player1ScoreView, player2ScoreView;
  private TextView player1LivesView, player2LivesView;
  private TextView timeView;
  private TextView angleView;
  private TextView powerView;
  private TextView playerNameView;
  // Angle and power control views
  private SeekBar angleControlView, powerControlView;
  // List of views for active balls
  private ArrayList<ImageView> ballViews = new ArrayList<>();

  // added by diego - add getters and setters if needed
  private boolean gameIsMultiplayer; // true if two player, false if one player
  private GameMode gameMode; // enum values of strings "TIMED" "INFINITE"
  private GameDifficulty gameDifficulty; // enum values of strings "EASY" "MEDIUM" "HARD"

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

    // Game options to be passed into director
    gameIsMultiplayer = appManager.getGameIsMultiPlayer();
    gameMode = appManager.getCurrentPlayerGameMode();
    gameDifficulty = appManager.getCurrentPlayerDifficulty();

    // Use director to construct ballgame with desired settings
    BallGameDirector director = new BallGameDirector(this, appManager);
    BallGame ballgame = director.constructGame(gameMode, gameDifficulty, gameIsMultiplayer);
    // Bind the constructed game and current activity to presenter
    ballGamePresenter = new BallGamePresenter(ballgame, this);
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
    // Initialize button views
    initializeGameButtons();
  }

  /** Main entry point into BallGame, initializes views and starts game. */
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    if (hasFocus) {
      // Initialize the text and seekbar views
      initializeGameViews();
      initializeGameSeekBars();
      // Game begins
      ballGamePresenter.startGame();
    }
  }

  private void initializeGameViews() {
    ballGamePresenter.initializePlayer(findViewById(R.id.picPlayer));
    ballGamePresenter.initializeTarget(findViewById(R.id.picTarget));
    // Give game control of layout used for ball views (also used for screen boundaries)
    ballLayout = findViewById(R.id.ballLayout);
    // ballGame.initializeLayouts(ballLayout);
    ballGamePresenter.initializeActiveArea(
        ballLayout.getLeft(), ballLayout.getTop(), ballLayout.getRight(), ballLayout.getBottom());
    // Capture game output views so that they can be updated when needed
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
            // Generates a view for the new ball being shot
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
    // Setup angle seekbar maximums and default position at game start
    angleControlView = findViewById(R.id.seekAngle);
    angleControlView.setMax(GameConstants.SHOT_MAX_ANGLE);
    angleControlView.setProgress(GameConstants.SHOT_STARTING_ANGLE, true);
    angleControlView.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int angle, boolean b) {
            // Update shot angle according to new seekbar position
            ballGamePresenter.updateShotAngle(angle);
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    // Setup power seekbar maximums and default position at game start
    powerControlView = findViewById(R.id.seekPower);
    powerControlView.setMax(GameConstants.SHOT_MAX_POWER);
    powerControlView.setProgress(GameConstants.SHOT_STARTING_POWER, true);
    powerControlView.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int power, boolean b) {
            // Update shot power according to new seekbar position
            ballGamePresenter.updateShotPower(power);
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {}
        });
  }

  /**
   * Updates the location of a given view.
   *
   * @param view the view being updated.
   * @param x x-coordinate of new location.
   * @param y y-coordinate of new location.
   */
  public void updateViewLocation(View view, float x, float y) {
    if (view == null) {
      throw new IllegalArgumentException("Illegal argument: view is null.");
    } else {
      view.setX(x);
      view.setY(y);
    }
  }

  /**
   * Updates the text displayed by a given text view.
   *
   * @param view the text view being updated.
   * @param str the new text to be displayed.
   */
  public void updateTextView(TextView view, String str) {
    if (view == null) {
      throw new IllegalArgumentException("Illegal argument: view is null.");
    } else {
      view.setText(str);
    }
  }

  /**
   * Removes a view from a given layout.
   *
   * @param view view to be removed.
   * @param layout layout to remove the given view from.
   */
  public void removeViewFromLayout(View view, ViewGroup layout) {
    if (layout == null || view == null) {
      throw new IllegalArgumentException("Illegal argument: layout or corresponding view is null.");
    } else {
      layout.removeView(view);
    }
  }

  @Override
  public void leaveGame(AppManager appManager) {
    appManager.setIsGameResults(true);
    setToResultsPageIntent(new Intent(BallGameActivity.this, ResultsPageActivity.class));
    getToResultsPageIntent().putExtra("appManager", appManager);
    startActivity(getToResultsPageIntent());
  }

  @Override
  public void onBackPressed() {
    // Forcibly ends the ballgame's timer to allow exit
    ballGamePresenter.getGameTimer().cancel();
    finish();
  }
}

package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.group0611.uoftgame.R;

import com.group0611.uoftgame.utilities.AppManager;
import com.group0611.uoftgame.utilities.DisplayNameChoices;
import com.group0611.uoftgame.utilities.GameMode;
import com.group0611.uoftgame.utilities.SaveManager;

public class GameDashboardActivity extends AppCompatActivity {

  AppManager appManager;
  Intent intentGameDashboard;
  ImageButton imageButtonSettings, imageButtonCardGame, imageButtonSubwayGame, imageButtonBallGame,
          imageButtonSwitchMultiPlayerMode, imageButtonStatsPage;
  TextView textViewDashboardHighScore, textViewTotalScore, textViewDisplayName, textViewDashboardHighScoreLabel,
          textViewTotalScoreLabel;
  ToggleButton toggleButtonGameMode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    intentGameDashboard = getIntent();
    this.appManager = (AppManager) intentGameDashboard.getSerializableExtra("appManager");
    setContentView(R.layout.activity_game_dashboard);

    getWindow().getDecorView().setBackgroundColor(appManager.getCurrentPlayer().getGameDashboardBackgroundColor());

    imageButtonCardGame = findViewById(R.id.cardGameButton);
    imageButtonSubwayGame = findViewById(R.id.subwayGameButton);
    imageButtonBallGame = findViewById(R.id.ballGameButton);
    toggleButtonGameMode = (ToggleButton) findViewById(R.id.toggleGameModeButton);
    if (appManager.getCurrentPlayer().getGameMode().equals(GameMode.TIMED)) {
        toggleButtonGameMode.setChecked(false);
    } else {
        toggleButtonGameMode.setChecked(true);
    }

    textViewDashboardHighScore = findViewById(R.id.highScoreDashboardStat);
    textViewTotalScore = findViewById(R.id.totalScoreStat);
    textViewDisplayName = findViewById(R.id.displayNameTextLabel);
    textViewDashboardHighScoreLabel = findViewById(R.id.highScoreLabel);
    textViewTotalScoreLabel = findViewById(R.id.totalScoreLabel);
    imageButtonSettings = findViewById(R.id.gameDashboardSettingsButton);
    imageButtonSwitchMultiPlayerMode = findViewById(R.id.switchMultiplayerDashboardButton);
    imageButtonStatsPage = findViewById(R.id.statsButtonDashboard);

    if (appManager.getGameIsMultiPlayer()) {
        textViewDashboardHighScore.setVisibility(View.INVISIBLE);
        textViewTotalScore.setVisibility(View.INVISIBLE);
        textViewTotalScoreLabel.setVisibility(View.INVISIBLE);
        textViewDashboardHighScoreLabel.setVisibility(View.INVISIBLE);
    } else {
        textViewDashboardHighScore.setVisibility(View.VISIBLE);
        textViewTotalScore.setVisibility(View.VISIBLE);
        textViewTotalScoreLabel.setVisibility(View.VISIBLE);
        textViewDashboardHighScoreLabel.setVisibility(View.VISIBLE);
    }

    // all buttons onClickListener declarations
    imageButtonCardGame.setOnClickListener(
            new View.OnClickListener() {
              public void onClick(View v) {
                Intent gameDashboardToCardGameIntent = new Intent(GameDashboardActivity.this, CardGameActivity.class);
                gameDashboardToCardGameIntent.putExtra("appManager", appManager);
                startActivity(gameDashboardToCardGameIntent);
              }
            });

    imageButtonSubwayGame.setOnClickListener(
            new View.OnClickListener() {
              public void onClick(View v) {
                Intent gameDashboardToSubwayGameIntent = new Intent(GameDashboardActivity.this, SubwayGameActivity.class);
                gameDashboardToSubwayGameIntent.putExtra("appManager", appManager);
                startActivity(gameDashboardToSubwayGameIntent);
              }
            });

    imageButtonBallGame.setOnClickListener(
            new View.OnClickListener() {
              public void onClick(View v) {
                Intent gameDashboardToBallGameIntent = new Intent(GameDashboardActivity.this, BallGameActivity.class);
                gameDashboardToBallGameIntent.putExtra("appManager", appManager);
                startActivity(gameDashboardToBallGameIntent);
              }
            });

    imageButtonSettings.setOnClickListener(
            new View.OnClickListener() {
              public void onClick(View v) {
                Intent gameDashboardToSettings = new Intent(GameDashboardActivity.this, SettingsActivity.class);
                gameDashboardToSettings.putExtra("appManager", appManager);
                startActivity(gameDashboardToSettings);
              }
            });

    imageButtonSwitchMultiPlayerMode.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    if (appManager.getGameIsMultiPlayer()) {
                        Intent gameDashboardToRemovePlayer = new Intent(GameDashboardActivity.this, RemovePlayerActivity.class);
                        gameDashboardToRemovePlayer.putExtra("appManager", appManager);
                        startActivity(gameDashboardToRemovePlayer);
                    } else {
                        appManager.setComingFromAddPlayer(true);
                        Intent gameDashboardToLogIn = new Intent(GameDashboardActivity.this, LogInActivity.class);
                        gameDashboardToLogIn.putExtra("appManager", appManager);
                        startActivity(gameDashboardToLogIn);
                    }
                }
            });

    toggleButtonGameMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                appManager.getPlayerOne().setGameMode(GameMode.INFINITE);
                if (appManager.getGameIsMultiPlayer()) {
                    appManager.getPlayerTwo().setGameMode(GameMode.INFINITE);
                }
            } else {
                appManager.getPlayerOne().setGameMode(GameMode.TIMED);
                if (appManager.getGameIsMultiPlayer()) {
                    appManager.getPlayerTwo().setGameMode(GameMode.TIMED);
                }
            }
            SaveManager.save(appManager.getPlayerOne());
            if (appManager.getGameIsMultiPlayer()) {
                SaveManager.save(appManager.getPlayerTwo());
            }
        }
    });

      imageButtonStatsPage.setOnClickListener(
              new View.OnClickListener() {
                  public void onClick(View v) {
                      appManager.setIsGameResults(false);
                      Intent gameDashboardToStats = new Intent(GameDashboardActivity.this, ResultsPageActivity.class);
                      gameDashboardToStats.putExtra("appManager", appManager);
                      startActivity(gameDashboardToStats);
                  }
              });

    // sets visible text fields based on logged in player
    textViewTotalScore.setText(String.valueOf(appManager.getCurrentPlayer().getTotalScore()));
    textViewDashboardHighScore.setText(String.valueOf(appManager.getCurrentPlayer().getHighScore()));

    if (appManager.getGameIsMultiPlayer()) {
        String displayNames = appManager.getPlayerOne().getUsername() + " VS " + appManager.getPlayerTwo().getUsername();
        textViewDisplayName.setText(displayNames);
    } else {
        if (appManager.getCurrentPlayerDisplayName().equals(DisplayNameChoices.USERNAME)) {
            textViewDisplayName.setText(appManager.getCurrentPlayer().getUsername());
        } else if (appManager.getCurrentPlayerDisplayName().equals(DisplayNameChoices.FIRSTNAME)) {
            textViewDisplayName.setText(appManager.getCurrentPlayer().getFirstName());
        } else if (appManager.getCurrentPlayerDisplayName().equals(DisplayNameChoices.LASTNAME)) {
            textViewDisplayName.setText(appManager.getCurrentPlayer().getLastName());
        }
    }

  }
}

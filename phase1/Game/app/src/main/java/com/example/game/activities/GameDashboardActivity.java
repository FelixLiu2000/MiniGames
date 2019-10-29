package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.game.R;

import com.example.game.utilities.AppManager;

public class GameDashboardActivity extends AppCompatActivity {

  AppManager appManager;
  Intent intentGameDashboard;
  ImageButton imageButtonPlay;
  TextView textViewHighScore;
  TextView textViewTotalRoundsPlayed;
  TextView  textViewTotalScore;
  TextView textViewDisplayName;
  ProgressBar progressBarRoundProgress;
  ImageButton imageButtonSettings;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    intentGameDashboard = getIntent();
    this.appManager = (AppManager) intentGameDashboard.getSerializableExtra("appManager");
    setContentView(R.layout.activity_game_dashboard);

    getWindow().getDecorView().setBackgroundColor(appManager.getCurrentPlayer().getGameDashboardBackgroundColor());

    imageButtonPlay = findViewById(R.id.gameDashboardPlayButton);
    textViewHighScore = findViewById(R.id.highScoreStat);
    textViewTotalRoundsPlayed = findViewById(R.id.totalRoundsPlayedStat);
    textViewTotalScore = findViewById(R.id.totalScoreStat);
    textViewDisplayName = findViewById(R.id.displayNameTextLabel);
    progressBarRoundProgress = findViewById(R.id.roundProgressBar);
    imageButtonSettings = findViewById(R.id.gameDashboardSettingsButton);

    imageButtonPlay.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            appManager.pickGameToPlay();
            Intent gameDashboardToCurrentGameIntent = new Intent(GameDashboardActivity.this, appManager.getGameToPlay());
//            gameDashboardToCurrentGameIntent.putExtra("appManager", appManager);
            startActivity(gameDashboardToCurrentGameIntent);
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

    textViewHighScore.setText(String.valueOf(appManager.getCurrentPlayer().getHighScore()));
    textViewTotalRoundsPlayed.setText(
        String.valueOf(appManager.getCurrentPlayer().getTotalRoundsPlayed()));
      textViewTotalScore.setText(String.valueOf(appManager.getCurrentPlayer().getTotalScore()));

    if (appManager.getCurrentPlayerDisplayName().equals("USERNAME")){
        textViewDisplayName.setText(String.valueOf(appManager.getCurrentPlayer().getUsername()));
    } else if (appManager.getCurrentPlayerDisplayName().equals("FIRST NAME")){
        textViewDisplayName.setText(String.valueOf(appManager.getCurrentPlayer().getFirstName()));
    } else if (appManager.getCurrentPlayerDisplayName().equals("LAST NAME")){
        textViewDisplayName.setText(String.valueOf(appManager.getCurrentPlayer().getLastName()));
    }

    progressBarRoundProgress.setProgress(appManager.getCurrentPlayer().getCurrentRoundProgress());
  }

  public void startGame(View view) {
    // Do something in response to button
    System.out.println("This would start the game at game corresponding to progress.");
  }
}

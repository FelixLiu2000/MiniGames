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

import org.w3c.dom.Text;

public class GameDashboardActivity extends AppCompatActivity {

  AppManager appManager;
  Intent intentGameDashboard;
  ImageButton imageButtonPlay;
  TextView textViewHighScore;
  TextView textViewTotalRoundsPlayed;
  TextView  textViewTotalScore;
  TextView textViewUsername;
  ProgressBar progressBarRoundProgress;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    intentGameDashboard = getIntent();
    this.appManager = (AppManager) intentGameDashboard.getSerializableExtra("appManager");
    setContentView(R.layout.activity_game_dashboard);

    imageButtonPlay = findViewById(R.id.playButton);
    imageButtonPlay.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            appManager.pickGameToPlay();
            Intent gameDashboardToCurrentGameIntent = new Intent(GameDashboardActivity.this, appManager.getGameToPlay());
            startActivity(gameDashboardToCurrentGameIntent);
          }
        });

    textViewHighScore = findViewById(R.id.highScoreStat);
    textViewTotalRoundsPlayed = findViewById(R.id.totalRoundsPlayedStat);
    textViewTotalScore = findViewById(R.id.totalScoreStat);
    textViewUsername = findViewById(R.id.usernameTextLabel);
    progressBarRoundProgress = findViewById(R.id.roundProgressBar);

    textViewHighScore.setText(String.valueOf(this.appManager.getCurrentPlayer().getHighScore()));
    textViewTotalRoundsPlayed.setText(
        String.valueOf(this.appManager.getCurrentPlayer().getTotalRoundsPlayed()));
    textViewTotalScore.setText(String.valueOf(this.appManager.getCurrentPlayer().getTotalScore()));
    textViewUsername.setText(String.valueOf(this.appManager.getCurrentPlayer().getUsername()));
    progressBarRoundProgress.setProgress(appManager.getCurrentPlayer().getCurrentRoundProgress());
  }

  public void startGame(View view) {
    // Do something in response to button
    System.out.println("This would start the game at game corresponding to progress.");
  }
}

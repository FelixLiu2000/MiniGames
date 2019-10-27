package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.game.R;

import com.example.game.utilities.AppManager;

public class GameDashboardActivity extends AppCompatActivity {

  AppManager appManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent appManagerIntent = getIntent();
    this.appManager = (AppManager) appManagerIntent.getSerializableExtra("appManager");
    setContentView(R.layout.activity_game_dashboard);

    ImageButton playButton = findViewById(R.id.playButton);
    playButton.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            appManager.pickGameToPlay();
            Intent gameDashboardToCurrentGameIntent = new Intent(GameDashboardActivity.this, appManager.getGameToPlay());
            startActivity(gameDashboardToCurrentGameIntent);
          }
        });

    TextView highScoreText = findViewById(R.id.highScoreStat);
    TextView totalRoundsPlayedText = findViewById(R.id.totalRoundsPlayedStat);
    TextView totalScoreText = findViewById(R.id.totalScoreStat);

    highScoreText.setText(String.valueOf(this.appManager.getCurrentPlayer().getHighScore()));
    totalRoundsPlayedText.setText(
        String.valueOf(this.appManager.getCurrentPlayer().getTotalRoundsPlayed()));
    totalScoreText.setText(String.valueOf(this.appManager.getCurrentPlayer().getTotalScore()));
  }

  public void startGame(View view) {
    // Do something in response to button
    System.out.println("This would start the game at game corresponding to progress.");
  }
}

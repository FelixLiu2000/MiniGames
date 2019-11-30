package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.utilities.AppManager;
import com.group0611.uoftgame.utilities.SaveManager;

public class ResultsPageActivity extends AppCompatActivity {

    AppManager appManager;
    Intent intentResultsPageActivity;
    TextView textViewScoreLabel, textViewSavedLabel, textViewHighScoreBannerLabel;
    Button buttonNextGame, buttonBackToDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentResultsPageActivity = getIntent();
        this.appManager = (AppManager) intentResultsPageActivity.getSerializableExtra("appManager");
        setContentView(R.layout.activity_results_page);
        textViewScoreLabel = findViewById(R.id.resultsScoreTextLabel);
        textViewSavedLabel = findViewById(R.id.resultsSavedLabel);
        textViewHighScoreBannerLabel = findViewById(R.id.resultsHighScoreBanner);
        buttonNextGame = findViewById(R.id.resultsNextGameButton);
        buttonBackToDashboard = findViewById(R.id.resultsBackToDashboardButton);
        String scoreText = "Your Score: " + appManager.getMainPlayer().getCurrentGameScore();
        textViewScoreLabel.setText(scoreText);
        if (appManager.getMainPlayer().getCurrentRoundProgress() != 2) {
            buttonNextGame.setEnabled(true);
            buttonNextGame.setVisibility(View.VISIBLE);
        }
        updatePlayer();

        buttonBackToDashboard.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent resultsToGameDashboard = new Intent(ResultsPageActivity.this, GameDashboardActivity.class);
                        resultsToGameDashboard.putExtra("appManager", appManager);
                        startActivity(resultsToGameDashboard);
                    }
                });

        buttonNextGame.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        appManager.pickGameToPlay();
                        Intent resultsToNextGame = new Intent(ResultsPageActivity.this, appManager.getGameToPlay());
                        resultsToNextGame.putExtra("appManager", appManager);
                        startActivity(resultsToNextGame);
                    }
                });
    }

    public void updatePlayer() {
        appManager.updatePlayerTotalScore();
        appManager.updatePlayerRoundScore();
        appManager.updatePlayerRoundProgress();
        if (appManager.getMainPlayer().getCurrentRoundProgress() == 3) {
            appManager.updatePlayerTotalRounds();
            boolean newHighScore = appManager.updatePlayerHighScore();
            if (newHighScore) {
                String highScoreBannerText = "NEW HIGH SCORE: " + appManager.getMainPlayer().getCurrentRoundScore();
                textViewHighScoreBannerLabel.setText(highScoreBannerText);
                textViewHighScoreBannerLabel.setVisibility(View.VISIBLE);
            }
            appManager.getMainPlayer().setCurrentRoundProgress(0);
            appManager.getMainPlayer().setCurrentRoundScore(0);
        }
        appManager.getMainPlayer().setCurrentGameScore(0);
        SaveManager.save(appManager.getMainPlayer());
        textViewSavedLabel.setEnabled(true);
    }
}

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
    Button buttonBackToDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentResultsPageActivity = getIntent();
        this.appManager = (AppManager) intentResultsPageActivity.getSerializableExtra("appManager");
        setContentView(R.layout.activity_results_page);
        textViewScoreLabel = findViewById(R.id.resultsScoreTextLabel);
        textViewSavedLabel = findViewById(R.id.resultsSavedLabel);
        textViewHighScoreBannerLabel = findViewById(R.id.resultsHighScoreBanner);
        buttonBackToDashboard = findViewById(R.id.resultsBackToDashboardButton);
        // String scoreText = "Your Score: " + appManager.getCurrentPlayer().getCurrentGameScore();
        // textViewScoreLabel.setText(scoreText);

        buttonBackToDashboard.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent resultsToGameDashboard = new Intent(ResultsPageActivity.this, GameDashboardActivity.class);
                        resultsToGameDashboard.putExtra("appManager", appManager);
                        startActivity(resultsToGameDashboard);
                    }
                });

    }
}

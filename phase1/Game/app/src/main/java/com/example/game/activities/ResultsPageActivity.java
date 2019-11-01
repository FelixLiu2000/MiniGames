package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.game.R;
import com.example.game.utilities.AppManager;
import com.example.game.utilities.SaveManager;

public class ResultsPageActivity extends AppCompatActivity {

    AppManager appManager;
    Intent intentResultsPageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentResultsPageActivity = getIntent();
        this.appManager = (AppManager) intentResultsPageActivity.getSerializableExtra("appManager");
        setContentView(R.layout.activity_results_page);
        updatePlayer();
    }

    public void updatePlayer() {
        appManager.updatePlayerTotalScore();
        appManager.updatePlayerRoundScore();
        appManager.updatePlayerRoundProgress();
        if (appManager.getCurrentPlayer().getCurrentRoundProgress() == 3) {
            appManager.updatePlayerTotalRounds();
            appManager.updatePlayerHighScore();
            appManager.getCurrentPlayer().setCurrentRoundProgress(0);
            appManager.getCurrentPlayer().setCurrentRoundScore(0);
        }
        appManager.getCurrentPlayer().setCurrentGameScore(0);
        SaveManager.save(appManager.getCurrentPlayer());
    }
}

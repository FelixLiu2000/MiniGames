package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.utilities.AppManager;

public class RemovePlayerActivity extends AppCompatActivity {

    Intent intentRemovePlayer;
    AppManager appManager;
    Button buttonPickPlayer1, buttonPickPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentRemovePlayer = getIntent();
        appManager = (AppManager) intentRemovePlayer.getSerializableExtra("appManager");
        setContentView(R.layout.activity_remove_player);

        buttonPickPlayer1 = findViewById(R.id.removePlayerKeepPlayer1);
        buttonPickPlayer2 = findViewById(R.id.removePlayerKeepPlayer2);

        buttonPickPlayer1.setText(appManager.getPlayerOne().getUsername());
        buttonPickPlayer2.setText(appManager.getPlayerTwo().getUsername());

        buttonPickPlayer1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        appManager.setCurrentPlayer(appManager.getPlayerOne());
                        appManager.setPlayerTwo(null);
                        appManager.setGameIsMultiPlayer(false);
                        appManager.setCurrentPlayerIsPlayerTwo(false);
                        Intent removePlayerToGameDashboard = new Intent(RemovePlayerActivity.this, GameDashboardActivity.class);
                        removePlayerToGameDashboard.putExtra("appManager", appManager);
                        startActivity(removePlayerToGameDashboard);
                    }
                });

        buttonPickPlayer2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        appManager.setCurrentPlayer(appManager.getPlayerTwo());
                        appManager.setPlayerOne(appManager.getPlayerTwo());
                        appManager.setPlayerTwo(null);
                        appManager.setGameIsMultiPlayer(false);
                        appManager.setCurrentPlayerIsPlayerTwo(false);
                        Intent removePlayerToGameDashboard = new Intent(RemovePlayerActivity.this, GameDashboardActivity.class);
                        removePlayerToGameDashboard.putExtra("appManager", appManager);
                        startActivity(removePlayerToGameDashboard);
                    }
                });
    }
}

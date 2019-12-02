package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.utilities.AppManager;

import java.util.HashMap;

public class ResultsPageActivity extends AppCompatActivity {

    AppManager appManager;
    Intent intentResultsPageActivity;
    TextView textViewGameLabel, textViewP1Username, textViewP2Username, textViewTotalScoreLabel,
            textViewP1TotalScore, textViewP2TotalScore, textViewTotalSuccessLabel, textViewP1TotalSuccess,
            textViewP2TotalSuccess, textViewTotalFailLabel, textViewP1TotalFail, textViewP2TotalFail,
            textViewTotalAttemptsLabel, textViewP1TotalAttempts, textViewP2TotalAttempts, textViewHighScoreLabel,
            textViewP1HighScore, textViewP2HighScore, textViewWinnerTGPLabel, textViewP1WinnerTGP, textViewP2WinnerTGP,
            textViewVSHistoryLabel, textViewP1VSHistory, textViewP2VSHistory;
    ImageButton buttonCardGameStats, buttonSubwayGameStats, buttonBallGameStats;
    Button buttonBackToDashboard;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentResultsPageActivity = getIntent();
        this.appManager = (AppManager) intentResultsPageActivity.getSerializableExtra("appManager");
        setContentView(R.layout.activity_results_page);

        // method to set all find id
        setAllStatFieldId();

        // set find id for buttons
        buttonBackToDashboard = findViewById(R.id.resultsBackToDashboardButton);
        buttonCardGameStats = findViewById(R.id.statCardGameButton);
        buttonBallGameStats = findViewById(R.id.statBallGameButton);
        buttonSubwayGameStats = findViewById(R.id.statSubwayGameButton);

        // set text for some fields that don't change
        textViewTotalScoreLabel.setText("Total Score:");
        textViewP1Username.setText(appManager.getPlayerOne().getUsername());
        textViewHighScoreLabel.setText("High Score:");

        // populates fields based on where results page is being called from and if its is multiplayer
        if (appManager.getIsGameResults()) {
            buttonSubwayGameStats.setVisibility(View.GONE);
            buttonBallGameStats.setVisibility(View.GONE);
            buttonCardGameStats.setVisibility(View.GONE);

            HashMap<String, Integer> gameResultsP1 = appManager.getPlayerOne().getPreviousGameStats();


            // set values to fields

            textViewP1TotalScore.setText(String.valueOf(gameResultsP1.get("Total Score")));
            textViewP1TotalSuccess.setText(String.valueOf(gameResultsP1.get("Total Successes")));
            textViewP1TotalFail.setText(String.valueOf(gameResultsP1.get("Total Failures")));
            if (gameResultsP1.get("High Score") == 0) textViewP1HighScore.setText("Not Quite!");
            else textViewP1HighScore.setText("New High Score!");

            // handles specific field names and values based on game played
            if (gameResultsP1.get("Game ID") == 1) { // cardGame
                setCardGameLabels();
                textViewP1TotalAttempts.setText(String.valueOf(gameResultsP1.get("Total Attempts")));

            } else if (gameResultsP1.get("Game ID") == 2) { // subwayGame
                setSubwayGameLabels();

            } else if (gameResultsP1.get("Game ID") == 3) { // ballGame
                setBallGameLabels();
                textViewP1TotalAttempts.setText(String.valueOf(gameResultsP1.get("Total Attempts")));
            }

            if (appManager.getGameIsMultiPlayer()) {
                HashMap<String, Integer> gameResultsP2 = appManager.getPlayerTwo().getPreviousGameStats();
                textViewP2Username.setText(appManager.getPlayerTwo().getUsername());
                textViewP2TotalScore.setText(String.valueOf(gameResultsP2.get("Total Score")));
                textViewP2TotalSuccess.setText(String.valueOf(gameResultsP2.get("Total Successes")));
                textViewP2TotalFail.setText(String.valueOf(gameResultsP2.get("Total Failures")));
                if (gameResultsP1.get("Game ID") != 2) textViewP2TotalAttempts.setText(String.valueOf(gameResultsP2.get("Total Attempts")));
                if (gameResultsP2.get("High Score") == 0) textViewP2HighScore.setText("Not Quite!");
                else textViewP2HighScore.setText("New High Score!");

                // added all fields for two player for both
                textViewWinnerTGPLabel.setText("You are the...");
                if (gameResultsP1.get("Winner") == 1) textViewP1WinnerTGP.setText("WINNER!");
                else textViewP1WinnerTGP.setText("LOSER!");
                if (gameResultsP2.get("Winner") == 1) textViewP2WinnerTGP.setText("WINNER!");
                else textViewP2WinnerTGP.setText("LOSER!");
                setTwoPlayerHistoryStats();

            }

        } else if (appManager.getGameIsMultiPlayer()){ // if results page is called from dashboard and is multiplayer
            buttonSubwayGameStats.setVisibility(View.GONE);
            buttonBallGameStats.setVisibility(View.GONE);
            buttonCardGameStats.setVisibility(View.GONE);

            textViewTotalScoreLabel.setText("Stats:");
            textViewP2Username.setText(appManager.getPlayerTwo().getUsername());
            setTwoPlayerHistoryStats();
            setP1MultiPlayerStats(appManager.getPlayerOne().getTwoPlayerStats());
            setP2MultiPlayerStats(appManager.getPlayerTwo().getTwoPlayerStats());

        } else { // if results page is called from dashboard and is single player
            buttonSubwayGameStats.setVisibility(View.VISIBLE);
            buttonBallGameStats.setVisibility(View.VISIBLE);
            buttonCardGameStats.setVisibility(View.VISIBLE);
            setCardGameOverallStats(appManager.getPlayerOne().getCardGameStats());
        }

        // game icon buttons change fields to show overall stats for those games dynamically
        buttonBackToDashboard.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent resultsToGameDashboard = new Intent(ResultsPageActivity.this, GameDashboardActivity.class);
                        resultsToGameDashboard.putExtra("appManager", appManager);
                        startActivity(resultsToGameDashboard);
                    }
                });

        buttonCardGameStats.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        setCardGameOverallStats(appManager.getPlayerOne().getCardGameStats());
                    }
                });

        buttonBallGameStats.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        setBallGameOverallStats(appManager.getCurrentPlayer().getBallGameStats());
                    }
                });

        buttonSubwayGameStats.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        setSubwayGameOverallStats(appManager.getPlayerOne().getSubwayGameStats());
                    }
                });

    }

    /**
     * Sets the labels for the stats fields for card game
     */
    public void setCardGameLabels() {
        textViewGameLabel.setText("Card Game:");
        textViewTotalSuccessLabel.setText("Total Correct Matches:");
        textViewTotalFailLabel.setText("Total Incorrect Matches:");
        textViewTotalAttemptsLabel.setText("Total Attempted Matches:");
    }

    /**
     * sets the stats values for player 1 for card game from the Hashmap
     * @param cardGameOverallStats the hashmap with stats values for card game player 1
     */
    public void setCardGameOverallStats(HashMap<String, Integer> cardGameOverallStats) {
        setCardGameLabels();
        setP1CommonStats(cardGameOverallStats);
        textViewP1TotalSuccess.setText(String.valueOf(cardGameOverallStats.get("Total Matches")));
        textViewP1TotalAttempts.setText(String.valueOf(cardGameOverallStats.get("Total Match Attempts")));
        textViewP1TotalFail.setText(String.valueOf(cardGameOverallStats.get("Total Mismatches")));
    }

    /**
     * sets stats labels for ball game
     */
    public void setBallGameLabels() {
        textViewGameLabel.setText("Ball Game:");
        textViewTotalSuccessLabel.setText("Total Makes:");
        textViewTotalFailLabel.setText("Total Misses:");
        textViewTotalAttemptsLabel.setText("Total Throws:");
    }

    /**
     * sets the stats values for player 1 for ball game from the hashmap
     * @param ballGameOverallStats the hashmap with the stats values for ball game player 1
     */
    public void setBallGameOverallStats(HashMap<String, Integer> ballGameOverallStats) {
        setBallGameLabels();
        setP1CommonStats(ballGameOverallStats);
        textViewP1TotalSuccess.setText(String.valueOf(ballGameOverallStats.get("Total Hits")));
        textViewP1TotalFail.setText(String.valueOf(ballGameOverallStats.get("Total Misses")));
        textViewP1TotalAttempts.setText(String.valueOf(ballGameOverallStats.get("Total Throws")));
  }

    /**
     * sets the stats labels for subway game
     */
    public void setSubwayGameLabels() {
        textViewGameLabel.setText("Subway Game:");
        textViewTotalSuccessLabel.setText("Total Coins Collected:");
        textViewTotalFailLabel.setText("Total Bins Hit:");
        textViewTotalAttemptsLabel.setText(null);
    }

    /**
     * sets the stats values for player 1 for subway game from the hashmap
     * @param subwayGameOverallStats the hashmap with the stats values for subway game for player 1
     */
    public void setSubwayGameOverallStats(HashMap<String, Integer> subwayGameOverallStats) {
        setSubwayGameLabels();
        setP1CommonStats(subwayGameOverallStats);
        textViewP1TotalSuccess.setText(String.valueOf(subwayGameOverallStats.get("Total Coins")));
        textViewP1TotalFail.setText(String.valueOf(subwayGameOverallStats.get("Total Obstacle Hits")));
        textViewP1TotalAttempts.setText(null);
  }

    /**
     * sets the general stats for player 1 that all games share
     * @param gameStats hashtable with player 1 stats
     */
    public void setP1CommonStats(HashMap<String, Integer> gameStats) {
        textViewWinnerTGPLabel.setText("Total Times Played");
        textViewP1TotalScore.setText(String.valueOf(gameStats.get("Total Score")));
        textViewP1HighScore.setText(String.valueOf(gameStats.get("High Score")));
        textViewP1WinnerTGP.setText(String.valueOf(gameStats.get("Total Times Played")));
    }

    /**
     * sets the stat values for playre 1 when the game mode is multi player
     * @param gameStats the hashmap with stats values for player 1
     */
    public void setP1MultiPlayerStats(HashMap<String, Integer> gameStats) {
        textViewWinnerTGPLabel.setText("Total VS Games Played:");
        textViewP1TotalScore.setText(String.valueOf(appManager.getPlayerOne().getTotalScore()));
        textViewP1HighScore.setText(String.valueOf(appManager.getPlayerOne().getHighScore()));
        textViewP1WinnerTGP.setText(String.valueOf(gameStats.get("Total Two Player Games")));
    }

    /**
     * sets the stats values for player 2 when game mode is multi player
     * @param gameStats the hashmap with stats values for player 2
     */
    public void setP2MultiPlayerStats(HashMap<String, Integer> gameStats) {
        textViewP2TotalScore.setText(String.valueOf(appManager.getPlayerTwo().getTotalScore()));
        textViewP2HighScore.setText(String.valueOf(appManager.getPlayerTwo().getHighScore()));
        textViewP2WinnerTGP.setText(String.valueOf(gameStats.get("Total Two Player Games")));
    }

    /**
     * sets the corresponding stats values the relate to the Two Player stats hash map such as win loss record
     */
    public void setTwoPlayerHistoryStats() {
        HashMap<String, Integer> twoPlayerResultsP1 = appManager.getPlayerOne().getTwoPlayerStats();
        HashMap<String, Integer> twoPlayerResultsP2 = appManager.getPlayerTwo().getTwoPlayerStats();

        textViewVSHistoryLabel.setText("WINS-LOSSES:");
        textViewP1VSHistory.setText(new StringBuilder().append(twoPlayerResultsP1.get("Total Wins")).append("-").append(twoPlayerResultsP1.get("Total Losses")));
        textViewP2VSHistory.setText(new StringBuilder().append(twoPlayerResultsP2.get("Total Wins")).append("-").append(twoPlayerResultsP2.get("Total Losses")));
    }

    /**
     * Sets all find id for all textView fields
     * makes reading above code easier
     */
    public void setAllStatFieldId() {
        textViewGameLabel = findViewById(R.id.statGameHeaderLabel);
        textViewP1Username = findViewById(R.id.statP1Label);
        textViewP2Username = findViewById(R.id.statP2Label);
        textViewTotalScoreLabel = findViewById(R.id.statScoreLabel);
        textViewP1TotalScore = findViewById(R.id.statScoreP1Label);
        textViewP2TotalScore = findViewById(R.id.statScoreP2Label);
        textViewTotalSuccessLabel = findViewById(R.id.statSuccessLabel);
        textViewP1TotalSuccess = findViewById(R.id.statSuccessP1Label);
        textViewP2TotalSuccess = findViewById(R.id.statSuccessP2Label);
        textViewTotalFailLabel = findViewById(R.id.statFailLabel);
        textViewP1TotalFail = findViewById(R.id.statFailP1Label);
        textViewP2TotalFail = findViewById(R.id.statFailP2Label);
        textViewTotalAttemptsLabel = findViewById(R.id.statTotalAttemptsLabel);
        textViewP1TotalAttempts = findViewById(R.id.statTotalAttemptsP1Label);
        textViewP2TotalAttempts = findViewById(R.id.statTotalAttemptsP2Label);
        textViewHighScoreLabel = findViewById(R.id.statHighScoreLabel);
        textViewP1HighScore = findViewById(R.id.statHighScoreP1Label);
        textViewP2HighScore = findViewById(R.id.statHighScoreP2Label);
        textViewWinnerTGPLabel = findViewById(R.id.statWinnerTGPLabel);
        textViewP1WinnerTGP = findViewById(R.id.statWinnerTGPP1Label);
        textViewP2WinnerTGP = findViewById(R.id.statWinnerTGPP2Label);
        textViewVSHistoryLabel = findViewById(R.id.statVSHistoryLabel);
        textViewP1VSHistory = findViewById(R.id.statVSHistoryP1Label);
        textViewP2VSHistory = findViewById(R.id.statVSHistoryP2Label);
    }

}

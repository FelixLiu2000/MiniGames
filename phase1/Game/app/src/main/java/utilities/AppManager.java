package utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.game.CardGameActivity;
import com.example.game.GameDashboardActivity;
import com.example.game.LogInActivity;

import java.io.Serializable;

public class AppManager implements Serializable {

    Context logInContext;
    private Class gameToPlay;
    private Player currentPlayer;

    public AppManager(Context context) {
        this.logInContext = context;
    }

    public void setGameToPlay () { pickGameToPlay(); }
    public Class getGameToPlay () {
        setGameToPlay();
        return this.gameToPlay;
    }
    public void setCurrentPlayer(Player currentPlayer) { this.currentPlayer = currentPlayer; }
    public Player getCurrentPlayer() { return this.currentPlayer; }


    public void createPlayer(String firstName, String lastName, String userName, String password) {
        Player currentPlayer = new Player(firstName, lastName, userName, password);
        this.currentPlayer = currentPlayer;
    }

    public void pickGameToPlay() {

        if (this.currentPlayer.getCurrentRoundProgress() == 0) {
            this.gameToPlay = CardGameActivity.class;
        } else if (this.currentPlayer.getCurrentRoundProgress() == 1) {
            // call game 2
        } else if (this.currentPlayer.getCurrentRoundProgress() == 2) {
            // call game 3
        } else if (this.currentPlayer.getCurrentRoundProgress() == 3) {
            // reset
        }


    }
}

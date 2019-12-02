package com.group0611.uoftgame.utilities;

import android.content.Context;
import android.graphics.Color;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("ConstantConditions")
public class AppManager implements Serializable {

  private Context logInContext;
  private Player currentPlayer, playerOne, playerTwo;
  private boolean gameIsMultiPlayer, comingFromAddPlayer, currentPlayerIsPlayerTwo, isGameResults;
  public AppManager(Context context) {
    this.logInContext = context;
    this.currentPlayer = null;
    this.playerOne = null;
    this.playerTwo = null;
  }

    /**
     * Method sets the current player
     * @param currentPlayer the player to which current player is set
     */
  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

    /**
     *  returns the current player
     * @return the current player
     */
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

    /**
     * sets player one
     * @param playerOne the player to which player one is set too
     */
  public void setPlayerOne (Player playerOne) { this.playerOne = playerOne;}

    /**
     * returns player one
     * @return player one
     */
  public Player getPlayerOne() { return playerOne;}

    /**
     * sets player two
     * @param playerTwo the player to which player two is set
     */
  public void setPlayerTwo (Player playerTwo) { this.playerTwo = playerTwo; }

    /**
     * returns player two
     * @return player two
     */
  public Player getPlayerTwo() { return playerTwo; }

    /**
     * boolean used in the create and log in pages to check if the use is at the start of the app or if
     * it is coming from the dashboard to add a second player
     * @param comingFromAddPlayer the boolean which dictates if the intent comes from dashboard for a
     *                            second player
     */
  public void setComingFromAddPlayer (boolean comingFromAddPlayer) { this.comingFromAddPlayer = comingFromAddPlayer; }

    /**
     * gets the boolean used in Create and Log In activity to check if this is adding a second player
     * @return the boolean checked
     */
  public boolean getComingFromAddPlayer () { return comingFromAddPlayer; }

    /**
     * set boolean to state if there are two players
     * @param gameIsMultiPlayer the boolean that states whether there are two players
     */
  public void setGameIsMultiPlayer (boolean gameIsMultiPlayer) { this.gameIsMultiPlayer = gameIsMultiPlayer; }

    /**
     * returns boolean to see if there are two players
     * @return the boolean returned to check
     */
  public boolean getGameIsMultiPlayer () { return gameIsMultiPlayer; }

    /**
     * boolean to see if the current player is player two
     * @param currentPlayerIsPlayerTwo the boolean this value is set too
     */
  public void setCurrentPlayerIsPlayerTwo (boolean currentPlayerIsPlayerTwo) { this.currentPlayerIsPlayerTwo = currentPlayerIsPlayerTwo; }

    /**
     * returns boolean to check if current player is player two
     * @return the boolean returned and checked
     */
  public boolean getCurrentPlayerIsPlayerTwo () { return currentPlayerIsPlayerTwo; }

  /**
   * sets whether the results stats page is being called from a finished game or not
   * @param isGameResults the boolean it is set too
   */
  public void setIsGameResults (boolean isGameResults) { this.isGameResults = isGameResults; }

  /**
   * returns whether the stats results page is game results or not
   * @return the boolean it returns
   */
  public boolean getIsGameResults () { return isGameResults; }

    /**
    * This method creates and returns a new player with the given parameters.
     * @param firstName is the first name of the player
     * @param lastName is the last name of the player
     * @param userName is the username of the player
     * @param password is the password of the player
    */
  public Player createPlayer(String firstName, String lastName, String userName, String password) {
    return (new Player(firstName, lastName, userName, password));
  }

    /**
     * This method saves any changes made in the Settings Activity and updates the respective value
     * for the player.
     * @param gameDashboardBackgroundColor is the chosen background color for some of the apps screens
     * @param gameDashboardDisplayName is the choice of which name to be displayed on the dashboard (first, last, or user)
     * @param chosenDifficulty is the choice of the game difficulty the player wishes to play at
     */
  public void saveCustomizationChanges(String gameDashboardBackgroundColor, DisplayNameChoices gameDashboardDisplayName, GameDifficulty chosenDifficulty) {
    int chosenColorInt = this.currentPlayer.getGameDashboardBackgroundColor();

    // check what the dashboard chosen color is.
    if (gameDashboardBackgroundColor.equals("WHITE")) chosenColorInt = Color.WHITE;
    else if (gameDashboardBackgroundColor.equals("RED")) chosenColorInt = Color.RED;
    else if (gameDashboardBackgroundColor.equals("GREEN")) chosenColorInt = Color.GREEN;
    else if (gameDashboardBackgroundColor.equals("BLUE")) chosenColorInt = Color.BLUE;
    else if (gameDashboardBackgroundColor.equals("YELLOW")) chosenColorInt = Color.YELLOW;

    // update all three values
    this.currentPlayer.setGameDashboardBackgroundColor(chosenColorInt);
    this.currentPlayer.setGameDifficulty(chosenDifficulty);
    this.currentPlayer.setDisplayNameChoice(gameDashboardDisplayName);

  }

    /**
     * returns the chosen background colour of the current player
     * @return chosen background colour
     */
  public String getCurrentPlayerCurrentDashboardColor() {
    int currentColor = currentPlayer.getGameDashboardBackgroundColor();
    if (currentColor == Color.WHITE) {
      return "WHITE";
    } else if (currentColor == Color.RED) {
      return "RED";
    } else if (currentColor == Color.GREEN) {
      return "GREEN";
    } else if (currentColor == Color.BLUE) {
      return "BLUE";
    } else if (currentColor == Color.YELLOW) {
      return "YELLOW";
    }
    return "WHITE";
  }

    /**
     * returns the chosen display name choice of the current player
     * @return chosen display name
     */
  public DisplayNameChoices getCurrentPlayerDisplayName() {
    return currentPlayer.getDisplayNameChoice();
  }

    /**
     * returns the chosen game difficulty
     * @return the chosen difficulty
     */
    public GameDifficulty getCurrentPlayerDifficulty() {
        return currentPlayer.getGameDifficulty();
    }

    /**
     * returns the Game mode chosen by the player - timed of infinite
     * @return the chosen Game mode
     */
    public GameMode getCurrentPlayerGameMode() { return currentPlayer.getGameMode(); }

    /**
     * method to switch the current player to the non current player during two player gameplay
     */
    public void switchCurrentPlayer() {
      if (playerTwo != null) {
          if (currentPlayerIsPlayerTwo) {
              currentPlayer = playerOne;
          } else {
              currentPlayer = playerTwo;
          }
          currentPlayerIsPlayerTwo = !currentPlayerIsPlayerTwo;
      }
    }

    /**
     * method to update the overall total score of the players all time history
     * @param player the player who is being updated
     * @param score the score the just got that is being added
     */
    private void updatePlayerTotalScore(Player player, int score) {
        player.setTotalScore(player.getTotalScore() + score);
    }

    /**
     * method to update the total history of games the player has played
     * @param player the player being updated
     */
    private void updatePlayerTotalGamesPlayed(Player player) {
        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1 );
    }

    /**
     * method to check and update all time high score if needed
     * @param player the player being checked and updated
     * @param score the score being compared to high score
     */
    private void updatePlayerHighScore(Player player, int score) {
        if (score > player.getHighScore()) {
            player.setHighScore(score);
        }
    }

    /**
     * this method updates the 3 main stats by calling those stats update method
     * check the code for which methods are being called and check those method's
     * java doc for more detail.
     * @param player player being updated
     * @param score the score of the game that was just played
     */
      public void updatePlayerMainStats(Player player, int score) {
        updatePlayerTotalScore(player, score);
        updatePlayerTotalGamesPlayed(player);
        updatePlayerHighScore(player, score);
      }

    /**
     * update method for the card game stats. Updates all the values in Player.cardGameStats
     * @param player the player being updated
     * @param totalScore the total score of pairs of cards matched regardless of mismatches
     * @param totalMatches the total number of correct matches
     * @param totalMisMatches the total number of incorrect matches
     * @param totalMatchAttempts the total number of attempts ( sum of matches and mismatches )
     */
      @SuppressWarnings("ConstantConditions")
      public void updatePlayerCardGameStats(Player player, int totalScore, int totalMatches, int totalMisMatches, int totalMatchAttempts) {
        updatePlayerMainStats(player, totalScore);
        HashMap<String, Integer> playerStats = player.getCardGameStats();

        player.getPreviousGameStats().put("High Score", 0);

        playerStats.put("Total Times Played", playerStats.get("Total Times Played") + 1 );
        playerStats.put("Total Score", playerStats.get("Total Score") + totalScore);
        playerStats.put("Total Match Attempts", playerStats.get("Total Match Attempts") + totalMatchAttempts);
        playerStats.put("Total Mismatches", playerStats.get("Total Mismatches") + totalMisMatches);
        playerStats.put("Total Matches", playerStats.get("Total Matches") + totalMatches);
        if (totalScore > playerStats.get("High Score")) {
          playerStats.put("High Score", totalScore);
          player.getPreviousGameStats().put("High Score", totalScore);
        }

        player.setCardGameStats(playerStats);
        SaveManager.save(player);
        updatePreviousGameStats(player, totalScore, totalMatchAttempts, totalMatches, totalMisMatches, 1);
      }

    /**
     * update method for the subway game stats. Updates all the values in Player.subwayGameStats
     * @param player the player being updated
     * @param totalScore the total value of all coins collected regardless of obstacles hit
     * @param totalCoins the total number of coins collected regardless their value in points
     * @param totalObstaclesHit the total number of obstacles hit
     */
      public void updatePlayerSubwayGameStats(Player player, int totalScore, int totalCoins,  int totalObstaclesHit){
        updatePlayerMainStats(player, totalCoins);
        HashMap<String, Integer> playerStats = player.getSubwayGameStats();

        player.getPreviousGameStats().put("High Score", 0);


        playerStats.put("Total Times Played", playerStats.get("Total Times Played") + 1 );
        playerStats.put("Total Score", playerStats.get("Total Score") + totalScore);
        playerStats.put("Total Coins", playerStats.get("Total Coins") + totalCoins);
        playerStats.put("Total Obstacle Hits", playerStats.get("Total Obstacle Hits") + totalObstaclesHit);
        if (totalScore > playerStats.get("High Score")) {
          playerStats.put("High Score", totalScore);
          player.getPreviousGameStats().put("High Score", totalScore);
        }

        player.setSubwayGameStats(playerStats);
        SaveManager.save(player);
        updatePreviousGameStats(player, totalScore, 0, totalCoins, totalObstaclesHit, 3);
      }

    /**
     * update method for ball game stats. Updates all the values in Player.ballGameStats
     * @param player player being updated
     * @param totalScore the total value of all completed throws
     * @param totalThrows total number of throw attempts (sum of hits and misses)
     * @param totalHits total number of successful throws
     * @param totalMisses total number of throws that missed
     */
      public void updatePlayerBallGameStats(Player player, int totalScore, int totalThrows, int totalHits, int totalMisses) {
        updatePlayerMainStats(player, totalScore);
        HashMap<String, Integer> playerStats = player.getBallGameStats();

        player.getPreviousGameStats().put("High Score", 0);


        playerStats.put("Total Times Played", playerStats.get("Total Times Played") + 1 );
        playerStats.put("Total Score", playerStats.get("Total Score") + totalScore);
        playerStats.put("Total Hits", playerStats.get("Total Hits") + totalHits);
        playerStats.put("Total Misses", playerStats.get("Total Misses") + totalMisses);
        playerStats.put("Total Throws", playerStats.get("Total Throws") + totalThrows);
        if (totalScore > playerStats.get("High Score")) {
          playerStats.put("High Score", totalScore);
          player.getPreviousGameStats().put("High Score", totalScore);
        }

        player.setBallGameStats(playerStats);
        SaveManager.save(player);
        updatePreviousGameStats(player, totalScore, totalThrows, totalHits, totalMisses, 2);
      }

    /**
     * updates the temporary Player.previousGameStats which is used for the result board right after the game.
     * @param player the player being updated
     * @param totalScore total points earned in the pervious game
     * @param totalAttempts total attempts (applies only to ball and card game)
     * @param totalSuccesses total number of successful moments (hitting target, matching pair, or collecting coin)
     * @param totalFailures total number of unsuccessful moments (missing target, non matching pair, hitting obstacle)
     * @param gameId a int game id to allow app manager to know which game it came from since not all fields apply
     *               all games.
     */
      public void updatePreviousGameStats(Player player, int totalScore, int totalAttempts, int totalSuccesses,
                                          int totalFailures, int gameId) {
          HashMap<String, Integer> previousGameStats = player.getPreviousGameStats();
          previousGameStats.put("Total Score", totalScore);
          previousGameStats.put("Total Attempts", totalAttempts);
          previousGameStats.put("Total Successes", totalSuccesses);
          previousGameStats.put("Total Failures", totalFailures);
          previousGameStats.put("Game ID", gameId);
          player.setPreviousGameStats(previousGameStats);
          SaveManager.save(player);
      }

    /**
     * method updates the history of the two players that played on who won and who lost
     * @param playerOneWon a boolean to state player one won if true, and lost if false
     */
      public void updateTwoPlayerStats(boolean playerOneWon) {
        if (playerOneWon) {
          playerOne.getTwoPlayerStats().put("Total Wins", playerOne.getTwoPlayerStats().get("Total Wins") + 1);
          playerOne.getPreviousGameStats().put("Winner", 1);
          playerTwo.getTwoPlayerStats().put("Total Losses", playerTwo.getTwoPlayerStats().get("Total Losses") + 1);
        } else {
          playerOne.getTwoPlayerStats().put("Total Losses", playerOne.getTwoPlayerStats().get("Total Losses") + 1);
          playerTwo.getPreviousGameStats().put("Winner", 1);
          playerTwo.getTwoPlayerStats().put("Total Wins", playerTwo.getTwoPlayerStats().get("Total Wins") + 1);
        }
        playerOne.getTwoPlayerStats().put("Total Two Player Games", playerOne.getTwoPlayerStats().get("Total Two Player Games") + 1);
        playerTwo.getTwoPlayerStats().put("Total Two Player Games", playerTwo.getTwoPlayerStats().get("Total Two Player Games") + 1);
        SaveManager.save(playerOne);
        SaveManager.save(playerTwo);
      }
}

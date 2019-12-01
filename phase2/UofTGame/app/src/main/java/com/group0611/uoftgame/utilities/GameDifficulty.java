package com.group0611.uoftgame.utilities;

public enum GameDifficulty {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    private String gameDifficulty;

    GameDifficulty(String gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public String getGameDifficulty() {
        return gameDifficulty;
    }
}

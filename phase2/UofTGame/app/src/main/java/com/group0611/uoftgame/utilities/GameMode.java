package com.group0611.uoftgame.utilities;

public enum GameMode {
    TIMED("TIMED"),
    INFINITE("INFINITE");

    private String gameMode;

    GameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameMode() {
        return gameMode;
    }
}

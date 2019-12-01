package com.group0611.uoftgame.utilities;

public enum DisplayNameChoices {
    USERNAME("USERNAME"),
    FIRSTNAME("FIRST NAME"),
    LASTNAME("LAST NAME");

    private String displayNameChoice;

    DisplayNameChoices(String displayNameChoice) {
        this.displayNameChoice = displayNameChoice;
    }

    public String getDisplayNameChoice() {
        return displayNameChoice;
    }
}

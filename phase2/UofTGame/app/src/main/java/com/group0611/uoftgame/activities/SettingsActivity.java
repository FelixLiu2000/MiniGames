package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.utilities.AppManager;
import com.group0611.uoftgame.utilities.SaveManager;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Majority of implementation of spinners throughout this class
    // was obtained via https://developer.android.com/guide/topics/ui/controls/spinner
    AppManager appManager;
    Intent intentSettings;
    Spinner spinnerColorChoice, spinnerDisplayNameChoice, spinnerDifficultyChoice;
    ArrayAdapter<CharSequence> arrayAdapterColorChoiceAdapter, arrayAdapterDisplayNameChoiceAdapter, arrayAdapterDifficultyChoiceAdapter;
    Button buttonCancel, buttonSave;
    String chosenColor, chosenDisplayName, chosenDifficulty;
    ArrayList<String> arrayListColorChoices, arrayListDisplayNameChoices, arrayListDifficultyChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentSettings = getIntent();
        appManager = (AppManager) intentSettings.getSerializableExtra("appManager");
        setContentView(R.layout.activity_settings);

        // sets preferred background color
        getWindow().getDecorView().setBackgroundColor(appManager.getCurrentPlayer().getGameDashboardBackgroundColor());

        // calls helper method to create spinners
        createSpinner(1, spinnerColorChoice, arrayAdapterColorChoiceAdapter);
        createSpinner(2, spinnerDisplayNameChoice, arrayAdapterDisplayNameChoiceAdapter);
        createSpinner(3, spinnerDifficultyChoice, arrayAdapterDifficultyChoiceAdapter);

        buttonCancel = findViewById(R.id.settingsCancelButton);
        buttonSave = findViewById(R.id.settingsSaveButton);
        arrayListColorChoices = new ArrayList<>(Arrays.asList("WHITE", "RED", "GREEN", "BLUE", "YELLOW"));
        arrayListDisplayNameChoices = new ArrayList<>(Arrays.asList("FIRST NAME", "LAST NAME", "USERNAME"));
        arrayListDifficultyChoices = new ArrayList<>(Arrays.asList("EASY", "MEDIUM", "HARD"));


        buttonCancel.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent settingsToGameDashboardCancel = new Intent(SettingsActivity.this, GameDashboardActivity.class);
                        settingsToGameDashboardCancel.putExtra("appManager", appManager);
                        startActivity(settingsToGameDashboardCancel);
                    }
                });

        buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        appManager.saveCustomizationChanges(chosenColor, chosenDisplayName, chosenDifficulty);
                        SaveManager.save(appManager.getCurrentPlayer());
                        Intent settingsToGameDashboardSave = new Intent(SettingsActivity.this, GameDashboardActivity.class);
                        settingsToGameDashboardSave.putExtra("appManager", appManager);
                        startActivity(settingsToGameDashboardSave);
                    }
                });

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String chosenItem = parent.getItemAtPosition(pos).toString();
        if (arrayListColorChoices.contains(chosenItem)) {
            chosenColor = parent.getItemAtPosition(pos).toString();
        } else if (arrayListDisplayNameChoices.contains(chosenItem)) {
            chosenDisplayName = parent.getItemAtPosition(pos).toString();
        } else if (arrayListDifficultyChoices.contains(chosenItem)) {
            chosenDifficulty = parent.getItemAtPosition(pos).toString();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void createSpinner(int spinnerInt, Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        String loadedChoice = null;

        // if statements house the code that is specific to each spinner despite similarities
        if (spinnerInt == 1){
            spinner = findViewById(R.id.colorChoiceSpinner);
            arrayAdapter = ArrayAdapter.createFromResource(this,
                    R.array.color_choices, android.R.layout.simple_spinner_item);
            this.chosenColor = appManager.getCurrentPlayerCurrentDashboardColor();
            loadedChoice = appManager.getCurrentPlayerCurrentDashboardColor();

        } else if (spinnerInt == 2) {

            spinner = findViewById(R.id.displayNameChoiceSpinner);
            arrayAdapter = ArrayAdapter.createFromResource(this,
                    R.array.display_name_choices, android.R.layout.simple_spinner_item);
            this.chosenDisplayName = appManager.getCurrentPlayerDisplayName();
            loadedChoice = appManager.getCurrentPlayerDisplayName();

        } else if (spinnerInt == 3) {

            spinner = findViewById(R.id.difficultyLevelChoiceSpinner);
            arrayAdapter = ArrayAdapter.createFromResource(this,
                    R.array.difficulty_level_choices, android.R.layout.simple_spinner_item);
            this.chosenDifficulty = appManager.getCurrentPlayerDifficulty();
            loadedChoice = appManager.getCurrentPlayerDifficulty();

        }
        // reusable code is written here to be used by any spinner
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        int loadedPosition = arrayAdapter.getPosition(loadedChoice);
        spinner.setSelection(loadedPosition);
    }
}

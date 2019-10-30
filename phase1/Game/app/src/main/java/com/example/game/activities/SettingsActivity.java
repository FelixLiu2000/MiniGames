package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.game.R;
import com.example.game.utilities.AppManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AppManager appManager;
    Intent intentSettings;
    Spinner spinnerColorChoice;
    Spinner spinnerDisplayNameChoice;
    ArrayAdapter<CharSequence> arrayAdapterColorChoiceAdapter;
    ArrayAdapter<CharSequence> arrayAdapterDisplayNameChoiceAdapter;
    Button buttonCancel;
    Button buttonSave;
    String chosenColor;
    String loadedColorChoice;
    int loadedColorPosition;
    String chosenDisplayName;
    String loadedDisplayNameChoice;
    int loadedDisplayNameChoicePosition;
    ArrayList<String> arrayListColorChoices;
    ArrayList<String> arrayListDisplayNameChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentSettings = getIntent();
        appManager = (AppManager) intentSettings.getSerializableExtra("appManager");
        setContentView(R.layout.activity_settings);

        getWindow().getDecorView().setBackgroundColor(appManager.getCurrentPlayer().getGameDashboardBackgroundColor());

        spinnerColorChoice = findViewById(R.id.colorChoiceSpinner);
        arrayAdapterColorChoiceAdapter = ArrayAdapter.createFromResource(this,
                R.array.color_choices, android.R.layout.simple_spinner_item);
        arrayAdapterColorChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColorChoice.setAdapter(arrayAdapterColorChoiceAdapter);
        spinnerColorChoice.setOnItemSelectedListener(this);
        chosenColor = appManager.getCurrentPlayerCurrentDashboardColor();
        loadedColorChoice = appManager.getCurrentPlayerCurrentDashboardColor();
        loadedColorPosition = arrayAdapterColorChoiceAdapter.getPosition(loadedColorChoice);
        spinnerColorChoice.setSelection(loadedColorPosition);

        spinnerDisplayNameChoice = findViewById(R.id.displayNameChoiceSpinner);
        arrayAdapterDisplayNameChoiceAdapter = ArrayAdapter.createFromResource(this,
                R.array.display_name_choices, android.R.layout.simple_spinner_item);
        arrayAdapterDisplayNameChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisplayNameChoice.setAdapter(arrayAdapterDisplayNameChoiceAdapter);
        spinnerDisplayNameChoice.setOnItemSelectedListener(this);
        chosenDisplayName = appManager.getCurrentPlayerDisplayName();
        loadedDisplayNameChoice = appManager.getCurrentPlayerDisplayName();
        loadedDisplayNameChoicePosition = arrayAdapterDisplayNameChoiceAdapter.getPosition(loadedDisplayNameChoice);
        spinnerDisplayNameChoice.setSelection(loadedDisplayNameChoicePosition);

        buttonCancel = findViewById(R.id.settingsCancelButton);
        buttonSave = findViewById(R.id.settingsSaveButton);
        arrayListColorChoices = new ArrayList<>(Arrays.asList("WHITE", "RED", "GREEN", "BLUE", "YELLOW"));
        arrayListDisplayNameChoices = new ArrayList<>(Arrays.asList("FIRST NAME", "LAST NAME", "USERNAME"));


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
                        appManager.saveCustomizationChanges(chosenColor, chosenDisplayName);
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
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

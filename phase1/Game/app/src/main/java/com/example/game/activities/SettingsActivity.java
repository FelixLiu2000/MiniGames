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

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AppManager appManager;
    Intent intentSettings;
    Spinner spinnerColorChoice;
    ArrayAdapter<CharSequence> arrayAdapterColorChoiceAdapter;
    Button buttonCancel;
    Button buttonSave;
    String chosenColor;
    String loadedColor;
    int loadedColorPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentSettings = getIntent();
        appManager = (AppManager) intentSettings.getSerializableExtra("appManager");
        setContentView(R.layout.activity_settings);
        spinnerColorChoice = findViewById(R.id.colorChoiceSpinner);
        arrayAdapterColorChoiceAdapter = ArrayAdapter.createFromResource(this,
                R.array.color_choices, android.R.layout.simple_spinner_item);
        arrayAdapterColorChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColorChoice.setAdapter(arrayAdapterColorChoiceAdapter);
        spinnerColorChoice.setOnItemSelectedListener(this);
        buttonCancel = findViewById(R.id.settingsCancelButton);
        buttonSave = findViewById(R.id.settingsSaveButton);
        loadedColor = appManager.getCurrentPlayerCurrentDashboardColor();
        loadedColorPosition = arrayAdapterColorChoiceAdapter.getPosition(loadedColor);
        spinnerColorChoice.setSelection(loadedColorPosition);

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
                        appManager.saveCustomizationChanges(chosenColor);
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
        chosenColor = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

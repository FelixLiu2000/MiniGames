package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.game.R;

import java.io.Serializable;

import com.example.game.utilities.AppManager;

public class LogInActivity extends AppCompatActivity implements Serializable {

    AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appManager = new AppManager(this);
        setContentView(R.layout.activity_log_in);

        Button playButton = findViewById(R.id.signInButton);

        playButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                appManager.createPlayer("Diego", "Nunez", "UNT", "PWT");
//                Player currentPlayer = appManager.getCurrentPlayer();
                Intent logInIntent = new Intent(LogInActivity.this, GameDashboardActivity.class);
                logInIntent.putExtra("appManager", appManager);
                startActivity(logInIntent);
            }
        });

        // THIS IS NEEDED ON APP START UP!
        checkFilePermissions();
    }

    /**
     * Needed to be able to save
     */
    private void checkFilePermissions(){
        boolean readGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED;
        boolean writeGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED;
        if(!readGranted || !writeGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }
}

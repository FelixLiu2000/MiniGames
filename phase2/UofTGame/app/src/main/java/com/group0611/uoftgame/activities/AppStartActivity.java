package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group0611.uoftgame.R;

import java.io.Serializable;

import com.group0611.uoftgame.utilities.AppManager;

public class AppStartActivity extends AppCompatActivity implements Serializable {

  AppManager appManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.appManager = new AppManager(this);
    setContentView(R.layout.activity_app_start);

    Button buttonCreateUserPage = findViewById(R.id.appStartCreateUserButton);
    Button buttonLogInUserPage = findViewById(R.id.appStartLogInButton);
    Button buttonSkipToDashboard = findViewById(R.id.appStartSkipToDashboardButton);

    buttonCreateUserPage.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            Intent intentAppStartToCreateUser = new Intent (AppStartActivity.this, CreateUserActivity.class);
            intentAppStartToCreateUser.putExtra("appManager", appManager);
            startActivity(intentAppStartToCreateUser);
          }
        });

    buttonLogInUserPage.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intentAppStartToLogInUser = new Intent (AppStartActivity.this, LogInActivity.class);
                    intentAppStartToLogInUser.putExtra("appManager", appManager);
                    startActivity(intentAppStartToLogInUser);
                }
            }
    );

    buttonSkipToDashboard.setOnClickListener(
            new View.OnClickListener() {
              public void onClick(View v) {
                appManager.createPlayer("Test", "Dummy", "UNT", "PWT");
                Intent intentAppStartToDashboard = new Intent(AppStartActivity.this, GameDashboardActivity.class);
                intentAppStartToDashboard.putExtra("appManager", appManager);
                startActivity(intentAppStartToDashboard);
              }
            });

    // THIS IS NEEDED ON APP START UP!
    checkFilePermissions();
  }

  /** Needed to be able to save */
  private void checkFilePermissions() {
    boolean readGranted =
        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED;
    boolean writeGranted =
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED;
    if (!readGranted || !writeGranted) {
      ActivityCompat.requestPermissions(
          this,
          new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
          },
          100);
    }
  }
}

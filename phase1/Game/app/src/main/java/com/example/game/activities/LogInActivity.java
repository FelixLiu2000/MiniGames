package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.game.R;
import com.example.game.utilities.AppManager;
import com.example.game.utilities.Player;
import com.example.game.utilities.SaveManager;

public class LogInActivity extends AppCompatActivity {

    AppManager appManager;
    Intent intentLogInUser;
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonSwitchToCreateUser;
    Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.intentLogInUser = getIntent();
        this.appManager = (AppManager) this.intentLogInUser.getSerializableExtra("appManager");
        setContentView(R.layout.activity_log_in);

        editTextUsername = findViewById(R.id.logInUserNameTextBox);
        editTextPassword = findViewById(R.id.logInPasswordTextBox);
        buttonSignIn = findViewById(R.id.logInSubmitLogInUserButton);
        buttonSwitchToCreateUser = findViewById(R.id.logInSwitchToLogInButton);

        editTextUsername.addTextChangedListener(logInUserPageTextWatcher);
        editTextPassword.addTextChangedListener(logInUserPageTextWatcher);

        buttonSwitchToCreateUser.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intentLogInToCreateUser = new Intent (LogInActivity.this, CreateUserActivity.class);
                        intentLogInToCreateUser.putExtra("appManager", appManager);
                        startActivity(intentLogInToCreateUser);

                    }
                }
        );

         // TODO: Make this check if user exists and return error message or log into that user
         buttonSignIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Player currentPlayer = SaveManager.loadPlayer(editTextUsername.getText().toString().trim());
                        if (currentPlayer == null) {
                            System.out.println("current player null");
                        } else if (currentPlayer.getPassword().equals(editTextPassword.getText().toString().trim())) {
                            appManager.setCurrentPlayer(currentPlayer);
                            Intent intentLogInToDashboard = new Intent (LogInActivity.this, GameDashboardActivity.class);
                            intentLogInToDashboard.putExtra("appManager", appManager);
                            startActivity(intentLogInToDashboard);
                        } else {
                            System.out.println("Password didn't match.");
                        }

                    }
                }
        );
    }

    private TextWatcher logInUserPageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = editTextUsername.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString().trim();

            buttonSignIn.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}

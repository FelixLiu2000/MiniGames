package com.group0611.uoftgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.group0611.uoftgame.R;
import com.group0611.uoftgame.utilities.AppManager;
import com.group0611.uoftgame.utilities.Player;
import com.group0611.uoftgame.utilities.SaveManager;

public class LogInActivity extends AppCompatActivity {

    AppManager appManager;
    Intent intentLogInUser;
    EditText editTextUsername, editTextPassword;
    Button buttonSwitchToCreateUser, buttonSignIn;
    TextView textViewErrorMessage;

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
        textViewErrorMessage = findViewById(R.id.logInErrorMessage);

        // some of the following code for disabling the button until the fields are filed in
        // was reused from the video https://www.youtube.com/watch?v=Vy_4sZ6JVHM
        // no code was copy pasted from any of the videos related links.
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

         buttonSignIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        textViewErrorMessage.setVisibility(View.INVISIBLE);
                        Player currentPlayer = SaveManager.loadPlayer(editTextUsername.getText().toString().trim());
                        if (currentPlayer == null) {
                            //show error message
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                        } else if (currentPlayer.getPassword().equals(editTextPassword.getText().toString().trim())) {
                            appManager.setCurrentPlayer(currentPlayer);
                            Intent intentLogInToDashboard = new Intent (LogInActivity.this, GameDashboardActivity.class);
                            intentLogInToDashboard.putExtra("appManager", appManager);
                            startActivity(intentLogInToDashboard);
                        } else {
                            //show error message
                            textViewErrorMessage.setVisibility(View.VISIBLE);
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

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

public class CreateUserActivity extends AppCompatActivity {

    AppManager appManager;
    Intent intentCreateUser;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSwitchToLogIn;
    private Button buttonCreateUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentCreateUser = getIntent();
        this.appManager = (AppManager) intentCreateUser.getSerializableExtra("appManager");
        setContentView(R.layout.activity_create_user);

        editTextFirstName = findViewById(R.id.firstNameTextBox);
        editTextLastName = findViewById(R.id.lastNameTextBox);
        editTextUsername = findViewById(R.id.userNameTextBox);
        editTextPassword = findViewById(R.id.passWordTextBox);
        buttonSwitchToLogIn = findViewById(R.id.switchToLogInButton);
        buttonCreateUser = findViewById(R.id.submitNewUserButton);

        editTextUsername.addTextChangedListener(createUserPageTextWatcher);
        editTextLastName.addTextChangedListener(createUserPageTextWatcher);
        editTextUsername.addTextChangedListener(createUserPageTextWatcher);
        editTextPassword.addTextChangedListener(createUserPageTextWatcher);

        buttonSwitchToLogIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent createUserToLogInIntent = new Intent (CreateUserActivity.this, LogInActivity.class);
                        createUserToLogInIntent.putExtra("appManager", appManager);
                        startActivity(createUserToLogInIntent);
                    }
                });

        buttonCreateUser.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        appManager.createPlayer(editTextFirstName.getText().toString().trim(),
                                editTextLastName.getText().toString().trim(),
                                editTextUsername.getText().toString().trim(),
                                editTextPassword.getText().toString().trim());
                        Intent createUserToGameDashboardIntent = new Intent (CreateUserActivity.this, GameDashboardActivity.class);
                        createUserToGameDashboardIntent.putExtra("appManager", appManager);
                        startActivity(createUserToGameDashboardIntent);
                    }
                });
    }

    private TextWatcher createUserPageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String firstNameInput = editTextFirstName.getText().toString().trim();
            String lastNameInput = editTextLastName.getText().toString().trim();
            String usernameInput = editTextUsername.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString().trim();

            buttonCreateUser.setEnabled(!firstNameInput.isEmpty() &&
                    !lastNameInput.isEmpty() &&
                    !usernameInput.isEmpty() &&
                    !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}

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
import com.example.game.utilities.SaveManager;

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

        editTextFirstName = findViewById(R.id.creatUserFirstNameTextBox);
        editTextLastName = findViewById(R.id.createUserLastNameTextBox);
        editTextUsername = findViewById(R.id.createUserUsernameTextBox);
        editTextPassword = findViewById(R.id.createUserPassWordTextBox);
        buttonSwitchToLogIn = findViewById(R.id.createUserSwitchToLogInButton);
        buttonCreateUser = findViewById(R.id.createUserSubmitButton);

        editTextUsername.addTextChangedListener(createUserPageTextWatcher);
        editTextLastName.addTextChangedListener(createUserPageTextWatcher);
        editTextUsername.addTextChangedListener(createUserPageTextWatcher);
        editTextPassword.addTextChangedListener(createUserPageTextWatcher);

        buttonSwitchToLogIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intentCreateUserToLogIn = new Intent (CreateUserActivity.this, LogInActivity.class);
                        intentCreateUserToLogIn.putExtra("appManager", appManager);
                        startActivity(intentCreateUserToLogIn);

                    }
                }
        );

        buttonCreateUser.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        appManager.createPlayer(editTextFirstName.getText().toString().trim(),
                                editTextLastName.getText().toString().trim(),
                                editTextUsername.getText().toString().trim(),
                                editTextPassword.getText().toString().trim());
                        SaveManager.save(appManager.getCurrentPlayer());
                        Intent intentCreateUserToGameDashboard = new Intent (CreateUserActivity.this, GameDashboardActivity.class);
                        intentCreateUserToGameDashboard.putExtra("appManager", appManager);
                        startActivity(intentCreateUserToGameDashboard);
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

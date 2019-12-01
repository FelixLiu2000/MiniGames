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

public class CreateUserActivity extends AppCompatActivity {

    AppManager appManager;
    Intent intentCreateUser;
    private EditText editTextFirstName, editTextLastName, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSwitchToLogIn, buttonCreateUser;
    private TextView textViewErrorMessage, textViewConfirmPasswordErrorMessage;


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
        editTextConfirmPassword = findViewById(R.id.createUserConfrimPassWordTextBox);
        buttonSwitchToLogIn = findViewById(R.id.createUserSwitchToLogInButton);
        buttonCreateUser = findViewById(R.id.createUserSubmitButton);
        textViewErrorMessage = findViewById(R.id.createUserErrorUsernameExists);
        textViewConfirmPasswordErrorMessage = findViewById(R.id.createUserErrorConfirmPassword);

        // some of the following code for disabling the button until the fields are filed in
        // was reused from the video https://www.youtube.com/watch?v=Vy_4sZ6JVHM
        // no code was copy pasted from any of the videos related links.
        editTextUsername.addTextChangedListener(createUserPageTextWatcher);
        editTextLastName.addTextChangedListener(createUserPageTextWatcher);
        editTextUsername.addTextChangedListener(createUserPageTextWatcher);
        editTextPassword.addTextChangedListener(createUserPageTextWatcher);
        editTextConfirmPassword.addTextChangedListener(createUserPageTextWatcher);

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
                        textViewErrorMessage.setVisibility(View.INVISIBLE);
                        String usernameInput = editTextUsername.getText().toString().trim();
                        Player playerSearch = SaveManager.loadPlayer(usernameInput);
                        if (playerSearch == null) {
                            Player newPlayer = appManager.createPlayer(editTextFirstName.getText().toString().trim(),
                                    editTextLastName.getText().toString().trim(),
                                    editTextUsername.getText().toString().trim(),
                                    editTextPassword.getText().toString().trim());
                            if (appManager.getComingFromAddPlayer()) {
                                appManager.setPlayerTwo(newPlayer);
                                appManager.setGameIsMultiPlayer(true);
                                appManager.setComingFromAddPlayer(false);
                            } else {
                                appManager.setCurrentPlayer(newPlayer);
                                appManager.setPlayerOne(newPlayer);
                            }
                            SaveManager.save(newPlayer);
                            Intent intentCreateUserToGameDashboard = new Intent (CreateUserActivity.this, GameDashboardActivity.class);
                            intentCreateUserToGameDashboard.putExtra("appManager", appManager);
                            startActivity(intentCreateUserToGameDashboard);
                        } else {
                            // Show error message
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

  private TextWatcher createUserPageTextWatcher =
      new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          String firstNameInput = editTextFirstName.getText().toString().trim();
          String lastNameInput = editTextLastName.getText().toString().trim();
          String usernameInput = editTextUsername.getText().toString().trim();
          String passwordInput = editTextPassword.getText().toString().trim();
          String confirmPasswordInput = editTextConfirmPassword.getText().toString().trim();

          boolean checkPasswordMatch = !passwordInput.isEmpty() && !confirmPasswordInput.isEmpty();

          boolean allFieldsFilled = !firstNameInput.isEmpty() &&
                  !lastNameInput.isEmpty() &&
                  !usernameInput.isEmpty() &&
                  checkPasswordMatch;

          boolean passwordsMatch = (!passwordInput.isEmpty() && !confirmPasswordInput.isEmpty()) &&
                  (passwordInput.equals(confirmPasswordInput));

          if (allFieldsFilled && passwordsMatch) {
                  textViewConfirmPasswordErrorMessage.setVisibility(View.INVISIBLE);
                  buttonCreateUser.setEnabled(true);
          }

          if (checkPasswordMatch && !passwordsMatch) {textViewConfirmPasswordErrorMessage.setVisibility(View.VISIBLE);}
          if (!allFieldsFilled || !passwordsMatch) {buttonCreateUser.setEnabled(false);}
        }

        @Override
        public void afterTextChanged(Editable s) {}
      };
}

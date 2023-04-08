package com.example.electronics_service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    EditText lastNameET;
    EditText firstNameET;
    EditText userEmailET;
    EditText userNameET;
    EditText passwordET;
    EditText passwordConfirmET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        lastNameET = findViewById(R.id.editTextLastName);
        firstNameET = findViewById(R.id.editTextFirstName);
        userEmailET = findViewById(R.id.editTextUserEmail);
        userNameET = findViewById(R.id.editTextUserName);
        passwordET = findViewById(R.id.editTextPassword);
        passwordConfirmET = findViewById(R.id.editTextPasswordConfirm);

        if (secret_key != 18) {
            finish();
        }
    }

    public void register(View view) {
        String lastName = lastNameET.getText().toString();
        String firstName = firstNameET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordConfirmET.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Log.i(LOG_TAG, "Given passwords don't match!");
        }
        else {
            Log.i(LOG_TAG, "Registered: " + userName + ", Full name: " + lastName + " " + firstName +
                    ", e-mail: " + userEmail + ", password: " + password);
        }
    }

    public void cancel(View view) {
        finish();
    }
}
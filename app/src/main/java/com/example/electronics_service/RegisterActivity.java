package com.example.electronics_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = Objects.requireNonNull(RegisterActivity.class.getPackage()).toString();

    EditText lastNameET;
    EditText firstNameET;
    EditText userEmailET;
    EditText userNameET;
    EditText passwordET;
    EditText passwordConfirmET;
    EditText phoneNumberET;
    Spinner spinner;
    EditText addressET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 18) {
            finish();
        }

        lastNameET = findViewById(R.id.editTextLastName);
        firstNameET = findViewById(R.id.editTextFirstName);
        userEmailET = findViewById(R.id.editTextUserEmail);
        userNameET = findViewById(R.id.editTextUserName);
        passwordET = findViewById(R.id.editTextPassword);
        passwordConfirmET = findViewById(R.id.editTextPasswordConfirm);
        phoneNumberET = findViewById(R.id.editTextPhoneNumber);
        spinner = findViewById(R.id.phoneSpinner);
        addressET = findViewById(R.id.editTextAddress);

        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userNameET.setText(userName);
        passwordET.setText(password);
        passwordConfirmET.setText(password);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phone_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void register(View view) {
        String lastName = lastNameET.getText().toString();
        String firstName = firstNameET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordConfirmET.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Given passwords don't match!");
            return;
        }

        String phoneNumber = phoneNumberET.getText().toString();
        String phoneType = spinner.getSelectedItem().toString();
        String address = addressET.getText().toString();

        Log.i(LOG_TAG, "Registered user: " + userName + ", Full name: " + lastName + " " + firstName +
                ", E-mail: " + userEmail + ", Password: " + password + ", Phone number: " + phoneNumber +
                ", Phone Type: " + phoneType + ", Address: " + address);
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();
        Log.i(LOG_TAG, selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
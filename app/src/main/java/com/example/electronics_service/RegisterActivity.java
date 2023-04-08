package com.example.electronics_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = Objects.requireNonNull(RegisterActivity.class.getPackage()).toString();

    EditText lastNameET;
    EditText firstNameET;
    EditText userEmailET;
    EditText passwordET;
    EditText passwordConfirmET;
    EditText phoneNumberET;
    Spinner spinner;
    EditText addressET;

    private FirebaseAuth mAuth;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lastNameET = findViewById(R.id.editTextLastName);
        firstNameET = findViewById(R.id.editTextFirstName);
        userEmailET = findViewById(R.id.editTextUserEmail);
        passwordET = findViewById(R.id.editTextPassword);
        passwordConfirmET = findViewById(R.id.editTextPasswordConfirm);
        phoneNumberET = findViewById(R.id.editTextPhoneNumber);
        spinner = findViewById(R.id.phoneSpinner);
        addressET = findViewById(R.id.editTextAddress);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userEmail = preferences.getString("userEmail", "");
        String password = preferences.getString("password", "");

        userEmailET.setText(userEmail);
        passwordET.setText(password);
        passwordConfirmET.setText(password);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phone_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String lastName = lastNameET.getText().toString();
        String firstName = firstNameET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordConfirmET.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Given passwords don't match!");
            return;
        }

        String phoneNumber = phoneNumberET.getText().toString();
        String phoneType = spinner.getSelectedItem().toString();
        String address = addressET.getText().toString();

        mAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "User has been created");
                    Log.i(LOG_TAG, "Registered user: Full name: " + lastName + " " + firstName +
                            ", E-mail: " + userEmail + ", Password: " + password + ", Phone number: " + phoneNumber +
                            ", Phone Type: " + phoneType + ", Address: " + address);
                    toHome();
                }
                else {
                    Log.w(LOG_TAG, "User creation failed");
                    Toast.makeText(RegisterActivity.this, "User creation failed: " +
                            Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    public void toHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
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

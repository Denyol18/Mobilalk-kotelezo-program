package com.example.electronics_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getName();
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }
    }

    public void toAppointmentBook(View view) {
        Intent intent = new Intent(this, AppointmentBookActivity.class);
        startActivity(intent);
    }

    public void toMyAppointments(View view) {
        Intent intent = new Intent(this, MyAppointmentsActivity.class);
        startActivity(intent);
    }

    public void toContacts(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}
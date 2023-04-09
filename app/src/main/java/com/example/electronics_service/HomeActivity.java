package com.example.electronics_service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
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

    public void signOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Kijelentkezés").setMessage("Biztosan ki szeretne jelentkezni?")
                        .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAuth.signOut();
                                startActivity(intent);
                                Log.d(LOG_TAG, "User logged out.");
                            }
                        }).setNegativeButton("Nem", null).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Kijelentkezés").setMessage("Biztosan ki szeretne jelentkezni?")
                .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(intent);
                        Log.d(LOG_TAG, "User logged out.");
                    }
                }).setNegativeButton("Nem", null).show();
    }
}
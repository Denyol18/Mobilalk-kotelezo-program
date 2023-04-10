package com.example.electronics_service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MyAppointmentsActivity extends AppCompatActivity {
    private static final String LOG_TAG = MyAppointmentsActivity.class.getName();
    private FirebaseUser user;

    private RecyclerView mRecyclerView;
    private ArrayList<Appointment> mAppointmentList;
    private AppointmentAdapter mAdapter;

    private FirebaseFirestore mStore;
    private CollectionReference mAppointments;
    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mAppointmentList = new ArrayList<>();

        mAdapter = new AppointmentAdapter(this, mAppointmentList);
        mRecyclerView.setAdapter(mAdapter);

        mStore = FirebaseFirestore.getInstance();
        mAppointments = mStore.collection("Appointments");

        queryData();
    }

    private void queryData() {
        mAppointmentList.clear();

        mAppointments.orderBy("device", Query.Direction.ASCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Map<String, String> user1 = (Map<String, String>) document.get("user");
                String email = user1.get("email");
                if (Objects.equals(email, user.getEmail())) {
                    Appointment appointment = document.toObject(Appointment.class);
                    mAppointmentList.add(appointment);
                    Log.d(LOG_TAG, "IT WORKS BABY!!! :DDDD");
                }
            }

            if (mAppointmentList.size() == 0) {
                noContent();
            }

            mAdapter.notifyDataSetChanged();
        });
    }

    public void noContent() {
        Intent intent = new Intent(this, HomeActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Időpontok").setMessage("Önnek nincs időpontja!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(intent);
                        Log.d(LOG_TAG, "sad");
                    }
                }).show();
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
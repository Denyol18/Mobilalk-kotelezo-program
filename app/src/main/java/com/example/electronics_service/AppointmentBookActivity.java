package com.example.electronics_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Objects;

public class AppointmentBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = AppointmentBookActivity.class.getName();
    private FirebaseUser user;

    User loggedInUser;

    Spinner spinner;
    TextView dateChosenTV;
    TextView timeChosenTV;
    EditText descriptionET;

    private FirebaseFirestore mStore;
    private CollectionReference mUsers;
    private CollectionReference mAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_book);

        mStore = FirebaseFirestore.getInstance();
        mUsers = mStore.collection("Users");
        mAppointments = mStore.collection("Appointments");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user");
            mUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (Objects.equals(document.getString("email"), user.getEmail())) {
                                loggedInUser = new User(
                                        document.getString("lastName"),
                                        document.getString("firstName"),
                                        document.getString("email"),
                                        document.getString("phoneNumber"),
                                        document.getString("phoneType"),
                                        document.getString("address"));
                            }
                        }
                    } else {
                        Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        } else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }

        spinner = findViewById(R.id.deviceSpinner);
        dateChosenTV = findViewById(R.id.dateChosenTextView);
        timeChosenTV = findViewById(R.id.timeChosenTextView);
        descriptionET = findViewById(R.id.editTextDescription);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.device_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void getAppointment(View view) {
        String device = spinner.getSelectedItem().toString();
        String date = dateChosenTV.getText().toString() + ", " + timeChosenTV.getText().toString();
        String description = descriptionET.getText().toString();

        Appointment appointment = new Appointment(loggedInUser, device, date, description);
        mAppointments.add(appointment);
        toMyAppointments();
    }

    public void toMyAppointments() {
        Intent intent = new Intent(this, MyAppointmentsActivity.class);
        startActivity(intent);
    }

    public void pickDate(View view) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentBookActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        dateChosenTV.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void pickTime(View view) {
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentBookActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        timeChosenTV.setText(time);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
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
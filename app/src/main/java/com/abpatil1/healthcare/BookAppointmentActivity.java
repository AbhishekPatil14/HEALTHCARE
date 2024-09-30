package com.abpatil1.healthcare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    // UI components
    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    Button dateButton, timeButton, btnBook, btnBack;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // Initialize views
        ed1 = findViewById(R.id.editTextAppFullNamE);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextAppContactNumber);
        ed4 = findViewById(R.id.editTextAppFees);
        tv = findViewById(R.id.textViewAppPackageName); // Assuming the TextView ID is textViewTitle
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBook = findViewById(R.id.buttonAppBookAppoinment);
        btnBack = findViewById(R.id.buttonAppBack);

        // Set EditText fields to non-editable
        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        // Get data from Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("text1");
        String fullname = intent.getStringExtra("text2");
        String address = intent.getStringExtra("text3");
        String contact = intent.getStringExtra("text4");
        String fees = intent.getStringExtra("text5");

        // Set data to views
        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText("Cons Fees:"+fees+"/-");

        // Initialize DatePicker and TimePicker
        initDatePicker();
        initTimePicker();

        // Set up DatePicker button
        dateButton.setOnClickListener(v -> datePickerDialog.show());

        // Set up TimePicker button
        timeButton.setOnClickListener(v -> timePickerDialog.show());

        // Back button click listener
        btnBack.setOnClickListener(v -> startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class)));

        // Book button click listener
        btnBook.setOnClickListener(v -> {
            Database db = new Database(getApplicationContext());
            SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedpreferences.getString("username", "");

            // Check if appointment already exists
            if (db.checkAppointmentExists(username, title + " => " + fullname, address, contact, dateButton.getText().toString(), timeButton.getText().toString()) == 1) {
                Toast.makeText(getApplicationContext(), "Appointment Already Booked", Toast.LENGTH_LONG).show();
            } else {
                // Add appointment to the database
                db.addOrder(username, title + " => " + fullname, address, contact, 0, dateButton.getText().toString(), timeButton.getText().toString(), Float.parseFloat(fees), "appointment");
               Toast.makeText(getApplicationContext(), "Your appointment done successfully", Toast.LENGTH_LONG).show();
               startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
            }
        });
    }

    // Initialize DatePickerDialog
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
               i1=i1+1;
               dateButton.setText(i2+"/"+i1+"/"+i);
            }
        };


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = DatePickerDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    // Initialize TimePickerDialog
    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
            String time = String.format("%02d:%02d", hour, minute);
            timeButton.setText(time);
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        int style = TimePickerDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

package com.example.kachow_now;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class submit_report extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private DatabaseReference dB;
    private Button submit;
    private TextView reportTitle;
    private EditText complaintee;
    private EditText mealOrdered;
    private TextView date;
    private EditText day;
    private EditText month;
    private EditText year;
    private EditText textReview;

    private String cUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        database = FirebaseDatabase.getInstance().getReference("LOG");
        dB = FirebaseDatabase.getInstance().getReference("UID");
        mAuth = FirebaseAuth.getInstance();

        cUID = getIntent().getExtras().getString("UID");


        submit = (Button) findViewById(R.id.submitButton);
        reportTitle = (TextView) findViewById(R.id.submitreporttitle);
        complaintee = (EditText) findViewById(R.id.setComplaintee);
        mealOrdered = (EditText) findViewById(R.id.setMealOrdered);
        date = (TextView) findViewById(R.id.dateTitle);
        day = (EditText) findViewById(R.id.day);
        month = (EditText) findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);
        textReview = (EditText) findViewById(R.id.setTextReview);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    sendReportToDatabase();
                    setContentView(R.layout.activity_clienthomepage);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(submit_report.this, "All fields must be filled out.", Toast.LENGTH_LONG).show();
                }



            }
        });

    }
    public void sendReportToDatabase(){
        String cookToComplain = complaintee.getText().toString().trim();
        String mealReview = mealOrdered.getText().toString().trim();
        String dayOfReview = day.getText().toString().trim();
        String monthOfReview = month.getText().toString().trim();
        String yearOfReview = year.getText().toString().trim();
        String textBoxReview = textReview.getText().toString().trim();

        if (cookToComplain.isEmpty() || mealReview.isEmpty() || dayOfReview.isEmpty()
                || monthOfReview.isEmpty() || yearOfReview.isEmpty() || textBoxReview.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (dayOfReview.length() != 2 || monthOfReview.length() != 2 || yearOfReview.length() != 4) {
            Toast.makeText(submit_report.this, "Invalid date", Toast.LENGTH_LONG).show();
            throw new NumberFormatException();

        }


        Cook tmpCook = new Cook();
        dB.child(cUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot cookSnapshot) {
                for (DataSnapshot c : cookSnapshot.getChildren()) {
                    System.out.println(c.getKey());
                }
                Cook tmpCook = new Cook();
                tmpCook.setUID(cookSnapshot.child("uid").getValue(String.class));
                tmpCook.setAddress(cookSnapshot.child("address").getValue(String.class));

                tmpCook.setBank(cookSnapshot.child("bank").getValue(new GenericTypeIndicator<ArrayList<Long>>() {
                }));

                tmpCook.setDaySus(cookSnapshot.child("daySus").getValue(Integer.class));
                tmpCook.setDescription(cookSnapshot.child("description").getValue(String.class));
                tmpCook.setEmail(cookSnapshot.child("email").getValue(String.class));
                tmpCook.setFirstName(cookSnapshot.child("firstName").getValue(String.class));
                tmpCook.setIsBanned(cookSnapshot.child("isBanned").getValue(boolean.class));
                tmpCook.setIsSuspended(cookSnapshot.child("isSuspended").getValue(boolean.class));
                tmpCook.setLastName(cookSnapshot.child("lastName").getValue(String.class));
                tmpCook.setPassword(cookSnapshot.child("password").getValue(String.class));
                tmpCook.setPhoneNumber(cookSnapshot.child("phoneNumber").getValue(long.class));
                tmpCook.setPostalCode(cookSnapshot.child("postalCode").getValue(String.class));
                tmpCook.setRating(cookSnapshot.child("rating").getValue(Integer.class));
                tmpCook.setRole(cookSnapshot.child("role").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Complaint comp = new Complaint(mealReview, textBoxReview, tmpCook, Integer.parseInt(dayOfReview),
                Integer.parseInt(monthOfReview), Integer.parseInt(yearOfReview));

        database.child(String.valueOf(System.currentTimeMillis())).setValue(comp);

        Toast.makeText(submit_report.this, "Report has been sent.", Toast.LENGTH_LONG).show();
        finish();
    }






}
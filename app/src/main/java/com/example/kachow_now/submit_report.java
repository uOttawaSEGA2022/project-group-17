package com.example.kachow_now;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class submit_report extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private Button submit = (Button)findViewById(R.id.submitButton);
    private TextView reportTitle = (TextView) findViewById(R.id.submitreporttitle);
    private EditText complaintee = (EditText) findViewById(R.id.setComplaintee);
    private EditText mealOrdered = (EditText) findViewById(R.id.setMealOrdered);
    private TextView date = (TextView) findViewById(R.id.dateTitle);
    private EditText day = (EditText) findViewById(R.id.day);
    private EditText month = (EditText) findViewById(R.id.month);
    private EditText year = (EditText) findViewById(R.id.year);
    private EditText textReview = (EditText) findViewById(R.id.setTextReview);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        database = FirebaseDatabase.getInstance().getReference("LOG");
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    sendReportToDatabase();
                    setContentView(R.layout.activity_clienthomepage);
                }
                catch (IllegalArgumentException e) {
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
        || monthOfReview.isEmpty() || yearOfReview.isEmpty() || textBoxReview.isEmpty()){
            throw new IllegalArgumentException();
        }
        if (dayOfReview.length()!=2 || monthOfReview.length()!= 2 || yearOfReview.length()!=4){
            Toast.makeText(submit_report.this, "Invalid date", Toast.LENGTH_LONG).show();
            throw new NumberFormatException();

        }

        // TODO
        Cook temp2 = new Cook("kevin", "dang", "1234567","kdang038@uottawa", "2302 apple st", "K6V 34A", 2139539306, 12345764,248,68345);

        Complaint comp = new Complaint(mealReview, textBoxReview, temp2, Integer.parseInt(dayOfReview),
        Integer.parseInt(monthOfReview), Integer.parseInt(yearOfReview));

        database.child(String.valueOf(System.currentTimeMillis())).setValue(comp);

        Toast.makeText(submit_report.this, "Report has been sent.", Toast.LENGTH_LONG).show();
        finish();
    }






}
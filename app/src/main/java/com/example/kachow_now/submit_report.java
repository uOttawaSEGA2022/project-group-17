package com.example.kachow_now;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class submit_report extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private Button submit;
    private TextView reportTitle;
    private EditText complaintee;
    private EditText mealOrdered;
    private TextView date;
    private EditText day;
    private EditText month;
    private EditText year;
    private EditText textReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        database = FirebaseDatabase.getInstance().getReference("LOG");
        mAuth = FirebaseAuth.getInstance();

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
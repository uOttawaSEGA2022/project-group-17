package com.example.kachow_now;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CookProfileClientSide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile_client_side);

        Button reportButton = (Button) findViewById(R.id.ReportCookProfileClient);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO //
                //setContentView(R.layout.activity_submit_report);
            }
        });

    }


}
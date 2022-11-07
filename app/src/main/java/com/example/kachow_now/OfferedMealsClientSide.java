package com.example.kachow_now;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OfferedMealsClientSide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_offered_meals_client_side);

        ImageButton chefIcon = (ImageButton) findViewById(R.id.cheficon);

        chefIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_cook_profile_client_side);
            }
        });
    }
}
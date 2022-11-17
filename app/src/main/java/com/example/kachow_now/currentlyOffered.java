package com.example.kachow_now;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class currentlyOffered extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu);

        //TODO add current offered mealList stuff here (edited to have switch one)

        Button addMeal = (Button) findViewById(R.id.addMeal);

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), add_meal.class);
                startActivity(intent);
            }
        });
    }
}

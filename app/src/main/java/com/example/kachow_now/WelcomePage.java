package com.example.kachow_now;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = this.getIntent().getParcelableExtra("CurrentUser");

            //TODO Edit text on welcome screen
            // access db and get information
        }

        public void logOut(View view){
            finish();
        }

    }

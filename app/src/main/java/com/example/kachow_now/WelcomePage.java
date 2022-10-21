package com.example.kachow_now;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class WelcomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;


        TextView Name = (TextView) findViewById(R.id.clientname);
        TextView Role = (TextView) findViewById(R.id.textView7);
        Button logOutButton = (Button) findViewById(R.id.logoutButton);//not very useful but will link anyway
        Button continueButton = (Button) findViewById(R.id.Continue);

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = this.getIntent().getParcelableExtra("CurrentUser");

            //TODO Edit text on welcome screen
            // access db and get information

            Name.setText("Name");
            Role.setText("Role");

        }
        public void logOut(View view){
            finish();
        }

    }

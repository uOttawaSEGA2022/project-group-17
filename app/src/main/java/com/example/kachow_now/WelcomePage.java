package com.example.kachow_now;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class WelcomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;



        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);

            TextView Name = (TextView) findViewById(R.id.clientname);
            TextView Role = (TextView) findViewById(R.id.textView7);
            Button logOutButton = (Button) findViewById(R.id.logoutButton);//not very useful but will link anyway
            Button continueButton = (Button) findViewById(R.id.Continue);

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = this.getIntent().getParcelableExtra("CurrentUser");

            //TODO Edit text on welcome screen
            // access db and get information

            Name.setText("Name");
            Role.setText("Role");

            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WelcomePage.this, "Menu Pages Under Development")
                    //setContentView(R.layout.activity_home);
                }
            });
            logOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logOut(view);
                }
            });
        }
        public void logOut(View view){
            finish();
        }

    }

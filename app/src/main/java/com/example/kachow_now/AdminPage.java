package com.example.kachow_now;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        mAuth = FirebaseAuth.getInstance();
        //dB = FirebaseDatabase.getInstance().getReference("UID");
        Button adminLogoutButton = (Button) findViewById(R.id.adminLogoutButton);


        adminLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut(view);
            }
        });
    }
    public void logOut(View view){
        mAuth.signOut();
        finish();
    }
}
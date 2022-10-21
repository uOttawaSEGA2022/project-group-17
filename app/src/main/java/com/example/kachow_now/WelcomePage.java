package com.example.kachow_now;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class WelcomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;



        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);

            TextView Name = (TextView) findViewById(R.id.clientname);
            TextView Role = (TextView) findViewById(R.id.textView7);
            Button logOutButton = (Button) findViewById(R.id.logoutButton);//not very useful but will link anyway
            Button continueButton = (Button) findViewById(R.id.Continue);

            mAuth = FirebaseAuth.getInstance();
            dB = FirebaseDatabase.getInstance().getReference("UID");
            FirebaseUser user = mAuth.getCurrentUser();
            dB.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String tmpName = snapshot.child("firstName").getValue(String.class) + " "
                            + snapshot.child("lastName").getValue(String.class);

                    Name.setText(tmpName);
                    Role.setText(snapshot.child("role").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Name.setText("");
                    Role.setText(R.string.user);
                }
            });



            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WelcomePage.this, "Menu Pages Under Development", Toast.LENGTH_LONG).show();
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
            mAuth.signOut();
            finish();
        }

    }

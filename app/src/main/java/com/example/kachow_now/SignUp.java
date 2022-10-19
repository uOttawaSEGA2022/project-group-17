package com.example.kachow_now;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance();
    }

    public void createAccount(View view){
        String Username =  ((EditText)findViewById(R.id.SignupEmail)).getText().toString().trim();
        String Password =  ((EditText)findViewById(R.id.SignupPassword)).getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(Username,Password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    database = FirebaseDatabase.getInstance();
                    //TODO get all data feilds as veriables and apply them to our object, then push object into realtime DB
                    // also push to auth server (half done)
                    FirebaseUser usr = mAuth.getCurrentUser();
                    Intent intent = new Intent(SignUp.this.getApplicationContext(),WelcomePage.class);
                    startActivityForResult(intent,0);
                }
                else{
                    Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    Toast.makeText(SignUp.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
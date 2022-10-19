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

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void createAccount(View view){
        String Username =  ((EditText)findViewById(R.id.userName)).getText().toString().trim();
        String Password =  ((EditText)findViewById(R.id.password)).getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(Username,Password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("Added user");
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
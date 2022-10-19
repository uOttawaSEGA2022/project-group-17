package com.example.kachow_now;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText editTextUsername;
    EditText editTextPassword;
    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = ((EditText)findViewById(R.id.userName)) ;
        editTextPassword = ((EditText)findViewById(R.id.password));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }


    public void login(View view){
        String username =  ((EditText)findViewById(R.id.userName)).getText().toString().trim();
        String password =  ((EditText)findViewById(R.id.password)).getText().toString().trim();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Authentication Successful.",
                                    Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Authentication Failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void createAccount(View view){
        String username =  ((EditText)findViewById(R.id.userName)).getText().toString().trim();
        String password =  ((EditText)findViewById(R.id.password)).getText().toString().trim();
        System.out.println("formatted email and password");
        mAuth.createUserWithEmailAndPassword(editTextUsername.toString().trim().toLowerCase(Locale.ROOT),
                editTextPassword.toString().trim()).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usr = mAuth.getCurrentUser();
                    Intent intent = new Intent(getApplicationContext(),welcome.class);
                    startActivityForResult(intent,0);
                }
                else{
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
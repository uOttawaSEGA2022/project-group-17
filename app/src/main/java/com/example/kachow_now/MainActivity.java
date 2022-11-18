package com.example.kachow_now;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.singUpButton);

        editTextUsername = findViewById(R.id.userName);
        editTextPassword = findViewById(R.id.password);
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
        //TODO error checking here
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Unable to Login. Empty fields detected.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                ((EditText)findViewById(R.id.userName)).setText("");
                                ((EditText)findViewById(R.id.password)).setText("");

                                Toast.makeText(MainActivity.this, "Authentication Successful.",
                                        Toast.LENGTH_LONG).show();

                                DatabaseReference dB = FirebaseDatabase.getInstance().getReference("UID");

                                //if(dB.child(mAuth.getCurrentUser().getUid())
                                dB.child((mAuth.getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String role = snapshot.child("role").getValue(String.class);
                                        if (role.equalsIgnoreCase("admin")) {

                                            Toast.makeText(MainActivity.this, "Welcome administrator", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(MainActivity.this.getApplicationContext(), AdminPage.class);
                                            startActivity(intent);
                                        } else if (role.equalsIgnoreCase("cook") && snapshot.child("isBanned").getValue(boolean.class) != null) {
                                            boolean isBanned = Boolean.TRUE.equals(snapshot.child("isBanned").getValue(boolean.class));
                                            boolean isSuspended = Boolean.TRUE.equals(snapshot.child("isSuspended").getValue(boolean.class));
                                            Cook cook = new Cook();
                                            if (!isSuspended && !isBanned) {
                                                Intent intent = new Intent(MainActivity.this.getApplicationContext(), CookHomepage.class);
                                                startActivity(intent);
                                            } else if (isSuspended && !isBanned) {
                                                int daySus = snapshot.child("daySus").getValue(int.class);
                                                if (daySus <= Cook.getDate()) {
                                                    dB.child((mAuth.getCurrentUser()).getUid()).child("isSuspended").setValue(false);
                                                    Intent intent = new Intent(MainActivity.this.getApplicationContext(), CookHomepage.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(MainActivity.this, "You are suspended for " + (daySus - Cook.getDate()) + " more days.", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(MainActivity.this.getApplicationContext(), CookHomepage.class);
                                                    startActivity(intent);
                                                }
                                            } else {
                                                Toast.makeText(MainActivity.this, "KaChow you're banned! Katch you later!", Toast.LENGTH_LONG).show();
                                            }


                                        } else {
                                            Intent intent = new Intent(MainActivity.this.getApplicationContext(), ClientHomepage.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(MainActivity.this, "Failed to access Database error: "
                                                + error, Toast.LENGTH_LONG).show();
                                    }
                                });


                            }
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to login. Incorrect username or password.", Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }
    public void moveToRegister(View view){
        Intent intent = new Intent(MainActivity.this.getApplicationContext(),SignUp.class);
        startActivity(intent);
    }

}
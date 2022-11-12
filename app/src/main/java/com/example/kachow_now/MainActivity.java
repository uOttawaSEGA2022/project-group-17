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
        //TODO error checking here
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's informatsion
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
                                    boolean isBanned = snapshot.child("isBanned").getValue(boolean.class);
                                    boolean isSuspended = snapshot.child("isSuspended").getValue(boolean.class);
                                    if (role.equalsIgnoreCase("admin")) {

                                        Toast.makeText(MainActivity.this, "Welcome administrator", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this.getApplicationContext(), AdminPage.class);
                                        startActivity(intent);

                                    } else if (role.equalsIgnoreCase("cook") && !isBanned) {
                                        Cook cook = new Cook();
                                        if (isSuspended){
                                            int daySus = snapshot.child("daySus").getValue(int.class);
                                            if (daySus == cook.getDate()){
                                                DatabaseReference c = FirebaseDatabase.getInstance().getReference("UID");
                                                c.child(cook.getUID()).child("isSuspended").setValue(false);
                                                Intent intent = new Intent(MainActivity.this.getApplicationContext(), WelcomePage.class);
                                                startActivity(intent);
                                            }
                                        }

                                    } else {
                                        Intent intent = new Intent(MainActivity.this.getApplicationContext(), ClientHomepage.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(MainActivity.this,"Failed to access Database error: "
                                            + error.toString(),Toast.LENGTH_LONG).show();
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication Failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed to login", Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void moveToRegister(View view){
        Intent intent = new Intent(MainActivity.this.getApplicationContext(),SignUp.class);
        startActivity(intent);
    }

}
package com.example.kachow_now;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    DatabaseReference databaseProducts;
    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

    }
    private void onClickSignup(){

    }
    private void login(EditText username, EditText password){


    }
}
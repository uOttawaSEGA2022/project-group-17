package com.example.kachow_now;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance().getReference("UID");


        Spinner spin = (Spinner) findViewById(R.id.SignupRole);
        EditText AccountOrCardNumber = (EditText) findViewById(R.id.AccountOrCardNumber);
        EditText CCVorInstitution = (EditText) findViewById(R.id.CCVorInstitution);
        TextView BranchNumberorExpiry = (TextView) findViewById(R.id.BranchNumberorExpiry);
        EditText MonthOrBranchNumber = (EditText) findViewById(R.id.MonthOrBranchNumber);
        EditText Day = (EditText) findViewById(R.id.Day);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String textFromSpinner =String.valueOf(spin.getSelectedItem()).trim().toLowerCase();
                if (textFromSpinner.equals("cook")){
                    AccountOrCardNumber.setVisibility(View.VISIBLE);
                    CCVorInstitution.setVisibility(View.VISIBLE);
                    BranchNumberorExpiry.setVisibility(View.GONE);
                    MonthOrBranchNumber.setVisibility(View.VISIBLE);
                    Day.setVisibility(View.GONE);
                    AccountOrCardNumber.setHint("Account Number");
                    MonthOrBranchNumber.setHint("Branch Number");
                    CCVorInstitution.setHint("Institution Number");

                } else if (textFromSpinner.equals("client")){
                    AccountOrCardNumber.setVisibility(View.VISIBLE);
                    CCVorInstitution.setVisibility(View.VISIBLE);
                    BranchNumberorExpiry.setVisibility(View.VISIBLE);
                    MonthOrBranchNumber.setVisibility(View.VISIBLE);
                    Day.setVisibility(View.VISIBLE);
                    AccountOrCardNumber.setHint("Card Number");
                    MonthOrBranchNumber.setHint("Month");
                    CCVorInstitution.setHint("CCV");
                    Day.setHint("Day");
                }
                else{
                    AccountOrCardNumber.setVisibility(View.INVISIBLE);
                    CCVorInstitution.setVisibility(View.INVISIBLE);
                    BranchNumberorExpiry.setVisibility(View.INVISIBLE);
                    MonthOrBranchNumber.setVisibility(View.INVISIBLE);
                    Day.setVisibility(View.INVISIBLE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void createAccount(View view){
        String Type = String.valueOf(((Spinner) findViewById(R.id.SignupRole)).getSelectedItem()).trim().toLowerCase();
        String FirstName= ((EditText) findViewById(R.id.SignupFirstName)).getText().toString().trim();
        String Surname = ((EditText) findViewById(R.id.SignupLastName)).getText().toString().trim();
        String Phone = ((EditText) findViewById(R.id.SignupPhone)).getText().toString().trim();
        String Email =  ((EditText)findViewById(R.id.SignupEmail)).getText().toString().trim();
        String Password =  ((EditText)findViewById(R.id.SignupPassword)).getText().toString().trim();
        String AccountOrCardNumber =  ((EditText)findViewById(R.id.AccountOrCardNumber)).getText().toString().trim();
        String BranchOrExpiry =  ((EditText)findViewById(R.id.CCVorInstitution)).getText().toString().trim();
        String CCVOrInstitutionNumber =  ((EditText)findViewById(R.id.MonthOrBranchNumber)).getText().toString().trim();
        if(Type.equals("client")) {
            String Day = ((EditText) findViewById(R.id.Day)).getText().toString().trim();
        }else{
            String Day = null;
        }
        //int[]
        //Client myClient = new Client()
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    if(Type.equals("client")){

                    }

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
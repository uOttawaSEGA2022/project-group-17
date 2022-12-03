package com.example.kachow_now;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CookProfileClientSide extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile_client_side);

        String cUID = getIntent().getExtras().getString("UID");
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("UID").child(cUID);

        /*Cook tmpCook = new Cook();
        dB.child(cUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot cookSnapshot) {
                Cook tmpCook = new Cook();
                tmpCook.setUID(cookSnapshot.child("uid").getValue(String.class));
                tmpCook.setAddress(cookSnapshot.child("address").getValue(String.class));

                tmpCook.setBank(cookSnapshot.child("bank").getValue(new GenericTypeIndicator<ArrayList<Long>>() {
                }));

                tmpCook.setDaySus(cookSnapshot.child("daySus").getValue(Integer.class));
                tmpCook.setDescription(cookSnapshot.child("description").getValue(String.class));
                tmpCook.setEmail(cookSnapshot.child("email").getValue(String.class));
                tmpCook.setFirstName(cookSnapshot.child("firstName").getValue(String.class));
                tmpCook.setIsBanned(cookSnapshot.child("isBanned").getValue(boolean.class));
                tmpCook.setIsSuspended(cookSnapshot.child("isSuspended").getValue(boolean.class));
                tmpCook.setLastName(cookSnapshot.child("lastName").getValue(String.class));
                tmpCook.setPassword(cookSnapshot.child("password").getValue(String.class));
                tmpCook.setPhoneNumber(cookSnapshot.child("phoneNumber").getValue(long.class));
                tmpCook.setPostalCode(cookSnapshot.child("postalCode").getValue(String.class));
                tmpCook.setRating(cookSnapshot.child("rating").getValue(Integer.class));
                tmpCook.setRole(cookSnapshot.child("role").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        //TODO DO THIS AFTER DOING THE COOK SIDE FIRST

        Button reportButton = (Button) findViewById(R.id.ReportCookProfileClient);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), submit_report.class);
                intent.putExtra("UID", cUID);
                startActivity(intent);
            }
        });

        Button rateButton = (Button)findViewById(R.id.ratingPage);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CookProfileClientSide.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.rate_cook_dialog, null);

                EditText ratingInput = dialogView.findViewById(R.id.ratingCook);
                Button rateCook = dialogView.findViewById(R.id.rateCook);
                rateCook.setText("Rate Meal");

                dialogBuilder.setView(dialogView);
                final AlertDialog b = dialogBuilder.create();
                b.show();

                rateCook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String c = ratingInput.getText().toString();
                        try {
                            double userRating = Double.parseDouble(c);

                            if ((userRating <= 0) || (userRating > 5)){
                                throw new IllegalArgumentException();
                            }

                            dB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot s) {
                                    Double dbRating = s.child("rating").getValue(Double.class);

                                    if (dbRating == 0){
                                        dB.child("rating").removeValue();
                                        dB.child("rating").setValue(userRating);
                                    }
                                    else{
                                        dB.child("rating").removeValue();
                                        dB.child("rating").setValue((userRating + dbRating) / 2);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            b.dismiss();
                        }
                        catch(NullPointerException e){
                            Toast.makeText(CookProfileClientSide.this,
                                    "Please enter a rating", Toast.LENGTH_LONG).show();
                        }
                        catch(NumberFormatException e){
                            Toast.makeText(CookProfileClientSide.this,
                                    "Please enter a number", Toast.LENGTH_LONG).show();
                        }
                        catch (IllegalArgumentException e){
                            Toast.makeText(CookProfileClientSide.this,
                                    "Please enter a number between 1 and 5", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }

}
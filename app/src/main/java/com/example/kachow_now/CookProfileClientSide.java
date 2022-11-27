package com.example.kachow_now;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CookProfileClientSide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile_client_side);

        String cUID = getIntent().getExtras().getString("UID");


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
                Intent intent = new Intent(v.getContext(), Cook.class);
                startActivity(intent);
            }
        });

    }
}
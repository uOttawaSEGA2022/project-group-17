package com.example.kachow_now;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClientHomepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private DatabaseReference rateMealDB;
    private ArrayList<Cook> chefs;
    private ListView listViewChefs;
    private RecyclerView rv;
    private RecyclerView mealTypeRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clienthomepage);
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("UID");

        //TODO if previous order has been accepted and ordered by this client, do the rate the
        // meal menu, This is just an idea though
        rateMealDB = FirebaseDatabase.getInstance().getReference("MEALS");
        // scroll through all info and then give them the popup


        Button logOutButton = findViewById(R.id.ClientHomePageLogout);
        chefs = new ArrayList<Cook>();

        rv = findViewById(R.id.chefRecyclerView);
        rv.setAdapter(new CookList(chefs));

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //  mealTypeRV.setLayoutManager();
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



    @Override
    protected void onStart() {
        super.onStart();
        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefs.clear();
                for (DataSnapshot cookSnapshot : snapshot.getChildren()) {
                    String r = cookSnapshot.child("role").getValue(String.class);
                    if (r.equalsIgnoreCase("cook")) {
                        Cook tmpCook = new Cook();
                        tmpCook.setUID(cookSnapshot.child("uid").getValue(String.class));
                        tmpCook.setAddress(cookSnapshot.child("address").getValue(String.class));

                        tmpCook.setBank(cookSnapshot.child("bank").getValue(new GenericTypeIndicator<ArrayList<Long>>() {
                        }));

                        try {
                            tmpCook.setDaySus(cookSnapshot.child("daySus").getValue(Integer.class));

                        } catch (NullPointerException e) {
                            // nothing because this is not a needed value
                        }
                        try {
                            tmpCook.setIsBanned(cookSnapshot.child("isBanned").getValue(boolean.class));
                        } catch (NullPointerException e) {
                            tmpCook.setIsBanned(false);
                        }
                        try {
                            tmpCook.setIsSuspended(cookSnapshot.child("isSuspended").getValue(boolean.class));
                        } catch (NullPointerException e) {
                            tmpCook.setIsSuspended(false); // assume people are angels and they are not suspended or banned
                        }


                        tmpCook.setDescription(cookSnapshot.child("description").getValue(String.class));
                        tmpCook.setEmail(cookSnapshot.child("email").getValue(String.class));
                        tmpCook.setFirstName(cookSnapshot.child("firstName").getValue(String.class));
                        tmpCook.setLastName(cookSnapshot.child("lastName").getValue(String.class));
                        tmpCook.setPassword(cookSnapshot.child("password").getValue(String.class));
                        tmpCook.setPhoneNumber(cookSnapshot.child("phoneNumber").getValue(long.class));
                        tmpCook.setPostalCode(cookSnapshot.child("postalCode").getValue(String.class));
                        tmpCook.setRating(cookSnapshot.child("rating").getValue(Integer.class));
                        tmpCook.setRole(cookSnapshot.child("role").getValue(String.class));
                        chefs.add(tmpCook);
                    }
                }

                rv.swapAdapter(new CookList(chefs), false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

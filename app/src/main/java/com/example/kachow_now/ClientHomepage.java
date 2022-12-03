package com.example.kachow_now;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

        rateMealDB = FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                .child(mAuth.getCurrentUser().getUid());

        rateMealDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.child("accepted").getValue(boolean.class) == null) {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClientHomepage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.rate_cook_dialog, null);

                        TextView labelRating = dialogView.findViewById(R.id.labelRating1);
                        labelRating.setText("Rate your last meal");
                        EditText ratingInput = dialogView.findViewById(R.id.ratingCook);
                        ratingInput.setHint("Enter a rating from 1 to 5");
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

                                    if ((userRating <= 0) || (userRating > 5)) {
                                        throw new IllegalArgumentException();
                                    }

                                    DatabaseReference mealDb = FirebaseDatabase.getInstance().
                                            getReference("MEALS")
                                            .child(Objects.requireNonNull(s.child("cookId") //child(cookID)
                                                    .getValue(String.class)))
                                            .child(Objects.requireNonNull(s.child("orders")//child(mealName)
                                                    .child("0").getValue(String.class)));
                                    mealDb.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Double dbRating = snapshot.child("rating").getValue(Double.class);
                                            mealDb.child("rating").removeValue();
                                            if (dbRating == 0) {
                                                mealDb.child("rating").setValue(userRating);
                                            } else {
                                                mealDb.child("rating").setValue((userRating + dbRating) / 2);
                                            }
                                            rateMealDB.child(s.getKey()).removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                    b.dismiss();
                                } catch (NullPointerException e) {
                                    Toast.makeText(ClientHomepage.this,
                                            "Please enter a rating", Toast.LENGTH_LONG).show();
                                } catch (NumberFormatException e) {
                                    Toast.makeText(ClientHomepage.this,
                                            "Please enter a number", Toast.LENGTH_LONG).show();
                                } catch (IllegalArgumentException e) {
                                    Toast.makeText(ClientHomepage.this,
                                            "Please enter a number between 1 and 5", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // scroll through all info and then give them the popup

        SearchView s = findViewById(R.id.Search);
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), ClientSearch.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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

    public void logOut(View view) {
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

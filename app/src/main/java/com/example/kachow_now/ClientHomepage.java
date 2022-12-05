package com.example.kachow_now;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClientHomepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private DatabaseReference rateMealDB;
    private ArrayList<Cook> chefs;
    private RecyclerView rv;
    private LinearLayout statusOrders;
    private ListView myOrders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clienthomepage);
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("UID");

        rateMealDB = FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                .child(mAuth.getCurrentUser().getUid());


        statusOrders = findViewById(R.id.statusOrders);
        myOrders = findViewById(R.id.myOrders);
        showOrderStatus();

        //TODO change below code to scan for tag as well as scanning for ratings

        //TODO CHECK ACCEPTED BEFORE WE DELETE CLIENTLOG ANd SEND A NOTIFICATION
        //TODO CHECK ACCEPTED == NULL and send notification
        //TODO CHECK NEW TAG AND IF TAG EXISTS, SEND NOTIF REJECTED

        rateMealDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot s : snapshot.getChildren()) {

                    if (s.child("rejected").getValue(Boolean.class) != null) {
                        sendNotif("Sorry about this",
                                "Your order unfortunately can't be fulfilled");

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(mAuth.getCurrentUser().getUid())
                                .child(s.getKey())
                                .removeValue();
                    }


                    if (s.child("accepted").getValue(boolean.class) == null) {

                        sendNotif("Information about your latest order",
                                "Your order is complete please proceed to pick it up");

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(mAuth.getCurrentUser().getUid())
                                .child(s.getKey())
                                .removeValue();

                        //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClientHomepage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.rate_cook_dialog, null);

                        TextView labelRating = dialogView.findViewById(R.id.labelRating1);
                        labelRating.setText("Rate your last meal");
                        EditText ratingInput = dialogView.findViewById(R.id.ratingCook);
                        ratingInput.setHint("Enter a rating from 1 to 5");
                        Button rateCook = dialogView.findViewById(R.id.rateCook);
                        rateCook.setText("Rate Meal");

                        androidx.appcompat.app.AlertDialog bc = new MaterialAlertDialogBuilder(ClientHomepage.this)
                                .setView(dialogView)
                                .show();

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


                                    bc.dismiss();
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
                    } else if (s.child("accepted").getValue(boolean.class) == true) {
                        sendNotif("Information about your latest order",
                                "Your order has been accepted");
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
                    if (r.equalsIgnoreCase("cook") &&
                            !cookSnapshot.child("isSuspended").getValue(boolean.class) &&
                            !cookSnapshot.child("isBanned").getValue(boolean.class)) {
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

        showOrderStatus();

    }

    private void showOrderStatus() {

        rateMealDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    statusOrders.setVisibility(View.VISIBLE);
                    ArrayList<String> finalSentences = new ArrayList<String>();
                    rateMealDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            finalSentences.clear();
                            for (DataSnapshot o : snapshot.getChildren()) {
                                DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

                                Date res = new Date(Long.parseLong(Objects.requireNonNull(o.getKey())));

                                String date = dateFormat.format(res);

                                final Boolean accepted = o.child("accepted").getValue(Boolean.class);
                                String cookUID = o.child("cookId").getValue(String.class);


                                DatabaseReference uidDB = FirebaseDatabase.getInstance().getReference("UID");
                                ArrayList<String> cookNames = new ArrayList<String>();
                                uidDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot m : snapshot.getChildren()) {
                                            try {
                                                if (m.getKey().equals(cookUID)) {

                                                    String firstName = m.child("firstName").getValue(String.class);
                                                    String lastName = m.child("lastName").getValue(String.class);
                                                    String fullName = (firstName + " " + lastName);

                                                    cookNames.add(fullName);

                                                }
                                            } catch (NullPointerException nullPointerException) {
                                                System.out.println("COOK DOES NOT EXIST");
                                            }

                                        }
                                        String finishedString;
                                        for (String fullName : cookNames) {
                                            if (accepted == null) { //TODO how about rejected
                                                finishedString = "The request from " + fullName + " made on " + date + " is now completed.";
                                            } else if (accepted) {
                                                finishedString = "The request from " + fullName + " made on " + date + " is now accepted.";
                                            } else {
                                                finishedString = "The request from " + fullName + " made on " + date + " is now pending.";
                                            }
                                            System.out.println("added: " + finishedString);
                                            finalSentences.add(finishedString);
                                        }

                                        ArrayAdapter<String> orderAdapter =
                                                new ArrayAdapter<String>(ClientHomepage.this, R.layout.layout_order_status_display, finalSentences);
                                        System.out.println("ORDERS: \n" + finalSentences);
                                        myOrders.setAdapter(orderAdapter);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    statusOrders.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendNotif(String title, String message) {

        Intent intent = new Intent(getApplicationContext(), ClientHomepage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);


        final String channelId = "DEFAULT_CHANNEL";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(getString(R.string.app_name)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);

        assert notificationManager != null;
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}

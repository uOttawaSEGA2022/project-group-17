package com.example.kachow_now;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class CookOrders extends AppCompatActivity {


    private static final String CHANNEL_ID = "system_default";
    private NotificationManagerCompat notificationManager;

    FirebaseAuth mAuth;
    DatabaseReference dB;
    List<Request> pendingRequests;
    List<Request> acceptedRequests;
    ListView listViewPending;
    ListView listViewAccepted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_orders);
        mAuth = FirebaseAuth.getInstance();


        dB = FirebaseDatabase.getInstance().getReference("ORDERS")
                .child(mAuth.getCurrentUser().getUid());
        // This page is accessed by the cook, so we have their ID

        listViewAccepted = findViewById(R.id.list_of_accepted);
        // This is what I meant when we were talking about it, Check out the layout for cook orders
        // This is not the one your working on rn, I just made this for later

        listViewPending = findViewById(R.id.list_of_requests);


        pendingRequests = new ArrayList<Request>();
        acceptedRequests = new ArrayList<Request>();

        listViewPending.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Request request = pendingRequests.get(i);
                showPendingRequestEntry(request.getCookId(), request.getClientId(),
                        request.getCurrentTime());
                return true;
            }
        });
        listViewAccepted.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Request request = acceptedRequests.get(i);
                showAcceptedRequestEntry(request.getCookId(), request.getClientId(),
                        request.getCurrentTime());
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        dB.child("pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pendingRequests.clear();

                for (DataSnapshot s : snapshot.getChildren()) {
                    Request tmp = new Request();
                    tmp.setCookId(s.child("cookID").getValue(String.class));
                    tmp.setClientId(s.child("clientID").getValue(String.class));
                    tmp.setAccepted(Boolean.TRUE.equals(s.child("accepted").getValue(boolean.class)));
                    tmp.setCurrentTime(s.child("currentTime").getValue(long.class));

                    tmp.setOrders(s.child("orders").getValue(
                            new GenericTypeIndicator<ArrayList<String>>() {
                            }));

                    pendingRequests.add(tmp);
                }
                RequestList pendingRequestListAdapter = new RequestList(CookOrders.this, pendingRequests);
                listViewPending.setAdapter(pendingRequestListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dB.child("accepted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                acceptedRequests.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Request tmp = new Request();
                    tmp.setCookId(s.child("cookID").getValue(String.class));
                    tmp.setClientId(s.child("clientID").getValue(String.class));
                    tmp.setAccepted(Boolean.TRUE.equals(s.child("accepted").getValue(boolean.class)));
                    tmp.setCurrentTime(s.child("currentTime").getValue(long.class));

                    tmp.setOrders(s.child("orders").getValue(
                            new GenericTypeIndicator<ArrayList<String>>() {
                            }));

                    acceptedRequests.add(tmp);
                }
                RequestList acceptedRequestListAdapter = new RequestList(CookOrders.this, acceptedRequests);
                listViewAccepted.setAdapter(acceptedRequestListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showPendingRequestEntry(final String cookId, String clientId, long currentTime) {

        //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.orderstate_dialog, null);
        //dialogBuilder.setView(dialogView);

        Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        Button rejectButton = dialogView.findViewById(R.id.rejectButton);

        acceptButton.setText("ACCEPT");
        rejectButton.setText("REJECT");

        //AlertDialog b = dialogBuilder.create();
        androidx.appcompat.app.AlertDialog bc = new MaterialAlertDialogBuilder(CookOrders.this)
                .setTitle("Accept or Reject request ")
                .setView(dialogView)
                .show();


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sendNotif("Your order has been Accepted",
                //        "Hi there, we have just received word your order is Accepted");

                dB.child("pending").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot s) {
                        Request tmp = new Request();
                        tmp.setCookId(s.child("cookId").getValue(String.class));
                        tmp.setClientId(s.child("clientId").getValue(String.class));
                        //tmp.setAccepted(Boolean.TRUE.equals(s.child("accepted").getValue(boolean.class)));
                        tmp.setCurrentTime(Long.parseLong(s.getKey()));

                        tmp.setOrders(s.child("orders").getValue(
                                new GenericTypeIndicator<ArrayList<String>>() {
                                }));

                        dB.child("accepted").child(String.valueOf(currentTime)).setValue(tmp);

                        String clientId = s.child("clientId").getValue(String.class);


                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(clientId)
                                .child(String.valueOf(currentTime))
                                .child("accepted")
                                .removeValue();

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(s.child("clientId").getValue(String.class))
                                .child(String.valueOf(currentTime))
                                .child("accepted")
                                .setValue(true);

                        dB.child("pending").child(String.valueOf(currentTime)).child("accepted").removeValue();
                        dB.child("accepted").child(String.valueOf(currentTime)).child("accepted").setValue(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dB.child("pending").child(String.valueOf(currentTime)).removeValue();
                Toast.makeText(CookOrders.this, "Accepted Request", Toast.LENGTH_LONG).show();
                bc.dismiss();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sendNotif("Your order has been Rejected",
                //        "Hi there, we have just received word your order is Rejected");

                dB.child("pending").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String clientId = snapshot.child("clientId").getValue(String.class);

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(clientId)
                                .child(String.valueOf(currentTime))
                                .child("rejected")
                                .setValue(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dB.child("pending").child(String.valueOf(currentTime)).removeValue();
                Toast.makeText(CookOrders.this, "Rejected Request!", Toast.LENGTH_LONG).show();
                bc.dismiss();
            }
        });

    }

    public void showAcceptedRequestEntry(final String cookId, String clientId, long currentTime) {

        //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.orderstate_dialog, null);
        //dialogBuilder.setView(dialogView);

        Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        Button rejectButton = dialogView.findViewById(R.id.rejectButton);

        acceptButton.setText("COMPLETE");
        rejectButton.setText("REJECT");
        androidx.appcompat.app.AlertDialog bc = new MaterialAlertDialogBuilder(CookOrders.this)
                .setTitle("Complete or Reject request ")
                .setView(dialogView)
                .show();
        //AlertDialog b = dialogBuilder.create();
        //b.show();


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sendNotif("Your order has been Completed",
                //        "Hi there, we have just received word your order is completed");

                dB.child("accepted").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String clientId = snapshot
                                .child("clientId")
                                .getValue(String.class);

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(clientId)
                                .child(String.valueOf(currentTime))
                                .child("accepted")
                                .removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dB.child("accepted").child(String.valueOf(currentTime)).removeValue();
                Toast.makeText(CookOrders.this, "Completed Request", Toast.LENGTH_LONG).show();
                bc.dismiss();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO Change to not delete clientlog and instead add tag to client log, then
                // delete from orders

                //sendNotif("Your order has been Rejected",
                //        "Hi there, we have just received word your order is Rejected");

                dB.child("accepted").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String clientId = snapshot
                                .child("clientId")
                                .getValue(String.class);

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(clientId)
                                .child(String.valueOf(currentTime))
                                .child("rejected")
                                .setValue(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dB.child("accepted")
                        .child(String.valueOf(currentTime))
                        .removeValue();
                Toast.makeText(CookOrders.this, "Rejected Request!", Toast.LENGTH_LONG).show();
                bc.dismiss();
            }
        });

    }

    public void sendNotif(String title, String message) {

        Intent intent = new Intent(getApplicationContext(), CookOrders.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);


        String channelId = "DEFAULT_CHANNEL";
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
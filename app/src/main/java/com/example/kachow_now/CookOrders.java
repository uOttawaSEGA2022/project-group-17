package com.example.kachow_now;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class CookOrders extends AppCompatActivity {

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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.orderstate_dialog, null);
        dialogBuilder.setView(dialogView);

        Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        Button rejectButton = dialogView.findViewById(R.id.rejectButton);

        acceptButton.setText("ACCEPT");
        rejectButton.setText("REJECT");
        AlertDialog b = dialogBuilder.create();
        b.show();


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dB.child("pending").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot s) {

                        for (DataSnapshot l : s.getChildren()) {
                            System.out.println(l.getKey());
                        }

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
                b.dismiss();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dB.child("pending").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String clientId = snapshot.child("clientId").getValue(String.class);

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(clientId)
                                .child(String.valueOf(currentTime))
                                .removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dB.child("pending").child(String.valueOf(currentTime)).removeValue();
                Toast.makeText(CookOrders.this, "Rejected Request!", Toast.LENGTH_LONG).show();
                b.dismiss();
            }
        });

    }

    public void showAcceptedRequestEntry(final String cookId, String clientId, long currentTime) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.orderstate_dialog, null);
        dialogBuilder.setView(dialogView);

        Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        Button rejectButton = dialogView.findViewById(R.id.rejectButton);

        acceptButton.setText("COMPLETE");
        rejectButton.setText("REJECT");
        AlertDialog b = dialogBuilder.create();
        b.show();


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                b.dismiss();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dB.child("accepted").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String clientId = snapshot
                                .child("clientId")
                                .getValue(String.class);

                        FirebaseDatabase.getInstance().getReference("CLIENTLOG")
                                .child(clientId)
                                .child(String.valueOf(currentTime))
                                .removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dB.child("accepted")
                        .child(String.valueOf(currentTime))
                        .removeValue();
                Toast.makeText(CookOrders.this, "Rejected Request!", Toast.LENGTH_LONG).show();
                b.dismiss();
            }
        });

    }
}
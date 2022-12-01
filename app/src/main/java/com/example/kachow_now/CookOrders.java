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
                showRequestEntry(request.getCookId(), request.getClientId(),
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

    public void showRequestEntry(final String cookId, String clientId, long currentTime) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.orderstate_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        final Button rejectButton = dialogView.findViewById(R.id.rejectButton);

        final AlertDialog b = dialogBuilder.create();
        b.show();


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dB.child("pending").child(String.valueOf(currentTime)).child("accepted").setValue(true);
                dB.child("pending").child(String.valueOf(currentTime)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            Request tmp = new Request();
                            tmp.setCookId(s.child("cookID").getValue(String.class));
                            tmp.setClientId(s.child("clientID").getValue(String.class));
                            tmp.setAccepted(Boolean.TRUE.equals(s.child("accepted").getValue(boolean.class)));
                            tmp.setCurrentTime(Long.parseLong(snapshot.getKey()));

                            tmp.setOrders(s.child("orders").getValue(
                                    new GenericTypeIndicator<ArrayList<String>>() {
                                    }));

                            acceptedRequests.add(tmp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dB.child("pending").child(String.valueOf(currentTime)).removeValue();
                Toast.makeText(CookOrders.this, "Accepted Request",Toast.LENGTH_LONG).show();
                b.dismiss();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dB.child("pending").child(String.valueOf(currentTime)).removeValue();
                Toast.makeText( CookOrders.this,"Rejected Request!", Toast.LENGTH_LONG).show();
                b.dismiss();
            }
        });

    }
}
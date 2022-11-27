package com.example.kachow_now;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CookOrders extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference dB;
    List<Request> requests;
    ListView listViewRequests;

    private String cUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_orders);
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("ORDERS");
        listViewRequests = findViewById(R.id.list_of_requests);

        cUID = getIntent().getExtras().getString("UID");


        requests = new ArrayList<Request>();

        listViewRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Request request = requests.get(i);
                showRequestEntry(request.getCookId(), request.getClientId(),
                        request.getCurrentTime());
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        dB.child(cUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot s) {
                requests.clear();
                for (DataSnapshot snapshot : s.getChildren()) {
                    Request tmp = new Request();
                    tmp.setCookId(cUID);
                    tmp.setClientId(mAuth.getCurrentUser().getUid());
                    tmp.setState("pending");
                    tmp.setCurrentTime(snapshot.child("currentTime").getValue(String.class));

                    ArrayList<String> meals = new ArrayList<String>();

                    DataSnapshot pendingOrders = snapshot.child("meals");
                    for (DataSnapshot order : pendingOrders.getChildren()) {
                        meals.add(String.valueOf(order));
                    }
                    requests.add(tmp);
                }



            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showRequestEntry(final String cookId, String clientId, String currentTime) {

        //TODO like fix everything lol
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.orderstate_dialog,null);
        dialogBuilder.setView(dialogView);

        final Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        final Button rejectButton = dialogView.findViewById(R.id.rejectButton);

        final AlertDialog b = dialogBuilder.create();
        b.show();


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dB.child("state").setValue("accepted");
                b.dismiss();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dB.child("state").setValue("rejected");
                b.dismiss();
            }
        });

    }
}
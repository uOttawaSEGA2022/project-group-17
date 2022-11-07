package com.example.kachow_now;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference dB;
    List<Complaint> complaints;
    ListView listViewComplaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("LOG");
        Button adminLogoutButton = (Button) findViewById(R.id.adminLogoutButton);
        listViewComplaints = (ListView) findViewById(R.id.list_of_complaints);

        adminLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut(view);
            }
        });


    listViewComplaints.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Complaint complaint = complaints.get(i);
                showMealEntry(complaint.getMealReviewed().getName(), complaint.getComplaintee().getFirstName() + " " + complaint.getComplaintee().getLastName(),
                        complaint.getComplaintee(), complaint);
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for (DataSnapshot post: snapshot.getChildren()) {
                    Complaint complaint = post.getValue(Complaint.class);
                    complaints.add(complaint);
                }
                ComplaintList productsAdapter = new ComplaintList(AdminPage.this,complaints);
                listViewComplaints.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void logOut(View view){
        mAuth.signOut();
        finish();
    }

    public void showMealEntry(final String mealName, String cookName, Cook cook, Complaint c) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.punishment_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDismiss = (Button) dialogView.findViewById(R.id.dismissButton);
        final Button buttonPermaBan = (Button) dialogView.findViewById(R.id.permaBanButton);
        final Button buttonSuspension = (Button) dialogView.findViewById(R.id.suspendButton);

        dialogBuilder.setTitle("Complaint about " + cookName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dB.child(String.valueOf(c.getTime())).removeValue();
            }
        });

        buttonPermaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banCook(cook);
            }
        });

        buttonSuspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suspendCook(cook);
            }
        });

    }

    private void banCook(Cook cook) {
        DatabaseReference c = FirebaseDatabase.getInstance().getReference("UID");
        c.child(cook.getUID()).child("isBanned").setValue(true);
    }

    private void suspendCook(Cook cook) {
        DatabaseReference c = FirebaseDatabase.getInstance().getReference("UID");
        c.child(cook.getUID()).child("isSuspended").setValue(true);
        c.child(cook.getUID()).child("daySus").setValue(Cook.getDate());
    }
}
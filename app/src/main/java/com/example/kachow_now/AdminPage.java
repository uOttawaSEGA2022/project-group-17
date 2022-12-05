package com.example.kachow_now;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
        Button adminLogoutButton = findViewById(R.id.adminLogoutButton);
        listViewComplaints = findViewById(R.id.list_of_complaints);

        complaints = new ArrayList<Complaint>();

        adminLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*StorageReference storageRef = FirebaseStorage.getInstance().getReference();

// Create a reference to the file to delete
                StorageReference desertRef = storageRef.child("images");

// Delete the file
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Toast.makeText(AdminPage.this,"Passed",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Toast.makeText(AdminPage.this,"FAILED: "+exception,Toast.LENGTH_LONG).show();
                    }
                });*/
                logOut(view);
            }
        });


        listViewComplaints.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Complaint complaint = complaints.get(i);
                showComplaintEntry(complaint.getMealReviewed(), complaint.getComplaintee().getFirstName() + " " + complaint.getComplaintee().getLastName(),
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
                //complaints = snapshot.getValue(new GenericTypeIndicator<ArrayList<Complaint>>(){});
                for (DataSnapshot s : snapshot.getChildren()) {
                    Complaint tmp = new Complaint();
                    Cook tmpCook = new Cook();
                    DataSnapshot cookSnapshot = s.child("complaintee");
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

                    tmp.setComplaintee(tmpCook);
                    tmp.setDay(s.child("day").getValue(Integer.class));
                    tmp.setMealReviewed(s.child("mealReviewed").getValue(String.class));
                    tmp.setMonth(s.child("month").getValue(Integer.class));
                    tmp.setTextReview(s.child("textReview").getValue(String.class));
                    tmp.setTime(s.child("time").getValue(long.class));
                    tmp.setYear(s.child("year").getValue(Integer.class));

                    complaints.add(tmp);
                }
                ComplaintList productsAdapter = new ComplaintList(AdminPage.this, complaints);
                listViewComplaints.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void logOut(View view) {
        mAuth.signOut();
        finish();
    }

    public void showComplaintEntry(final String mealName, String cookName, Cook cook, Complaint c) {

        //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.punishment_dialog, null);
        //dialogBuilder.setView(dialogView);

        final Button buttonDismiss = dialogView.findViewById(R.id.toggleoffer);
        final Button buttonPermaBan = dialogView.findViewById(R.id.deletemeal);
        final Button buttonSuspension = dialogView.findViewById(R.id.rateCook);
        final EditText textDaySus = dialogView.findViewById(R.id.labelRating1);

        androidx.appcompat.app.AlertDialog bc = new MaterialAlertDialogBuilder(this)
                .setTitle("Complaint about " + cookName)
                .setView(dialogView)
                .show();

        //dialogBuilder.setTitle("Complaint about " + cookName);
        //final AlertDialog b = dialogBuilder.create();
        //b.show();


        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dB.child(String.valueOf(c.getTime())).removeValue();
                bc.dismiss();
            }
        });

        buttonPermaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banCook(cook);
                dB.child(String.valueOf(c.getTime())).removeValue();
                bc.dismiss();
            }
        });

        buttonSuspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int daySus = Integer.parseInt(textDaySus.getText().toString());
                suspendCook(cook, daySus);
                dB.child(String.valueOf(c.getTime())).removeValue();
                bc.dismiss();
            }
        });

    }

    public void banCook(Cook cook) {
        DatabaseReference c = FirebaseDatabase.getInstance().getReference("UID");
        c.child(cook.getUID()).child("isBanned").setValue(true);
    }

    public void suspendCook(Cook cook, int days) {
        DatabaseReference c = FirebaseDatabase.getInstance().getReference("UID");
        c.child(cook.getUID()).child("isSuspended").setValue(true);
        c.child(cook.getUID()).child("daySus").setValue((Cook.getDate() + days) % 365);
    }
}
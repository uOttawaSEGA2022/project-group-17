package com.example.kachow_now;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CookProfileClientSide extends AppCompatActivity {
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile_client_side);

        String cUID = getIntent().getExtras().getString("UID");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CookProfileClientSide.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate_cook_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView name = findViewById(R.id.ChefRating);
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("UID").child(cUID);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullName = snapshot.child("firstName").getValue(String.class) + " "
                        + snapshot.child("lastName").getValue(String.class);
                name.setText(fullName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        ImageView chefprofilepicture = findViewById(R.id.chefprofilepicture);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference mImageRef = storageReference.child("images/" + cUID + "/profilePhoto");
        final long FOUR_MEGABYTE = 1024 * 1024;
        try {
            Task<byte[]> im = mImageRef.getBytes(FOUR_MEGABYTE);
            im.addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (im.isSuccessful()) {
                        byte[] b = im.getResult();
                        Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                        chefprofilepicture.setImageBitmap(bm);
                    } else {
                        System.out.println("Not Successful");
                    }
                }
            });

        } catch (Exception ignored) {
        }
        
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
                //final Button buttonRate = dialogView.findViewById(R.id.rateButton);
                //final AlertDialog b = dialogBuilder.create();
                //b.show();

            }
        });

    }
}
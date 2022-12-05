package com.example.kachow_now;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CookHomepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private Toolbar tName;
    private TextView name, address, email, postalCode, phoneNumber;
    private ImageView profilePic;
    private Button menu, orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile);
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("UID").child(mAuth.getCurrentUser().getUid());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Button logOutButton = findViewById(R.id.CookLogout);

        tName = findViewById(R.id.CookMenuToolbar);
        name = findViewById(R.id.cookNameInput);
        email = findViewById(R.id.cookEmailInput);
        phoneNumber = findViewById(R.id.cookPhoneNumberInput);
        address = findViewById(R.id.cookAddressInput);
        postalCode = findViewById(R.id.cookPostalCodeInput);
        profilePic = findViewById(R.id.cookProfilePic);
        menu = findViewById(R.id.cookProfileMenuButton);
        orders = findViewById(R.id.cookProfileOrders);

        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isSuspended = Boolean.TRUE.equals(snapshot.child("isSuspended").getValue(boolean.class));
                if (isSuspended) {
                    menu.setVisibility(View.GONE);
                    orders.setVisibility(View.GONE);
                } else {
                    menu.setVisibility(View.VISIBLE);
                    orders.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        StorageReference mImageRef = storageReference.child("images/" +
                mAuth.getCurrentUser().getUid() + "/profilePhoto");
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut(view);
            }
        });



        final long FOUR_MEGABYTE = 1024 * 1024;
        try {
            Task<byte[]> im = mImageRef.getBytes(FOUR_MEGABYTE);
            im.addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (im.isSuccessful()) {
                        byte[] b = im.getResult();
                        Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                        profilePic.setImageBitmap(bm);
                    } else {
                        System.out.println("Not Successful");
                    }
                }
            });

        } catch (Exception ignored) {
        }


        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot c) {
                tName.setTitle(c.child("firstName").getValue(String.class) + " "
                        + c.child("lastName").getValue(String.class) + "'s Profile");
                String n = (c.child("firstName").getValue(String.class) + " "
                        + c.child("lastName").getValue(String.class));
                name.setText(n);
                email.setText(c.child("email").getValue(String.class));
                phoneNumber.setText(String.valueOf(c.child("phoneNumber").getValue(long.class)));
                address.setText(c.child("address").getValue(String.class));
                postalCode.setText(String.valueOf(c.child("rating").getValue(double.class)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), currentlyOffered.class);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CookOrders.class);
                startActivity(intent);
            }
        });

    }
    public void logOut(View view) {
        mAuth.signOut();
        finish();
    }



}
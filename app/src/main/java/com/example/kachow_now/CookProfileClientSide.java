package com.example.kachow_now;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CookProfileClientSide extends AppCompatActivity {
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile_client_side);

        String cUID = getIntent().getExtras().getString("UID");

        TextView name = findViewById(R.id.ChefName);
        TextView rating = findViewById(R.id.ChefRating);
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("UID").child(cUID);

        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullName = "Name: "+snapshot.child("firstName").getValue(String.class) + " "
                        + snapshot.child("lastName").getValue(String.class);
                name.setText(fullName);

                String Rating = "Rating: " + snapshot.child("rating").getValue(double.class);
                rating.setText(Rating);

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

                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.rate_cook_dialog, null);
                TextView label = dialogView.findViewById(R.id.labelRating1);

                label.setVisibility(View.GONE);

                EditText cookRating = dialogView.findViewById(R.id.ratingCook);
                Button submit = dialogView.findViewById(R.id.rateCook);

                androidx.appcompat.app.AlertDialog bc = new MaterialAlertDialogBuilder(CookProfileClientSide.this)
                        .setTitle("Please rate the following cook")
                        .setView(dialogView)
                        .show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot s) {
                                double dbRating;
                                try {
                                    dbRating = s.child("rating").getValue(double.class);
                                } catch (NullPointerException e) {
                                    dbRating = 0.0;
                                }


                                String c = cookRating.getText().toString();
                                try {
                                    double userRating = Double.parseDouble(c);

                                    if ((userRating <= 0) || (userRating > 5)) {
                                        throw new IllegalArgumentException();
                                    }

                                    if (dbRating != 0.0) {
                                        userDB.child("rating").setValue((dbRating + userRating) / 2);
                                    } else {
                                        userDB.child("rating").setValue(userRating);
                                    }


                                    bc.dismiss();
                                } catch (NullPointerException e) {
                                    Toast.makeText(CookProfileClientSide.this,
                                            "Please enter a rating", Toast.LENGTH_LONG).show();
                                } catch (NumberFormatException e) {
                                    Toast.makeText(CookProfileClientSide.this,
                                            "Please enter a number", Toast.LENGTH_LONG).show();
                                } catch (IllegalArgumentException e) {
                                    Toast.makeText(CookProfileClientSide.this,
                                            "Please enter a number between 1 and 5", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });

    }
}
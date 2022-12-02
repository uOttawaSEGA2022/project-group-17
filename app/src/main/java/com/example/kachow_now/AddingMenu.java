package com.example.kachow_now;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddingMenu extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 22;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView imageView;
    private Uri filePath;
    private Button uploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_menu);

        uploadBtn = (Button) findViewById(R.id.UploadImageButton);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("UID");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //.child("images/"+mAuth.getCurrentUser().getUid());

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
    }

    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] fileInBytes = baos.toByteArray();
                imageView.setImageBitmap(bitmap);
                uploadImage(fileInBytes);
            } catch (IOException e) {
                // Catch the exception
            }
        }
    }

    private void uploadImage(byte[] fileInBytes) {
        if (filePath != null) {
            // Code for showing progressDialog while uploading


            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

            // adding listeners on upload
            // or failure of image
            ref.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    Toast.makeText(AddingMenu.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    Toast.makeText(AddingMenu.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
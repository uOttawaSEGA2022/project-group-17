package com.example.kachow_now;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class add_meal extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 22;
    private EditText name, type, price, cuisine, allergens, calories, ingredients, description;
    private Button upload, add;
    private Uri filePath;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_new_item);

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS").child(mAuth.getCurrentUser().getUid());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        name = (EditText) findViewById(R.id.ItemName);
        type = (EditText) findViewById(R.id.MealType);
        price = (EditText) findViewById(R.id.ItemPrice);
        cuisine = (EditText) findViewById(R.id.CusineType);
        allergens = (EditText) findViewById(R.id.ItemAllergens);
        calories = (EditText) findViewById(R.id.Calories);
        ingredients = (EditText) findViewById(R.id.Ingredients);
        description = (EditText) findViewById(R.id.ItemDescription);

        upload = (Button) findViewById(R.id.UploadItemPicture);
        add = (Button) findViewById(R.id.AddNewItemButton);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMealToDatabase();
                uploadImage();
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Catch the exception
            }
        }
    }

    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    private void SendMealToDatabase() {

        String mealName = name.getText().toString().trim();
        String mealDesc = description.getText().toString().trim();
        String mealType = type.getText().toString().trim();
        double mealPrice = Double.parseDouble(price.getText().toString().trim());

        //TODO finish meal to database


        //Meal m = new Meal(mealName,mealDesc,mealType,mealPrice,)
        //dB.child(name.getText().toString()).setValue(m);

    }

    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + (mAuth.getCurrentUser()).getUid() + "/" + name.getText().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog

                    //Toast.makeText(add_meal.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded

                    Toast.makeText(add_meal.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

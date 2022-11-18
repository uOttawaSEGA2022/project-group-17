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
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class add_meal extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 22;
    private EditText name, type, price, cuisine, allergens, calories, ingredients, description, servingSize;
    private Button upload, add, addAllergen, addIngredients;
    private Uri filePath;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ArrayList<String> ing;
    private ArrayList<String> all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_menu);

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS").child(mAuth.getCurrentUser().getUid());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        all = new ArrayList<>();
        ing = new ArrayList<>();

        name = findViewById(R.id.ItemName);
        type = findViewById(R.id.MealType);
        price = findViewById(R.id.Price);
        cuisine = findViewById(R.id.CusineType);
        allergens = findViewById(R.id.Allergens);
        calories = findViewById(R.id.Calories);
        ingredients = findViewById(R.id.IngredientsList);
        description = findViewById(R.id.Description);
        servingSize = findViewById(R.id.ServingSize);

        addIngredients = findViewById(R.id.addIngredients);
        addAllergen = findViewById(R.id.addAllergens);

        add = findViewById(R.id.AddNewItemButton);
        upload = findViewById(R.id.UploadImageButton);

        addIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ing.add(ingredients.getText().toString().trim());
                ingredients.setText("");
            }
        });

        addAllergen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.add(allergens.getText().toString().trim());
                allergens.setText("");
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || type.getText().toString().isEmpty() || price.getText().toString().isEmpty() || cuisine.getText().toString().isEmpty() ||
                        allergens.getText().toString().isEmpty() || calories.getText().toString().isEmpty() ||
                        ingredients.getText().toString().isEmpty() || description.getText().toString().isEmpty() || servingSize.getText().toString().isEmpty()) {
                    Toast.makeText(add_meal.this, "Could not add meal. Please fill all boxes.", Toast.LENGTH_LONG).show();
                }else{
                    if (filePath != null){
                        Toast.makeText(add_meal.this, "Added meal.", Toast.LENGTH_LONG).show();
                        SendMealToDatabase();
                        uploadImage();
                        finish();
                    } else{
                        Toast.makeText(add_meal.this, "Please upload a picture of the meal", Toast.LENGTH_LONG).show();
                    }

                }
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
        String mealPriceString = price.getText().toString().trim();
        double mealPrice = Double.parseDouble(mealPriceString);
        String mealCuisine = cuisine.getText().toString().trim();
        String mealCaloriesString = calories.getText().toString().trim();
        double mealCalories = Double.parseDouble(mealCaloriesString);
        String mealServingSizeString = servingSize.getText().toString().trim();
        double mealServingSize = Double.parseDouble(mealServingSizeString);



        Meal m = new Meal(mealName, mealDesc, mealType, mealPrice, mealCuisine, ing, all,
                mealServingSize, mealCalories);
        dB.child(name.getText().toString()).setValue(m);

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

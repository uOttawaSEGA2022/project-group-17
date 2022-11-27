package com.example.kachow_now;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OfferedMealsClientSide extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String cUID;
    private boolean exists;

    List<Meal> meals;
    ListView listViewMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_offered_meals_client_side);


        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS");

        cUID = getIntent().getExtras().getString("UID");

        exists = false;

        ImageButton chefIcon = (ImageButton) findViewById(R.id.cheficon);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference mImageRef = storageReference.child("images/" + cUID + "/profilePhoto");

        final long FOUR_MEGABYTE = 4096 * 4096;
        try {
            Task<byte[]> im = mImageRef.getBytes(FOUR_MEGABYTE);
            im.addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (im.isSuccessful()) {
                        byte[] b = im.getResult();
                        Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                        chefIcon.setImageBitmap(bm);
                    } else {
                        System.out.println("Not Successful");
                    }
                }
            });

        } catch (IndexOutOfBoundsException ignored) {
        }

        chefIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CookProfileClientSide.class);
                intent.putExtra("UID", cUID);
                startActivity(intent);
            }
        });

        listViewMeals = (ListView) findViewById(R.id.list_of_offered_meals);

        meals = new ArrayList<Meal>();


        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMealEntry(meals.get(i).getName());
                return true;
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("RAN ON START");
        dB.child(cUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(String.class) != null) {
                    exists = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (exists) {
            dB.child(cUID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    meals.clear();
                    for (DataSnapshot s : snapshot.getChildren()) {
                        for (DataSnapshot l : s.getChildren()) {
                            System.out.println(l.getKey());
                        }
                        Meal tmp = new Meal();
                        DataSnapshot mealSnapShot = s.child("meals");

                        ArrayList<String> ingredients = new ArrayList<String>();

                        DataSnapshot ingredientsSnapshot = mealSnapShot.child("ingredients");
                        for (DataSnapshot ingredient : ingredientsSnapshot.getChildren()) {
                            ingredients.add(String.valueOf(ingredient));
                        }

                        ArrayList<String> allergens = new ArrayList<String>();

                        DataSnapshot allergensSnapshot = mealSnapShot.child("allergens");
                        for (DataSnapshot allergen : allergensSnapshot.getChildren()) {
                            allergens.add(String.valueOf(allergen));
                        }

                        tmp.setName(mealSnapShot.child("name").getValue(String.class));
                        tmp.setDescription(mealSnapShot.child("description").getValue(String.class));
                        tmp.setMealType(mealSnapShot.child("mealType").getValue(String.class));
                        tmp.setPrice(mealSnapShot.child("price").getValue(double.class));
                        tmp.setCuisine(mealSnapShot.child("cuisine").getValue(String.class));
                        tmp.setIngredients(ingredients);
                        tmp.setAllergens(allergens);
                        tmp.setServingSize(mealSnapShot.child("servingSize").getValue(double.class));
                        tmp.setCalories(mealSnapShot.child("calories").getValue(double.class));
                        meals.add(tmp);
                    }
                    MealListClient mealsAdapter = new MealListClient(OfferedMealsClientSide.this, meals);
                    listViewMeals.setAdapter(mealsAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void showMealEntry (String mealName){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OfferedMealsClientSide.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_meal_list, null);
        dialogBuilder.setView(dialogView);


        dialogBuilder.setTitle(mealName);
        final AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
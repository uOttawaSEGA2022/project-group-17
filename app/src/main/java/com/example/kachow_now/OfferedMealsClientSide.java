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

    List<Meal> meals;
    ListView listViewMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_offered_meals_client_side);
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("UID");
        String cUID = getIntent().getExtras().getString("UID");

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

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("UID");
        listViewMeals = (ListView) findViewById(R.id.list_of_offered_meals);

        meals = new ArrayList<Meal>();
        Bundle bundle = getIntent().getExtras();


        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = meals.get(i);
                showMealEntry(meal.getName());
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
                meals.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.child("role").getValue(String.class).equals("cook")){
                        Meal tmp = new Meal();
                        DataSnapshot mealSnapShot = s.child("meals");

                        ArrayList<String> ingredients = new ArrayList<String>();

                        DataSnapshot ingredientsSnapshot = mealSnapShot.child("Ingredients");
                        for (DataSnapshot ingredient : ingredientsSnapshot.getChildren()){
                            ingredients.add(String.valueOf(ingredient));
                        }

                        ArrayList<String> allergens = new ArrayList<String>();

                        DataSnapshot allergensSnapshot = mealSnapShot.child("Allergens");
                        for (DataSnapshot allergen : allergensSnapshot.getChildren()){
                            allergens.add(String.valueOf(allergen));
                        }

                        tmp.setName(mealSnapShot.child("Name").getValue(String.class));
                        tmp.setDescription(mealSnapShot.child("Description").getValue(String.class));
                        tmp.setMealType(mealSnapShot.child("MealType").getValue(String.class));
                        tmp.setPrice(mealSnapShot.child("Price").getValue(double.class));
                        tmp.setCuisine(mealSnapShot.child("Cuisine").getValue(String.class));
                        tmp.setIngredients(ingredients);
                        tmp.setAllergens(allergens);
                        tmp.setServingSize(mealSnapShot.child("ServingSize").getValue(double.class));
                        tmp.setCalories(mealSnapShot.child("Calories").getValue(double.class));
                        meals.add(tmp);
                    }

                }
                MealListClient productsAdapter = new MealListClient(OfferedMealsClientSide.this, meals);
                listViewMeals.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
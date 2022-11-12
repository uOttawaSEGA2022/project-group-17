package com.example.kachow_now;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OfferedMealsClientSide extends AppCompatActivity {


    FirebaseAuth mAuth;
    DatabaseReference dB;
    List<Meal> meals;
    ListView listViewMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_offered_meals_client_side);

        ImageButton chefIcon = (ImageButton) findViewById(R.id.cheficon);

        chefIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_cook_profile_client_side);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("Meals");
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
 /*   @Override
    protected void onStart() {
        super.onStart();
        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Meal tmp = new Meal();
                    Cook tmpCook = new Cook();
                    DataSnapshot mealSnapShot = s.child("Meals");

                    ArrayList<String> ingredients = new ArrayList<String>();
                    for (String s1 : mealSnapShot.getValue(String.class)){
                        ingredients.add(s1);
                    }

                    tmp.setName(mealSnapShot.child("Name").getValue(String.class));
                    tmp.setDescription(mealSnapShot.child("Description").getValue(String.class));
                    tmp.setMealType(mealSnapShot.child("MealType").getValue(String.class));
                    tmp.setPrice(mealSnapShot.child("Price").getValue(double.class));
                    tmp.setCuisine(mealSnapShot.child("Cuisine").getValue(String.class));
                    tmp.setIngredients(mealSnapShot.child("Ingredients").getValue(ArrayList<String>.class));
                    tmp.setAllergens(mealSnapShot.child("Allergens").getValue(ArrayList<String>.class));
                    tmp.setServingSize(mealSnapShot.child("ServingSize").getValue(double.class));
                    tmp.setCalories(mealSnapShot.child("Calories").getValue(double.class));
                    meals.add(tmp);
                }
                MealList productsAdapter = new MealList(OfferedMealsClientSide.this, meals);
                listViewMeals.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    } */
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
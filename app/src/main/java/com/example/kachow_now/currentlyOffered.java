package com.example.kachow_now;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class currentlyOffered extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private List<Meal> meals;
    private ListView listViewMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu);

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS").child(mAuth.getCurrentUser().getUid());

        listViewMeals = findViewById(R.id.cookOffferedMeals);

        meals = new ArrayList<Meal>();



        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showMealEntry(position);
                return true;
            }
        });


        Button addMeal = (Button) findViewById(R.id.addMeal);

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), add_meal.class);
                startActivity(intent);
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
                for (DataSnapshot s:snapshot.getChildren()) {
                    Meal tmpMeal = new Meal();
                    tmpMeal.setName(s.child("name").getValue(String.class));
                    tmpMeal.setAllergens(s.child("allergens").getValue(new GenericTypeIndicator<ArrayList<String>>() {
                    }));
                    tmpMeal.setCalories(s.child("calories").getValue(double.class));
                    tmpMeal.setCuisine(s.child("cuisine").getValue(String.class));
                    tmpMeal.setDescription(s.child("description").getValue(String.class));
                    tmpMeal.setIngredients(s.child("ingredients").getValue(new GenericTypeIndicator<ArrayList<String>>() {
                    }));
                    tmpMeal.setIsOffered(Boolean.TRUE.equals(s.child("isOffered").getValue(boolean.class)));
                    tmpMeal.setMealType(s.child("mealType").getValue(String.class));
                    tmpMeal.setPrice(s.child("price").getValue(double.class));
                    tmpMeal.setServingSize(s.child("servingSize").getValue(double.class));

                    meals.add(tmpMeal);
                }

                MealList mealAdapter = new MealList(currentlyOffered.this,meals);
                listViewMeals.setAdapter(mealAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showMealEntry(int position){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cook_offered_dialog, null);
        dialogBuilder.setView(dialogView);
        Button toggleOffer = dialogView.findViewById(R.id.toggleoffer);
        Button deleteMeal = dialogView.findViewById(R.id.deletemeal);
        final AlertDialog b = dialogBuilder.create();

            toggleOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleOffered(meals.get(position).getName());
                }
            });
            deleteMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteMeal(meals.get(position).getName());
                }
            });
            b.show();

    }


    private void toggleOffered(String name){
        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isOffered = snapshot.child("isOffered").getValue(boolean.class);
                dB.child(name).child("isOffered").removeValue();
                if (isOffered){
                    dB.child(name).child("isOffered").setValue(false);
                }else{
                    dB.child(name).child("isOffered").setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void deleteMeal(String name){
        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isOffered = snapshot.child("isOffered").getValue(boolean.class);
                if (isOffered == false){
                    Toast.makeText(currentlyOffered.this, "Deleted Meal", Toast.LENGTH_LONG).show();
                    dB.child(name).removeValue();
                }else{
                    Toast.makeText(currentlyOffered.this, "Could not Delete Meal. Meal is currently offered.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

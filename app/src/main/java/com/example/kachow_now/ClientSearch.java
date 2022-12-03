package com.example.kachow_now;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ClientSearch extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private DatabaseReference rateMealDB;
    private ArrayList<Meal> meals;
    private ListView listViewSearch;

    boolean isSUS;
    boolean isBAN;


    private String searchQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchQuery = getIntent().getExtras().getString("query").trim();

        meals = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS");

        EditText searchBox = findViewById(R.id.searchItemName);
        listViewSearch = findViewById(R.id.lv1);

        searchBox.setFocusableInTouchMode(false);
        searchBox.setCursorVisible(false);
        searchBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(searchQuery.length())});
        searchBox.setText(searchQuery);

        listViewSearch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Meal m = meals.get(position);
                String cUID = m.getCookUID();
                Intent intent = new Intent(getApplicationContext(), OfferedMealsClientSide.class);
                intent.putExtra("UID", cUID);
                startActivity(intent);
                return false;
            }
        });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                for (DataSnapshot cook : snapshot.getChildren()) {
                    for (DataSnapshot s : cook.getChildren()) {
                        String cookUID = s.child("cookUID").getValue(String.class);

                        DatabaseReference d = FirebaseDatabase.getInstance()
                                .getReference("UID").child(cookUID);

                        d.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot s) {
                                isSUS = s.child("isSuspended").getValue(boolean.class);
                                isBAN = s.child("isSuspended").getValue(boolean.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                isSUS = false;
                                isBAN = false;
                                //Assume everyone is a good person :-)
                            }
                        });

                        String name = s.child("name").getValue(String.class);
                        assert name != null;
                        if (name.equalsIgnoreCase(searchQuery) && !isSUS && !isBAN) {
                            Meal tmpMeal = new Meal();
                            tmpMeal.setName(s.child("name").getValue(String.class));
                            tmpMeal.setAllergens(s.child("allergens").getValue(new GenericTypeIndicator<ArrayList<String>>() {
                            }));
                            tmpMeal.setCalories(s.child("calories").getValue(double.class));
                            tmpMeal.setCuisine(s.child("cuisine").getValue(String.class));
                            tmpMeal.setCookUID(s.child("cookUID").getValue(String.class));
                            tmpMeal.setDescription(s.child("description").getValue(String.class));
                            tmpMeal.setIngredients(s.child("ingredients").getValue(new GenericTypeIndicator<ArrayList<String>>() {
                            }));
                            tmpMeal.setIsOffered(Boolean.TRUE.equals(s.child("isOffered").getValue(boolean.class)));
                            tmpMeal.setMealType(s.child("mealType").getValue(String.class));
                            tmpMeal.setPrice(s.child("price").getValue(double.class));
                            tmpMeal.setServingSize(s.child("servingSize").getValue(double.class));
                            try {
                                tmpMeal.setRating(s.child("rating").getValue(double.class));
                            } catch (Exception e) {
                                tmpMeal.setRating(-1.0);
                            }
                            meals.add(tmpMeal);
                        }
                    }
                }
                MealListClient mealsAdapter = new MealListClient(ClientSearch.this, meals);
                listViewSearch.setAdapter(mealsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

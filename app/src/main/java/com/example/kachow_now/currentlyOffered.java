package com.example.kachow_now;

import android.app.AlertDialog;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();


        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final View dialogView = inflater.inflate(R.layout.cook_offered_dialog, null);
                dialogBuilder.setView(dialogView);
                Button toggleOffer = dialogView.findViewById(R.id.toggleoffer);
                Button deleteMeal = dialogView.findViewById(R.id.deletemeal);
                final AlertDialog b = dialogBuilder.create();

                toggleOffer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dB.child(String.valueOf(meals.get(position))).addValueEventListener( new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean isOffered = snapshot.child("isOffered").getValue(boolean.class);
                                if (isOffered){
                                    dB.child(String.valueOf(meals.get(position))).child("isOffered").removeValue();
                                    dB.child(String.valueOf(meals.get(position))).child("isOffered").setValue(false);
                                }else{
                                    dB.child(String.valueOf(meals.get(position))).child("isOffered").removeValue();
                                    dB.child(String.valueOf(meals.get(position))).child("isOffered").setValue(true);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                deleteMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(currentlyOffered.this, "Deleted Meal", Toast.LENGTH_LONG).show();
                        dB.child(String.valueOf(meals.get(position))).removeValue();
                    }
                });
                b.show();
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

                    //tmpMeal.setName();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showMealEntry(){

    }

}

package com.example.kachow_now;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OfferedMealsClientSide extends AppCompatActivity {

    public static final long FOUR_MEGABYTE = 1024 * 1024;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String cUID;

    List<Meal> meals;
    ListView listViewMeals;
    List<Meal> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_offered_meals_client_side);

        cUID = getIntent().getExtras().getString("UID");
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS").child(cUID);

        TextView name = findViewById(R.id.chefname);
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("UID").child(cUID);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullName = snapshot.child("firstName").getValue(String.class) + " "
                        + snapshot.child("lastName").getValue(String.class);
                name.setText(fullName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton chefIcon = findViewById(R.id.cheficon);

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
                        chefIcon.setImageBitmap(bm);
                    } else {
                        System.out.println("Not Successful");
                    }
                }
            });

        } catch (Exception ignored) {
        }

        chefIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CookProfileClientSide.class);
                intent.putExtra("UID", cUID);
                startActivity(intent);
            }
        });

        listViewMeals = findViewById(R.id.list_of_offered_meals);

        meals = new ArrayList<Meal>();
        cart = new ArrayList<Meal>();


        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = (meals.get(i));
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OfferedMealsClientSide.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.client_order_popup, null);

                EditText quantityEditText = dialogView.findViewById(R.id.orderQuantity);
                Button cartButton = dialogView.findViewById(R.id.addToCartButton);
                Button remove = dialogView.findViewById(R.id.deleteMeal);
                remove.setVisibility(View.GONE);

                TextView textViewMealName = dialogView.findViewById(R.id.textViewMealName);
                TextView textViewDescription = dialogView.findViewById(R.id.textViewDescription);
                TextView textViewMealType = dialogView.findViewById(R.id.textViewMealType);
                TextView textViewPrice = dialogView.findViewById(R.id.textViewPrice);
                TextView textViewCuisine = dialogView.findViewById(R.id.textViewCuisine);
                TextView textViewIngredients = dialogView.findViewById(R.id.textViewIngredients);
                TextView textViewMealAllergens = dialogView.findViewById(R.id.textViewMealAllergens);
                TextView textViewServingSize = dialogView.findViewById(R.id.textViewServingSize);
                TextView textViewRating = dialogView.findViewById(R.id.textViewRating1);
                TextView textViewCalories = dialogView.findViewById(R.id.textViewCalories);

                ImageView chefprofilePic = dialogView.findViewById(R.id.mealPhp);

                StorageReference ref = storageReference.child("images/" + cUID + "/" + meal.getName());

                final long FOUR_MEGABYTE = 1024 * 1024;
                try {
                    Task<byte[]> im = ref.getBytes(FOUR_MEGABYTE);
                    im.addOnCompleteListener(new OnCompleteListener<byte[]>() {
                        @Override
                        public void onComplete(@NonNull Task<byte[]> task) {
                            if (im.isSuccessful()) {
                                byte[] b = im.getResult();
                                Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                                chefprofilePic.setImageBitmap(bm);
                            } else {
                                System.out.println("Not Successful");
                            }
                        }
                    });

                } catch (IndexOutOfBoundsException ignored) {
                }

                String ingredients;
                StringBuilder line = new StringBuilder();
                for (String ingredient : meal.getIngredients()) {
                    line.append(ingredient + ", ");
                }
                ingredients = line.toString();

                String allergens;
                StringBuilder line2 = new StringBuilder();
                for (String allergen : meal.getAllergens()) {
                    line.append(allergen + ", ");
                }
                allergens = line2.toString();

                textViewMealName.setText("Meal name: " + meal.getName());
                textViewDescription.setText("Description: " + meal.getDescription());
                textViewMealType.setText("Meal Type: " + meal.getMealType());
                textViewPrice.setText("Price: " + meal.getPrice());
                textViewCuisine.setText("Cuisine Type: " + meal.getCuisine());
                textViewIngredients.setText("Ingredients: " + ingredients);
                textViewMealAllergens.setText("Allergens: " + allergens);
                textViewServingSize.setText("Serving size: " + meal.getServingSize());
                textViewCalories.setText("Calories: " + meal.getCalories());
                textViewRating.setText("Rating: "+meal.getRating());


                dialogBuilder.setView(dialogView);

                dialogBuilder.setTitle((meal.getName()));
                final AlertDialog b = dialogBuilder.create();
                b.show();

                cartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String quantity = quantityEditText.getText().toString();
                        if (quantity.equals("")) {
                            quantity = "1";
                        }//TODO NEED TO VALIDATE QUANTITY FIELD
                        int a = Integer.parseInt(quantity);
                        for (int j = 0; j < a; j++) {
                            cart.add(meal);
                            System.out.println("added " + meal.getName());
                        }
                        b.dismiss();
                    }
                });

                return true;
            }
        });

        Button goToCart = findViewById(R.id.cartButton);

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cart.isEmpty()) {
                    Intent intent = new Intent(v.getContext(), ClientCart.class);
                    intent.putExtra("cart", new Gson().toJson(cart));
                    intent.putExtra("UID", cUID);
                    startActivity(intent);
                } else {
                    Toast.makeText(OfferedMealsClientSide.this,
                            "Please add Items to your cart", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();


        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mealSnapShot) {
                meals.clear();
                for (DataSnapshot s : mealSnapShot.getChildren()) {
                    if (s.exists() && Boolean.TRUE.equals(s.child("isOffered").getValue(boolean.class))) {
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
                        tmpMeal.setCookUID(s.child("cookUID").getValue(String.class));
                        try {
                            tmpMeal.setRating(s.child("rating").getValue(double.class));
                        } catch (Exception e) {
                            tmpMeal.setRating(-1.0);
                        }
                        meals.add(tmpMeal);
                    }
                    MealListClient mealsAdapter = new MealListClient(OfferedMealsClientSide.this, meals);
                    listViewMeals.setAdapter(mealsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.kachow_now;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ClientCart extends AppCompatActivity {

    ListView listViewMeals;
    List<Meal> cart;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String cUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_cart);

        cart = new ArrayList<>();
        Type type = new TypeToken<List<Meal>>() {
        }.getType();
        cart = new Gson().fromJson(getIntent().getStringExtra("cart"), type);

        cUID = getIntent().getExtras().getString("UID");

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS").child(cUID);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        listViewMeals = (ListView) findViewById(R.id.cartListView);

        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = (cart.get(i));
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClientCart.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.client_order_popup, null);

                EditText quantityEditText = dialogView.findViewById(R.id.orderQuantity);
                quantityEditText.setVisibility(View.GONE);
                Button cartButton = dialogView.findViewById(R.id.addToCartButton);
                cartButton.setVisibility(View.GONE);
                Button remove = dialogView.findViewById(R.id.deleteMeal);

                TextView textViewMealName = (TextView) dialogView.findViewById(R.id.textViewMealName);
                TextView textViewDescription = (TextView) dialogView.findViewById(R.id.textViewDescription);
                TextView textViewMealType = (TextView) dialogView.findViewById(R.id.textViewMealType);
                TextView textViewPrice = (TextView) dialogView.findViewById(R.id.textViewPrice);
                TextView textViewCuisine = (TextView) dialogView.findViewById(R.id.textViewCuisine);
                TextView textViewIngredients = (TextView) dialogView.findViewById(R.id.textViewIngredients);
                TextView textViewMealAllergens = (TextView) dialogView.findViewById(R.id.textViewMealAllergens);
                TextView textViewServingSize = (TextView) dialogView.findViewById(R.id.textViewServingSize);
                TextView textViewCalories = (TextView) dialogView.findViewById(R.id.textViewCalories);

                ImageView chefprofilePic = (ImageView) dialogView.findViewById(R.id.mealPhp);

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

                textViewMealName.setText("Meal name:" + meal.getName());
                textViewDescription.setText("Description:" + meal.getDescription());
                textViewMealType.setText("Meal Type:" + meal.getMealType());
                textViewPrice.setText("Price:" + meal.getPrice());
                textViewCuisine.setText("Cuisine Type:" + meal.getCuisine());
                textViewIngredients.setText("Ingredients:" + ingredients);
                textViewMealAllergens.setText("Allergens:" + allergens);
                textViewServingSize.setText("Serving size:" + meal.getServingSize());
                textViewCalories.setText("Calories:" + meal.getCalories());


                dialogBuilder.setView(dialogView);

                dialogBuilder.setTitle((meal.getName()));
                final AlertDialog b = dialogBuilder.create();
                b.show();

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cart.remove(i);
                        MealListClient mealsAdapter = new MealListClient(ClientCart.this, cart);
                        listViewMeals.setAdapter(mealsAdapter);
                        b.dismiss();
                    }
                });

                return true;
            }
        });

        Button request = findViewById(R.id.requestOrderButton);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request r = new Request();
                r.setClientId(mAuth.getCurrentUser().getUid());
                r.setCookId(cart.get(0).getCookUID());
                for (Meal m : cart) {
                    r.add(m);
                }
                DatabaseReference tempDB = FirebaseDatabase.getInstance().getReference("ORDERS")
                        .child(cUID).child("pending").child(Long.toString(r.getCurrentTime()));
                tempDB.setValue(r);
                DatabaseReference completedOrders = FirebaseDatabase.getInstance().getReference("CLIENTLOG").child(mAuth.getCurrentUser().getUid());
                completedOrders.setValue(r);
                completedOrders.child("accepted").setValue(null);


                cart.clear();
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        MealListClient mealsAdapter = new MealListClient(this, cart);
        listViewMeals.setAdapter(mealsAdapter);
    }
}

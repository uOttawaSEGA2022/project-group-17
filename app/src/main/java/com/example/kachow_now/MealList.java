package com.example.kachow_now;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

public class MealList extends ArrayAdapter<Meal> {
    private final Activity context;
    List<Meal> meals;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public MealList(Activity context, List<Meal> meals) {
        super(context, R.layout.layout_meal_list, meals);
        this.context = context;
        this.meals = meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_meal_list, null, true);

        TextView textViewMealName = listViewItem.findViewById(R.id.textViewMealName);
        TextView textViewDescription = listViewItem.findViewById(R.id.textViewDescription);
        TextView textViewMealType = listViewItem.findViewById(R.id.textViewMealType);
        TextView textViewPrice = listViewItem.findViewById(R.id.textViewPrice);
        TextView textViewCuisine = listViewItem.findViewById(R.id.textViewCuisine);
        TextView textViewIngredients = listViewItem.findViewById(R.id.textViewIngredients);
        TextView textViewMealAllergens = listViewItem.findViewById(R.id.textViewMealAllergens);
        TextView textViewServingSize = listViewItem.findViewById(R.id.textViewServingSize);
        TextView textViewCalories = listViewItem.findViewById(R.id.textViewCalories);
        TextView textViewRating = listViewItem.findViewById(R.id.textViewRating);

        ImageView chefprofilePic = listViewItem.findViewById(R.id.mealPhp);
        SwitchCompat offered = listViewItem.findViewById(R.id.Offered_switch);

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Meal meal = meals.get(position);
        String cUID = meal.getCookUID();


        StorageReference ref = storageReference.child("images/" +
                (mAuth.getCurrentUser()).getUid() + "/" + meal.getName());

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

        } catch (Exception ignored) {
        }

        String ingredients;
        StringBuilder line = new StringBuilder();
        for (String ingredient : meal.getIngredients()) {
            if (!ingredient.isEmpty()) {
                line.append(ingredient + ", ");
            }
        }
        line.deleteCharAt(line.length() - 1);
        ingredients = line.toString();
        String allergens;
        if (meal.getAllergens() != null) {

            StringBuilder line2 = new StringBuilder();
            for (String allergen : meal.getAllergens()) {
                if (!allergen.isEmpty()) {
                    line.append(allergen + ", ");
                }
            }
            if (line2.length() > 0) {
                line2.deleteCharAt(line2.length() - 1);
            }
            allergens = line2.toString();
        } else {
            allergens = "";
        }


        textViewMealName.setText("Meal name:" + meal.getName());
        textViewDescription.setText("Description:" + meal.getDescription());
        textViewMealType.setText("Meal Type:" + meal.getMealType());
        textViewPrice.setText("Price:" + meal.getPrice());
        textViewCuisine.setText("Cuisine Type:" + meal.getCuisine());
        textViewIngredients.setText("Ingredients:" + ingredients);
        textViewMealAllergens.setText("Allergens:" + allergens);
        textViewServingSize.setText("Serving size:" + meal.getServingSize());
        textViewCalories.setText("Calories:" + meal.getCalories());
        textViewRating.setText("Rating: " + meal.getRating());

        offered.setChecked(meal.getIsOffered());


        return listViewItem;
    }
}
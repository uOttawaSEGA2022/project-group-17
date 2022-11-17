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

        TextView textViewMealName = (TextView) listViewItem.findViewById(R.id.textViewMealName);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView textViewMealType = (TextView) listViewItem.findViewById(R.id.textViewMealType);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);
        TextView textViewCuisine = (TextView) listViewItem.findViewById(R.id.textViewCuisine);
        TextView textViewIngredients = (TextView) listViewItem.findViewById(R.id.textViewIngredients);
        TextView textViewMealAllergens = (TextView) listViewItem.findViewById(R.id.textViewMealAllergens);
        TextView textViewServingSize = (TextView) listViewItem.findViewById(R.id.textViewServingSize);
        TextView textViewCalories = (TextView) listViewItem.findViewById(R.id.textViewCalories);

        ImageView chefprofilePic = (ImageView) listViewItem.findViewById(R.id.mealPhp);
        SwitchCompat offered = (SwitchCompat) listViewItem.findViewById(R.id.Offered_switch);

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Meal meal = meals.get(position);
        String cUID = meal.getCookUID();


        StorageReference ref = storageReference.child("images/" +
                (mAuth.getCurrentUser()).getUid() + "/" + meal.getName());

        final long TWO_MEGABYTE = 2048 * 2048;
        try {
            Task<byte[]> im = ref.getBytes(TWO_MEGABYTE);
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
        for (String allergen: meal.getAllergens()){
            line.append(allergen+", ");
        }
        allergens = line2.toString();

        textViewMealName.setText(meal.getName());
        textViewDescription.setText(meal.getDescription());
        textViewMealType.setText(meal.getMealType());
        textViewPrice.setText(String.valueOf(meal.getPrice()));
        textViewCuisine.setText(meal.getCuisine());
        textViewIngredients.setText(ingredients);
        textViewMealAllergens.setText(allergens);
        textViewServingSize.setText(String.valueOf(meal.getServingSize()));
        textViewCalories.setText(String.valueOf(meal.getCalories()));

        offered.setChecked(meal.getIsOffered());



        return listViewItem;
    }
}
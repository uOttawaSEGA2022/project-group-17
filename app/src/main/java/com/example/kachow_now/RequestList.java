package com.example.kachow_now;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RequestList extends ArrayAdapter<Request> {
    private final Activity context;
    private final List<Request> requests;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public RequestList(Activity context, List<Request> requests) {
        super(context, R.layout.layout_request_list, requests);
        this.context = context;
        this.requests = requests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_meal_list, null, true);


        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //TODO actually finish this
        /*StorageReference ref = storageReference.child("images/" +
                (mAuth.getCurrentUser()).getUid() + "/" + meal.getName());

        final long FOUR_MEGABYTE = 4096 * 4096;
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
        for (String allergen: meal.getAllergens()){
            line.append(allergen+", ");
        }
        allergens = line2.toString();

        textViewMealName.setText("Meal name:"+meal.getName());
        textViewDescription.setText("Description:"+meal.getDescription());
        textViewMealType.setText("Meal Type:"+meal.getMealType());
        textViewPrice.setText(String.valueOf("Price:"+meal.getPrice()));
        textViewCuisine.setText("Cuisine Type:"+meal.getCuisine());
        textViewIngredients.setText("Ingredients:"+ingredients);
        textViewMealAllergens.setText("Allergens:"+allergens);
        textViewServingSize.setText(String.valueOf("Serving size:"+meal.getServingSize()));
        textViewCalories.setText(String.valueOf("Calories:"+meal.getCalories()));

        offered.setChecked(meal.getIsOffered());
        */


        return listViewItem;
    }
}

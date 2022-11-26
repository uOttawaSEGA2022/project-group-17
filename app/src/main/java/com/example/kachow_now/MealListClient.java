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

public class MealListClient extends ArrayAdapter<Meal> {
    private final Activity context;
    List<Meal> meals;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public MealListClient(Activity context, List<Meal> meals) {
        super(context, R.layout.layout_meal_list_client, meals);
        this.context = context;
        this.meals = meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_meal_list_client, null, true);

        TextView textViewMealName = (TextView) listViewItem.findViewById(R.id.MealName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.MealPrice);
        TextView textViewCalories = (TextView) listViewItem.findViewById(R.id.MealCalories);
        ImageView chefprofilePic = (ImageView) listViewItem.findViewById(R.id.MealPhp);

        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Meal meal = meals.get(position);
        String cUID = meal.getCookUID();


        StorageReference ref = storageReference.child("images/" +
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

        textViewMealName.setText("Meal name:" + meal.getName());
        textViewPrice.setText("Price:" + meal.getPrice());
        textViewCalories.setText("Calories:" + meal.getCalories());


        return listViewItem;
    }
}
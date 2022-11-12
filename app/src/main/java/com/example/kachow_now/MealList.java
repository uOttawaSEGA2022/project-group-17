package com.example.kachow_now;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.StringBufferInputStream;
import java.util.List;

public class MealList extends ArrayAdapter<Meal> {
    private final Activity context;
    List<Meal> meals ;

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

        Meal meal = meals.get(position);

        String ingredients;
        StringBuilder line = new StringBuilder();
        for (String ingredient: meal.getIngredients()){
            line.append(ingredient);
        }
        ingredients = line.toString();

        String allergens;
        StringBuilder line2 = new StringBuilder();
        for (String allergen: meal.getAllergens()){
            line.append(allergen);
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


        return listViewItem;
    }
}
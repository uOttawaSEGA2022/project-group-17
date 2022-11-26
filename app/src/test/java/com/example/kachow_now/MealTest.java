package com.example.kachow_now;


import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class MealTest {

    @Test
    public void getName() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        assertEquals( "Salad", salad.getName());
    }

    @Test
    public void setName() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        salad.setName("Mediterranean Salad");
        assertEquals( "Mediterranean Salad", salad.getName());
    }

    @Test
    public void getDescription() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        assertEquals( "well-dressed salad", salad.getDescription());
    }

    @Test
    public void setDescription() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        salad.setDescription("VERY well-dressed salad");
        assertEquals( "VERY well-dressed salad", salad.getDescription());
    }

    @Test
    public void getServingSize() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        assertEquals(2.00, salad.getServingSize(), 0);
    }

    @Test
    public void setServingSize() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        salad.setServingSize(3.00);
        assertEquals(3.00, salad.getServingSize(), 0);
    }

    @Test
    public void getCalories() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        assertEquals(200.0, salad.getCalories(), 0);
    }

    @Test
    public void setCalories() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("lettuce");
        Meal salad = new Meal("Salad", "well-dressed salad", "Lunch", 5.00, "Mediterranean", ingredients, null, 2.00, 200.00 );
        salad.setCalories(150.0);
        assertEquals(150.0, salad.getCalories(), 0);
    }
}
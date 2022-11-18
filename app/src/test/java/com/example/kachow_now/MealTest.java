package com.example.kachow_now;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class MealTest {

    @Test
    public void getName() {
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
}
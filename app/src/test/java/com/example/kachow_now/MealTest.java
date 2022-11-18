package com.example.kachow_now;


import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class MealTest {

    @Test
    public void getName() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ArrayList<String> allergens = new ArrayList<String>();

        Meal daal = new Meal("daal", "yummy yummy good good", "dinner", 32.0, "south east asian", ingredients, allergens, 2, 120);
        assertEquals("daal", daal.getName());
    }

    @Test
    public void setName() {
        Meal rightinfrontofmysalad = new Meal();
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void setDescription() {
    }
}
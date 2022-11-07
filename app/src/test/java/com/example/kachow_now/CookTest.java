package com.example.kachow_now;

import static org.junit.Assert.*;

import org.junit.Test;

public class CookTest {

    @Test
    public void cookInfoValidator() {
        Cook testCook = new Cook("John", "Bowling", "abc123", "bobolin123@hotmail.com", "123 shundo road",
                "K1G 3Z7", 1123345567, 00003, 004, 1234512345);
        assertEquals("John", testCook.getFirstName());
        assertEquals("Bowling", testCook.getLastName());
        assertEquals("abc123", testCook.getPassword());
        assertEquals("bobolin123@hotmail.com", testCook.getEmail());
        assertEquals("123 shundo road", testCook.getAddress());
        assertEquals("K1G 3Z7", testCook.getPostalCode());
        assertEquals(1123345567, testCook.getPhoneNumber());

    }

}
package com.example.kachow_now;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdminTest {

    @Test
    public void getEmail() {
        Admin testAdmin = new Admin("adminman2@gmail.com", "abc123", "ge", "ge");
        assertEquals("adminman2@gmail.com", testAdmin.getEmail());

    }

    @Test
    public void getPassword() {
        Admin testAdmin = new Admin("adminman2@gmail.com", "abc123", "ge", "ge");
        assertEquals("abc123", testAdmin.getPassword());
    }
}
package com.example.kachow_now;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTest extends Cook {

    AdminPage newAdmin = new AdminPage();

    //String firstName, String lastname, String password, String email, String address, String postalCode, long phoneNumber, int transit, int institution, double account

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

    @Test
    public void clientInfoValidator() {
        Client testClient = new Client("def123", "Siva", "Doe", "ssent069@uottawa.ca", 0000000000000000, 5, 2, 231, "456 swag road", "K1G 3Z7", 1234567890);
        assertEquals("def123", testClient.getPassword());
        assertEquals("Siva", testClient.getFirstName());
        assertEquals("Doe", testClient.getLastName());
        assertEquals("ssento9@uottawa.ca", testClient.getEmail());
        assertEquals("456 swag road", testClient.getAddress());
        assertEquals("K1G 3Z7", testClient.getAddress());
        assertEquals(1234567890, testClient.getPhoneNumber());
    }

    @Test
    public void newAccountSuspendValidator() {
        Cook evilCook = new Cook();
        newAdmin.suspendCook(evilCook);
        assertTrue(evilCook.getIsSuspended());
    }

    @Test
    public void newAccountBanValidator() {
        Cook evilCook = new Cook();
        newAdmin.banCook(evilCook);
        assertTrue(evilCook.getIsBanned());
    }

}

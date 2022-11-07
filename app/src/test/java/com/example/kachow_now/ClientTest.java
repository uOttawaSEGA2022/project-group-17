package com.example.kachow_now;

import static org.junit.Assert.*;

import org.junit.Test;


public class ClientTest {

    @Test
    public void clientInfoValidator() {
        Client testClient = new Client("def123", "Siva", "Doe", "ssento@uottawa.ca", 0000000000000000, 5, 2, 231, "456 swag road", "K1G 3Z7", 1234567890);
        assertEquals("def123", testClient.getPassword());
        assertEquals("Siva", testClient.getFirstName());
        assertEquals("Doe", testClient.getLastName());
        assertEquals("ssento@uottawa.ca", testClient.getEmail());
        assertEquals("456 swag road", testClient.getAddress());
        assertEquals("K1G 3Z7", testClient.getPostalCode());
        assertEquals(1234567890, testClient.getPhoneNumber());
    }

}
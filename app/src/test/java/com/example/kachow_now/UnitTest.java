package com.example.kachow_now;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTest extends Cook {

    AdminPage newAdmin = new AdminPage();

    Cook newCook = new Cook();
    @Test
    public void newAccountBanValidator() {
        assertFalse(newCook.getIsBanned());
    }

}

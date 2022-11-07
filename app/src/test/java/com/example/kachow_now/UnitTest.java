package com.example.kachow_now;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTest extends Cook {

    AdminPage newAdmin = new AdminPage();

    @Test
    public void cookInfoValidator() {
        
    }

    @Test
    public void newAccountBanValidator() {
        Cook evilCook = new Cook();
        newAdmin.banCook(evilCook);
        assertFalse(evilCook.getIsBanned());
        assertTrue(evilCook.getIsBanned());
    }

}

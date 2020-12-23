package Purse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Purse.
 */
public class PurseTest {


    private Purse pDefault;
    private Purse pCustom;
    private int[] userPin;
    private int[] adminPin;

    @Before
    public void setUp() {
        userPin = new int[]{1, 2, 3, 4};
        adminPin = new int[]{1, 2, 3, 4, 5, 6};
        pDefault = new Purse(userPin, adminPin);
    }

    @Test
    public void testVerifyPINUser() {
        assertTrue(pDefault.verifyPINUser(new int[]{1, 2, 3, 4}));
        assertFalse(pDefault.verifyPINUser(new int[]{3, 2, 1, 4}));
    }

    @Test
    public void testVerifyPINAdmin() {
        assertTrue(pDefault.verifyPINAdmin(new int[]{1, 2, 3, 4, 5, 6}));
        assertFalse(pDefault.verifyPINAdmin(new int[]{3, 2, 1, 4, 6 ,5}));
    }

    @Test
    public void testGetIdentificationUser(){

    }

    @Test
    public void testGetIdentificationAdmin(){

    }
}

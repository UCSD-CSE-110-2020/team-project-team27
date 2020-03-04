package com.example.wwr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TeammateUnitTest {
    @Test
    public void getEmail_isCorrect() {
        Teammate tst = new Teammate("Bill Griswold", "bgriswold@ucsd.edu", 0);
        assertEquals(tst.getEmail(),"bgriswold@ucsd.edu");
    }
    @Test
    public void getName_isCorrect() {
        Teammate tst = new Teammate("Bill Griswold", "bgriswold@ucsd.edu", 0);
        assertEquals(tst.getName(),"Bill Griswold");
    }
}
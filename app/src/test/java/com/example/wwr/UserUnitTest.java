package com.example.wwr;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserUnitTest {
    @Test
    public void returnDistance_isCorrect() {
        User.setHeight(5, 8);

        User.setSteps(0);
        assertEquals(User.returnDistance(),(double) 0 , 5*Math.ulp(0));

        User.setSteps(3223);
        assertEquals(User.returnDistance(),(double) 1.49 , 5*Math.ulp(1.49));

        User.setSteps(11);
        assertEquals(User.returnDistance(),(double) 0.01 , 5*Math.ulp(0.01));
    }
}
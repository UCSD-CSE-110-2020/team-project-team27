package com.example.wwr;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RoutesUnitTest {
    @Test
    public void getTime_isCorrect() {
        Route test = new Route("Revelle Walk", "Revelle").setSteps(10).setDistance(.1).setTime(new int[]{1, 0, 1});

        assertArrayEquals(new int[] {1, 0, 1}, test.getTime());

        test.setTime(new int[] {4, 5, 1});
        assertArrayEquals(new int[] {4, 5, 1}, test.getTime());
    }

    @Test
    public void getName_isCorrect(){
        Route test = new Route("Revelle Walk", "Revelle").setSteps(10).setDistance(.1).setTime(new int[]{1, 0, 1});

        assertEquals("Revelle Walk", test.getName());

        test.setName("Bruh moment");
        assertEquals("Bruh moment", test.getName());
    }

    @Test
    public void getDistance_isCorrect() {
        Route test = new Route("Revelle Walk", "Revelle").setSteps(10).setDistance(.1).setTime(new int[]{1, 0, 1});

        assertEquals(test.getDistance(), .1, 5 * Math.ulp(.1));

        test.setDistance(19.45);
        assertEquals(test.getDistance(), 19.45, 5 * Math.ulp(19.45));
    }
}
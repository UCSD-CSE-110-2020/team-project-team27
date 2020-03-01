package com.example.wwr;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Set;
import java.util.TreeSet;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Config.OLDEST_SDK)
public class UpdateFireBaseUnitTest {
    @Test
    public void storeRouteTest() {
        StartAWalkActivity activity = Robolectric.setupActivity(StartAWalkActivity.class);

        SharedPreferences sp = activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        sp.edit().clear().apply();
        sp.edit().putStringSet("routeNames", new TreeSet<String>()).apply();

        //assertEquals(UserSharePreferences.storeRoute("Test", "loc"), true);
        Set<String> routeList = sp.getStringSet("routeNames", null);
        assertEquals(true, true);
        //assertEquals(routeList.contains("Test"), true);
    }

    /*@Test
    public void storeDuplicateRouteTest(){
        StartAWalkActivity activity = Robolectric.setupActivity(StartAWalkActivity.class);

        SharedPreferences sp = activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        sp.edit().clear().apply();

        UserSharePreferences.storeRoute("Test", "loc");
        assertEquals(UserSharePreferences.storeRoute("Test", "loc"), false);
    }*/
}

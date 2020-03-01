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
public class StartAWalkUnitTest {
    @Test
    public void storeRouteTest() {
        StartAWalkActivity activity = Robolectric.setupActivity(StartAWalkActivity.class);

        UserSharePreferences.routeSP= activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        UserSharePreferences.routeSP.edit().clear().apply();
        UserSharePreferences.routeSP.edit().putStringSet("routeNames", new TreeSet<String>()).apply();

        assertEquals(UserSharePreferences.storeRoute("Test", "loc", true), true);
        Set<String> routeList = UserSharePreferences.routeSP.getStringSet("routeNames", null);

        assertEquals(routeList.contains("Test"), true);
    }

    @Test
    public void storeDuplicateRouteTest(){
        StartAWalkActivity activity = Robolectric.setupActivity(StartAWalkActivity.class);

        UserSharePreferences.routeSP= activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        UserSharePreferences.routeSP.edit().clear().apply();

        UserSharePreferences.storeRoute("Test", "loc", true);
        assertEquals(UserSharePreferences.storeRoute("Test", "loc", true), false);
    }
}

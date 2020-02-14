package com.example.wwr;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.TreeSet;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Config.OLDEST_SDK)
public class RoutesPageUnitTest {
    @Test
    public void populateListTest() {
        RoutesPageActivity activity = Robolectric.setupActivity(RoutesPageActivity.class);
        SharedPreferences sp = activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        sp.edit().clear().apply();

        TreeSet<String> set = new TreeSet<String>();
        set.add("Test");

        sp.edit().putStringSet("routeNames", set).apply();


        ArrayList<Route> list = new ArrayList<>();
        activity.populateList(list);

        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getName(), "Test");
    }
}

package com.example.wwr;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Config.OLDEST_SDK)
public class NotesUnitTest {
    @Test
    public void addNotesUnitTest() {
        FeaturesActivity activity = Robolectric.setupActivity(FeaturesActivity.class);
        SharedPreferences sp = activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        sp.edit().clear().apply();

        sp.edit().putString("test_notes", "im a note!").apply();

        assertEquals(sp.getString("test_notes", ""), "im a note!");
    }
}

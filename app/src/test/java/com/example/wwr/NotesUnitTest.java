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
    public void populateListTest() {
        TakeHeightActivity activity = Robolectric.setupActivity(TakeHeightActivity.class);
        SharedPreferences sp = activity.getSharedPreferences("height", Context.MODE_PRIVATE);

        sp.edit().clear().apply();

        sp.edit().putInt("FEET", 5).apply();
        sp.edit().putInt("INCH", 10).apply();

        activity.finish();

        TakeHeightActivity activityNew = Robolectric.setupActivity(TakeHeightActivity.class);
        sp = activityNew.getSharedPreferences("height", Context.MODE_PRIVATE);


        assertEquals(sp.getInt("FEET", 0), 5);
        assertEquals(sp.getInt("INCH", 0), 10);
    }
}

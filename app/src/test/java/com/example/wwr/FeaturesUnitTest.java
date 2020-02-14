package com.example.wwr;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.common.Feature;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Config.OLDEST_SDK)
public class FeaturesUnitTest {
    @Test
    public void isAllSelectedTest() {
        FeaturesActivity activity = Robolectric.setupActivity(FeaturesActivity.class);

        assertEquals(activity.isAllSelected(), false);
    }
}

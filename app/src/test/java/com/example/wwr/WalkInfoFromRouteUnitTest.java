package com.example.wwr;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Config.OLDEST_SDK)
public class WalkInfoFromRouteUnitTest {
    @Test
    public void expandFeaturesTest() {
        WalkInfoFromRouteActivity activity = Robolectric.setupActivity(WalkInfoFromRouteActivity.class);

        assertEquals(activity.expandFeatures("O F S E E", true), "Out and Back     Flat     Street     Even Surface     Easy     Favorite");
        assertEquals(activity.expandFeatures("L H T U M", false), "Loop     Hilly     Trail     Uneven Surface     Moderate     ");
        assertEquals(activity.expandFeatures("L H T U D", false), "Loop     Hilly     Trail     Uneven Surface     Difficult     ");
    }
}

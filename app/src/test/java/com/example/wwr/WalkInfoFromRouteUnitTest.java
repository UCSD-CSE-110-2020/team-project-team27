package com.example.wwr;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class WalkInfoFromRouteUnitTest {


    @Test
    public void clickingButton_shouldChangeMessage() {
        WalkInfoFromRouteActivity activity = Robolectric.setupActivity(WalkInfoFromRouteActivity.class);

        assertEquals(activity.expandFeatures("", true), "");
);
    }
}

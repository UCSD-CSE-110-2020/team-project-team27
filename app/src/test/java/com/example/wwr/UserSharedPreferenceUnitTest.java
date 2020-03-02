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
public class UserSharedPreferenceUnitTest {
    @Test
    public void setRouteShared_isCorrect() {
        StartAWalkActivity activity = Robolectric.setupActivity(StartAWalkActivity.class);

        SharedPreferences sp = activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        UserSharePreferences.setRouteShared(sp);

        assertEquals(UserSharePreferences.routeSP, sp);
    }

    @Test
    public void setHeightShared_isCorrect() {
        HomeScreenActivity activity = Robolectric.setupActivity(HomeScreenActivity.class);

        SharedPreferences sp = activity.getSharedPreferences("height", Context.MODE_PRIVATE);

        UserSharePreferences.setRouteShared(sp);

        assertEquals(UserSharePreferences.heightSP, sp);
    }
}

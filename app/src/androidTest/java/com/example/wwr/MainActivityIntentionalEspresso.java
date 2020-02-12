package com.example.wwr;


import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.wwr.fitness.FitnessService;
import com.example.wwr.fitness.FitnessServiceFactory;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityIntentionalEspresso {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    @Rule
    public ActivityTestRule<TakeHeightActivity> mActivityTestRule = new ActivityTestRule<>(TakeHeightActivity.class);

    @Test
    public void mainActivityIntentionalEspresso() {

        /*mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(HomeScreenActivity homeScreenActivity) {
                return new TestFitnessService(homeScreenActivity);
            }
        });
        mActivityTestRule.getActivity().launchHomeScreenActivity();
        if(!User.hasHeight()) {
            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.done)));
            appCompatButton.perform(click());
        }
        // the following is a dummy
        int [] time = {0, 10, 54};
        Route testRoute = new Route("Apple Store", "UTC", 1000, 0.5, time);
        ViewIntentionalRoute.setRoute(testRoute);
        ViewInteraction textView = onView(
                allOf(withId(R.id.LastWalkName)));
        textView.check(matches(withText("Apple Store")));
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.lastWalkStart)));
        textView2.check(matches(withText("UTC")));
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.LWsteps)));
        textView3.check(matches(withText("1000")));
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.LWmiles)));
        textView4.check(matches(withText("0.5")));
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.hour)));
        textView5.check(matches(withText("0")));
        ViewInteraction textView6 = onView(
                allOf(withId(R.id.min)));
        textView6.check(matches(withText("10")));
        ViewInteraction textView7 = onView(
                allOf(withId(R.id.sec)));
        textView7.check(matches(withText("54")));*/

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private HomeScreenActivity homeScreenActivity;

        public TestFitnessService(HomeScreenActivity homeScreenActivity) {
            this.homeScreenActivity = homeScreenActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount(){
            System.err.println(TAG + "updateStepCount");

            homeScreenActivity.setStepCount(1234);
        }
    }
}
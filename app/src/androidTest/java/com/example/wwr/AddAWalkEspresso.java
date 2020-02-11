package com.example.wwr;


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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddAWalkEspresso {
    private static final String TEST_SERVICE = "TEST_SERVICE";


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void AddAWalkDuplicateEspresso() {
        mActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);

        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(HomeScreenActivity homeScreenActivity) {
                return new AddAWalkEspresso.TestFitnessService(homeScreenActivity);
            }
        });

        mActivityTestRule.getActivity().launchHomeScreenActivity();


        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.done), withText("DONE"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addButton), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.textView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.textView2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.save), withText("SAVE"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton3.perform(click());

        onView(withText("information stored"))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

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


            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        homeScreenActivity.setStepCount(1234);
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}

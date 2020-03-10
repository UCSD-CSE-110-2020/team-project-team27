//package com.example.wwr;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import androidx.test.espresso.DataInteraction;
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//import androidx.test.runner.AndroidJUnit4;
//
//import com.example.wwr.fitness.FitnessService;
//import com.example.wwr.fitness.FitnessServiceFactory;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.hamcrest.core.IsInstanceOf;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onData;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.replaceText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withParent;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.anything;
//import static org.hamcrest.Matchers.is;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class LaunchGoogleMapsEspresso {
//
//    private static final String TEST_SERVICE = "TEST_SERVICE";
//    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
//
//    @Rule
//    public ActivityTestRule<HomeScreenActivity> mActivityTestRule = new ActivityTestRule<>(HomeScreenActivity.class, false, false);
//
//
//    @Test
//    public void launchGoogleMapsEspresso() {
//        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
//            @Override
//            public FitnessService create(HomeScreenActivity homeScreenActivity) {
//                return new LaunchGoogleMapsEspresso.TestFitnessService(homeScreenActivity);
//            }
//        });
//
//        User.setEmail("test@test.com");
//        User.setName("test");
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        UpdateFirebase.setDatabase(db);
//        db.disableNetwork();
//
//        Intent i = new Intent();
//        i.putExtra(FITNESS_SERVICE_KEY, TEST_SERVICE);
//        mActivityTestRule.launchActivity(i);
//
//        SharedPreferences sp = mActivityTestRule.getActivity().getSharedPreferences("height", Context.MODE_PRIVATE);
//
//        if(sp.getInt("FEET", 0) == 0){
//            ViewInteraction appCompatButton = onView(
//                    allOf(withId(R.id.done), withText("DONE"),
//                            childAtPosition(
//                                    allOf(withId(R.id.coordinatorLayout2),
//                                            childAtPosition(
//                                                    withId(android.R.id.content),
//                                                    0)),
//                                    1),
//                            isDisplayed()));
//            appCompatButton.perform(click());
//        }
//
//
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.done)));
//        appCompatButton.perform(click());
//
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.routesButton)));
//        appCompatButton2.perform(click());
//
//
//        ViewInteraction floatingActionButton = onView(
//                allOf(withId(R.id.addRouteBtn)));
//        floatingActionButton.perform(click());
//
//
//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.textView)));
//        appCompatEditText.perform(replaceText("TestGMaps"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.textView2)));
//        appCompatEditText2.perform(replaceText("Pepper Canyon Hall"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton3 = onView(
//                allOf(withId(R.id.save)));
//        appCompatButton3.perform(click());
//
//        ViewInteraction appCompatButton4 = onView(
//                allOf(withId(R.id.routesButton)));
//        appCompatButton4.perform(click());
//
//
//        DataInteraction linearLayout = onData(anything())
//                .inAdapterView(allOf(withId(R.id.route_list),
//                        childAtPosition(
//                                withClassName(is("android.widget.RelativeLayout")),
//                                0)))
//                .atPosition(0);
//        linearLayout.perform(click());
//
//
//        ViewInteraction appCompatTextView = onView(
//                allOf(withId(R.id.startLoc)));
//        appCompatTextView.perform(click());
//
//
////        ViewInteraction textView = onView(
////                allOf(withText("Pepper Canyon Hall"),
////                        childAtPosition(
////                                allOf(withId(com.google.android.apps.maps.R.id.search_omnibox_text_box),
////                                        childAtPosition(
////                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
////                                                1)),
////                                0),
////                        isDisplayed()));
////        textView.check(matches(withText("Pepper Canyon Hall")));
//    }
//
//    class TestFitnessService implements FitnessService {
//        private static final String TAG = "[TestFitnessService]: ";
//        private HomeScreenActivity homeScreenActivity;
//
//        public TestFitnessService(HomeScreenActivity homeScreenActivity) {
//            this.homeScreenActivity = homeScreenActivity;
//        }
//
//        @Override
//        public int getRequestCode() {
//            return 0;
//        }
//
//        @Override
//        public void setup() {
//            System.out.println(TAG + "setup");
//        }
//
//        @Override
//        public void updateStepCount(){
//            System.err.println(TAG + "updateStepCount");
//
//
//            try {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        homeScreenActivity.setStepCount(1234);
//                    }
//                });
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        }
//    }
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}

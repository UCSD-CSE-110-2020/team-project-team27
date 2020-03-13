package com.example.wwr;


import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class InviteATeammateEspresso {

    private static final String TEST_SERVICE = "TEST_SERVICE";
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";

    @Rule
    public ActivityTestRule<HomeScreenActivity> mActivityTestRule = new ActivityTestRule<>(HomeScreenActivity.class, false, false);

    @Test
    public void inviteATeamMemberTest() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(HomeScreenActivity homeScreenActivity) {
                return new InviteATeammateEspresso.TestFitnessService(homeScreenActivity);
            }
        });
        User.setEmail("test-test.com");
        User.setName("test");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.document("/users/test-test.com").delete();
        db.document("/users/testFriend-test.com").delete();
        UpdateFirebase.setDatabase(db);
        //db.disableNetwork();
        Intent i = new Intent();
        i.putExtra(FITNESS_SERVICE_KEY, TEST_SERVICE);
        mActivityTestRule.launchActivity(i);
        SharedPreferences sp = mActivityTestRule.getActivity().getSharedPreferences("height", Context.MODE_PRIVATE);
        if(sp.getInt("FEET", 0) == 0){
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
        }
        ViewInteraction TeamButton = onView(
                allOf(withId(R.id.TeamButton)));
        TeamButton.perform(click());
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.teamfab)));
        floatingActionButton.perform(click());
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameText)));
        appCompatEditText.perform(click());
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.nameText)));
        appCompatEditText2.perform(replaceText("testFriend"), closeSoftKeyboard());
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.emailText)));
        appCompatEditText3.perform(replaceText("testFriend-test.com"), closeSoftKeyboard());
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.saveTeamMember)));
        appCompatButton3.perform(click());
        db.collection("users/test@test.com/invites").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                assertEquals(queryDocumentSnapshots.getDocuments().get(0).get("Email"), "testFriend@test.com");
            }
        });
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
}
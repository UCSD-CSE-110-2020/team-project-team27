package com.example.wwr;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telecom.Call;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Set;

import javax.security.auth.callback.Callback;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.doAnswer;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddAWalkEspresso {

    private static final String TEST_SERVICE = "TEST_SERVICE";
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";

    @Rule
    public ActivityTestRule<HomeScreenActivity> mActivityTestRule = new ActivityTestRule<>(HomeScreenActivity.class, false, false);


    private static Answer<String> reverseMsg() {
        System.out.println("Woob");
        return new Answer<String>() {
            public String answer(InvocationOnMock invocation) {
                return "";//reverseString((String) invocation.getArguments()[0]));
            }
        };
    };

    @Test
    public void startAWalkEspresso() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(HomeScreenActivity homeScreenActivity) {
                return new AddAWalkEspresso.TestFitnessService(homeScreenActivity);
            }
        });

        User.setEmail("test");

        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);

        final CollectionReference mockTeamCol = Mockito.mock(CollectionReference.class);

        final QuerySnapshot mockQuery = Mockito.mock(QuerySnapshot.class);

        final Task<QuerySnapshot> mockQ = Mockito.mock(Task.class);
        UpdateFirebase.setDatabase(mockFirestore);

        Mockito.when(mockFirestore.collection("users" + "/" + User.getEmail() + "/" + "team")).
                thenReturn(mockTeamCol);
        Mockito.when(mockTeamCol.get()).thenReturn(mockQ);

        doAnswer(new Answer<Void>(){
                public Void answer(InvocationOnMock invocation){
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Name", "test name");
                    map.put("Email", "test email");
                    //Mockito.when(mockTeamCol.add(map)).thenCallRealMethod();

                    OnSuccessListener osl = (OnSuccessListener) invocation.getArguments()[0];
                    System.err.println("Entered ");
                    osl.onSuccess(mockQ); //ADD DATA TO QUERY
                    System.err.println("Exited ");
                    return null;
            }
        }).when(mockQ).addOnSuccessListener(ArgumentMatchers.any(OnSuccessListener.class));


        CollectionReference userColMock = Mockito.mock(CollectionReference.class);
        Task mockUserTask = Mockito.mock(Task.class);
        Mockito.when(userColMock.get()).thenReturn(mockUserTask);

        /*doAnswer(new Answer<Void>(){
            public Void answer(InvocationOnMock invocation){
                OnSuccessListener osl = (OnSuccessListener) invocation.getArguments()[0];
                osl.onSuccess(mockQuery);
                System.err.println("Entered 2");
                return null;
            }
        }).when(mockUserTask).addOnSuccessListener(ArgumentMatchers.any(OnSuccessListener.class));*/

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

        ViewInteraction pls = onView(allOf(withId(R.id.debugMode)));
        pls.perform(click());

        pls = onView(allOf(withId(R.id.ClearDataBase_debug)));
        pls.perform(click());

        pls = onView(allOf(withId(R.id.debugMode)));
        pls.perform(click());

        ViewInteraction routesButton = onView(
                allOf(withId(R.id.routesButton)));
        routesButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.fab)));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.textView)));
        appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.textView2)));
        appCompatEditText2.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.save)));
        appCompatButton3.perform(click());


        sp = mActivityTestRule.getActivity().getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        Set<String> set = sp.getStringSet("routeNames", null);

        assertEquals(set.contains("a"), true);
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

package com.example.wwr;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Config.OLDEST_SDK)
public class StartAWalkUnitTest {
    @Test
    public void storeRouteTest() {
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
        CollectionReference mockCol = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc = Mockito.mock(DocumentReference.class);
        CollectionReference mockCol2 = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc2 = Mockito.mock(DocumentReference.class);
        Task mockQ = Mockito.mock(Task.class);

        UpdateFirebase.setDatabase(mockFirestore);

        Mockito.when(mockFirestore.collection("users")).thenReturn(mockCol);
        Mockito.when(mockCol.document(User.getEmail())).thenReturn(mockDoc);
        Mockito.when(mockDoc.collection("routes")).thenReturn(mockCol2);
        Mockito.when(mockCol2.document("Test")).thenReturn(mockDoc2); // change this to routename
        Mockito.when(mockDoc2.set(new HashMap<String, String>())).thenReturn(mockQ);

        StartAWalkActivity activity = Robolectric.setupActivity(StartAWalkActivity.class);

        UserSharePreferences.routeSP= activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        UserSharePreferences.routeSP.edit().clear().apply();
        UserSharePreferences.routeSP.edit().putStringSet("routeNames", new TreeSet<String>()).apply();

        assertEquals(UserSharePreferences.storeRoute("Test", "loc"), true);
        Set<String> routeList = UserSharePreferences.routeSP.getStringSet("routeNames", null);

        assertEquals(routeList.contains("Test"), true);
    }

    @Test
    public void storeDuplicateRouteTest(){
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
        CollectionReference mockCol = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc = Mockito.mock(DocumentReference.class);
        CollectionReference mockCol2 = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc2 = Mockito.mock(DocumentReference.class);
        Task mockQ = Mockito.mock(Task.class);

        UpdateFirebase.setDatabase(mockFirestore);

        Mockito.when(mockFirestore.collection("users")).thenReturn(mockCol);
        Mockito.when(mockCol.document(User.getEmail())).thenReturn(mockDoc);
        Mockito.when(mockDoc.collection("routes")).thenReturn(mockCol2);
        Mockito.when(mockCol2.document("Test")).thenReturn(mockDoc2); // change this to routename
        Mockito.when(mockDoc2.set(new HashMap<String, String>())).thenReturn(mockQ);
        StartAWalkActivity activity = Robolectric.setupActivity(StartAWalkActivity.class);

        UserSharePreferences.routeSP= activity.getSharedPreferences("routeInfo", Context.MODE_PRIVATE);

        UserSharePreferences.routeSP.edit().clear().apply();

        UserSharePreferences.storeRoute("Test", "loc");
        assertEquals(UserSharePreferences.storeRoute("Test", "loc"), false);
    }
}

package com.example.wwr;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
public class UpdateFireBaseUnitTest {
    @Test
    public void setupUser_isCorrect() {
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
        CollectionReference mockCol = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc = Mockito.mock(DocumentReference.class);
        Task mockQ = Mockito.mock(Task.class);

        UpdateFirebase.setDatabase(mockFirestore);
        User.setEmail("testing");
        HashMap<String, String> test = new HashMap<>();
        test.put("Name", "Name");
        test.put("Color", "" + 0);

        Mockito.when(mockFirestore.collection("users")).thenReturn(mockCol);
        Mockito.when(mockCol.document(User.getEmail())).thenReturn(mockDoc);
        Mockito.when(mockDoc.set(test)).thenCallRealMethod();
        Mockito.when(mockDoc.get()).thenReturn(mockQ);

        UpdateFirebase.setDatabase(mockFirestore);
        UpdateFirebase.setupUser("Name");

        mockDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                assertEquals((String)documentSnapshot.get("Name"), "Name");
            }
        });
    }

    @Test
    public void addedRoute_isCorrect() {
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
        CollectionReference mockCol = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc = Mockito.mock(DocumentReference.class);
        CollectionReference mockCol2 = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc2 = Mockito.mock(DocumentReference.class);

        UpdateFirebase.setDatabase(mockFirestore);
        Task mockQ = Mockito.mock(Task.class);

        Mockito.when(mockFirestore.collection("users")).thenReturn(mockCol);
        Mockito.when(mockCol.document(User.getEmail())).thenReturn(mockDoc);
        Mockito.when(mockDoc.collection("routes")).thenReturn(mockCol2);
        Mockito.when(mockCol2.document("Geisel")).thenReturn(mockDoc2);
        Mockito.when(mockDoc.get()).thenReturn(mockQ);

        UpdateFirebase.setDatabase(mockFirestore);
        UpdateFirebase.addedRoute("Geisel", "UTC");

        mockDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                assertEquals((String)documentSnapshot.get("Route"), "Geisel");
            }
        });

    }

    /*@Test
    public void acceptInvite_isCorrect() {
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
        CollectionReference mockCol = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc = Mockito.mock(DocumentReference.class);
        Task mockQ = Mockito.mock(Task.class);

        UpdateFirebase.setDatabase(mockFirestore);

        Mockito.when(mockFirestore.collection("users")).thenReturn(mockCol);
        Mockito.when(mockCol.document(User.getEmail())).thenReturn(mockDoc);
        Mockito.when(mockDoc.get()).thenReturn(mockQ);

        UpdateFirebase.acceptInvite("email1", "email2");

        mockDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // assertEquals(, "Name");
            }
        });
    }

    @Test
    public void rejectInvite_isCorrect() {
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
        CollectionReference mockCol = Mockito.mock(CollectionReference.class);
        DocumentReference mockDoc = Mockito.mock(DocumentReference.class);
        Task mockQ = Mockito.mock(Task.class);

        UpdateFirebase.setDatabase(mockFirestore);

        Mockito.when(mockFirestore.collection("users")).thenReturn(mockCol);
        Mockito.when(mockCol.document(User.getEmail())).thenReturn(mockDoc);
        Mockito.when(mockDoc.get()).thenReturn(mockQ);

        UpdateFirebase.acceptInvite("email1", "email2");

        mockDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // assertEquals(, "Name");
            }
        });
    }*/
}

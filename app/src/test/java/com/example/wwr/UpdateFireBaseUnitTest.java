package com.example.wwr;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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


        Mockito.when(mockFirestore.collection("users")).thenReturn(mockCol);
        Mockito.when(mockCol.document(User.getEmail())).thenReturn(mockDoc);
        Mockito.when(mockDoc.set(new HashMap<String, String>())).thenCallRealMethod();
        Mockito.when(mockDoc.get()).thenCallRealMethod();

        UpdateFirebase.setDatabase(mockFirestore);
        UpdateFirebase.setupUser("Name");

        /*mockDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                assertEquals((String)documentSnapshot.get("Name"), "Name");
            }
        });*/
    }
}

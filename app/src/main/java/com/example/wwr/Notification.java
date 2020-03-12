package com.example.wwr;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notification extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        System.err.println("WOOOOOOOOOOOOOOOOO");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            System.err.println("oops");

                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        System.err.println(token);
                        onNewToken(token);
                    }
                });



        //  need to implement this if you want to do something when you receive a notification while app is in the foreground.
    }
    @Override
    public void onNewToken(String token) {
        System.err.println("entered");

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(final String token) {
        FirebaseInstanceId.getInstance().getInstanceId();
    }
}

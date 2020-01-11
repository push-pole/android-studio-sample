package com.pushpole.sample.as.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.pushpole.sdk.PushPole;

public class FirebaseCompanion extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (PushPole.getFcmHandler(this).onMessageReceived(remoteMessage)) {
            // Message is for PushPole
            return;
        }
        super.onMessageReceived(remoteMessage);

        // Handle Firebase message
    }

    @Override
    public void onNewToken(String s) {
        PushPole.getFcmHandler(this).onNewToken(s);
        super.onNewToken(s);

        // Token is refreshed
    }

    @Override
    public void onMessageSent(String s) {
        PushPole.getFcmHandler(this).onMessageSent(s);
        super.onMessageSent(s);

        // Message sent
    }

    @Override
    public void onDeletedMessages() {
        PushPole.getFcmHandler(this).onDeletedMessages();
        super.onDeletedMessages();

        // Message was deleted
    }

    @Override
    public void onSendError(String s, Exception e) {
        PushPole.getFcmHandler(this).onSendError(s, e);
        super.onSendError(s, e);

        // Error sent
    }
}

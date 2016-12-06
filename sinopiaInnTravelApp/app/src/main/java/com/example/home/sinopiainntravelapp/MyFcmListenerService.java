package com.example.home.sinopiainntravelapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Home on 07/11/16.
 */
public class MyFcmListenerService extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    Map data;


    @Override
    public void onMessageReceived(RemoteMessage message) {

        String from = message.getFrom();

         data = message.getData();



        if (message.getData().size() > 0) {


        }


        if (message.getNotification() != null) {


            Intent notifyIntent =  new Intent(this, Activity_CheckIn.class);

            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //notifyIntent.putExtra("NEW MESSAGE",data.get("key1"));
            PendingIntent contentIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            notifyIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle( message.getNotification().getTitle())
                            .setStyle(new NotificationCompat.BigTextStyle().bigText( message.getNotification().getBody()))
                            .setContentText( message.getNotification().getBody())
                            .setContentIntent(contentIntent).setAutoCancel(true);


            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());




        }



    }

    private void sendNotification(RemoteMessage message) {




    }



}
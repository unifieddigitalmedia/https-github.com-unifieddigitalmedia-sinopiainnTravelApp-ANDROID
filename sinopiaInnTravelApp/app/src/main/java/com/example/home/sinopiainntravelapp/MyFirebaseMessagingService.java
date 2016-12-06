package com.example.home.sinopiainntravelapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by Home on 07/11/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    private Random random;


    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {



        if (remoteMessage.getData().size() > 0) {


            if(Fragment_Chats.active) {

                Intent i = new Intent();

                i.setAction("appendChatScreenMsg");

                i.putExtra("message", remoteMessage.getData().get("key1"));

                this.sendBroadcast(i);

            } else {


                if (remoteMessage.getNotification() != null) {

                    sendNotification(remoteMessage);


                }


            }



        }



    }

    private void sendNotification(RemoteMessage message) {

        Intent intent = new Intent(this, Activity_CheckIn.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("message", message.getData().get("key1"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)

                .setSmallIcon(R.drawable.ic_local_see_24dp)
                .setContentTitle( message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());


    }



}
package com.example.home.sinopiainntravelapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Home on 04/07/16.
 */
public class ScaleBitMaps {

    public ScaleBitMaps(Context context) {


    }

    public static int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    /**
     * Created by Home on 07/11/16.
     */
    public static class ChatMessage implements Serializable {

        public String body, sender, receiver, senderName,createdAt;
        public String Date, Time;
        public String msgid;
        public boolean isMine;

        public ChatMessage(String Sender, String Receiver, String messageString,
                           String ID, boolean isMINE,String timestamp,String sendername) {
            body = messageString;
            isMine = isMINE;
            sender = Sender;
            msgid = ID;
            receiver = Receiver;
            senderName = sendername;
            createdAt = timestamp;

        }

        public String getId() {
            return msgid;
        }

        public void setId(String id) {
            this.msgid = id;
        }

        public String getMessage() {
            return body;
        }

        public void setMessage(String message) {
            this.body = message;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String message) {
            this.sender = message;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String message) {
            this.senderName = message;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String message) {
            this.receiver = message;
        }

        public void setMsgID() {

            msgid += "-" + String.format("%02d", new Random().nextInt(100));
            ;
        }


    }


}

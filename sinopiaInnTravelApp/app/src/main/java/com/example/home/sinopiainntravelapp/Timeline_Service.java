package com.example.home.sinopiainntravelapp;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Home on 23/11/2016.
 */

public class Timeline_Service extends IntentService {

String name;
    ArrayList<Bitmap> bitmapArray ;
    ArrayList<String> guestbitmapArray;
    ArrayList<String> guestStringArray;
    JSONArray files ;

    JSONArray timelinefiles ;

    ArrayList<String> reservationsContainer ;

    public Timeline_Service() {
        super("Timeline_Service");
    }

    /*
    public Timeline_Service(String name) {

        super(name);
    }
*/
    @Override
    protected void onHandleIntent(Intent workIntent) {


        name = workIntent.getStringExtra("name");

        bitmapArray =  new  ArrayList<>();

        guestbitmapArray =  new  ArrayList<>();

        guestStringArray = new  ArrayList<>();
        reservationsContainer = new ArrayList<>();


        Thread getGuestFiles = new Thread(new Runnable() {

            public void run() {

                SyncHttpClient client = new SyncHttpClient();

                client.setConnectTimeout(20000);

                StringBuilder builder = new StringBuilder();

                String WEB_SERVICE_URL = null;

                try {

                    WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/api/timeline?name=").append(URLEncoder.encode(name, "utf-8")).toString();


                    Log.i("WEB_SERVICE_URL",WEB_SERVICE_URL);

                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();

                }


                client.get(WEB_SERVICE_URL, new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {



                       timelinefiles = json;


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {
                        // Handle the failure and alert the user to retry
                        Log.e("ERROR", e.toString());
                    }


                });



            }
        });

        getGuestFiles.start();

        try {


            getGuestFiles.join();


        } catch (InterruptedException e) {


            e.printStackTrace();


        }

/*ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
bitmapArray.add(myBitMap); // Add a bitmap
bitmapArray.get(0);*/
        Thread getGuestBitmaps = new Thread(new Runnable() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            public void run() {

                SyncHttpClient client = new SyncHttpClient();

                try {

                    for(int x = 0; x < timelinefiles.length(); x++) {

                        JSONObject reservation = (JSONObject) timelinefiles.get(x);

                        //JSONArray PHOTOS = reservation.getJSONArray("images");

                        JSONArray PHOTOS = reservation.getJSONArray("images");


                       guestbitmapArray = new ArrayList<>();

                        for(int l = 0; l < PHOTOS.length(); l++) {



                            JSONObject reservation_image = (JSONObject) PHOTOS.get(l);

                            client.setConnectTimeout(20000);

                            StringBuilder builder = new StringBuilder();

                            String WEB_SERVICE_URL = reservation_image.get("image_url").toString();

                           guestbitmapArray.add(WEB_SERVICE_URL);

                           guestStringArray.add((String) reservation_image.get("text"));



                        }


                        reservation.put("BITMAPS",guestbitmapArray.toString());
                        reservation.put("DESCRIPTIONS",guestStringArray.toString());
                        reservationsContainer.add(reservation.toString());



                    }



                    Intent checkinIntent = new Intent(getBaseContext(), Activity_CheckIn.class);
                    checkinIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    checkinIntent.putExtra("chat",false);
                    checkinIntent.putStringArrayListExtra("timeline",reservationsContainer);
                    getApplication().startActivity(checkinIntent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

        getGuestBitmaps.start();



    }

}


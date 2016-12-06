package com.example.home.sinopiainntravelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

public class Activity_Main extends AppCompatActivity {

    ScaleBitMaps bitmapClass ;
    ImageView imgView;
    FrameLayout frmLayout;
    Button checkInButton;
    Button reservationButton;
    public static final String PREFS_NAME = "checkinToken";
    public static final String PREFS_NAME_1 = "guestName";
    public static Boolean guest = false;
    SharedPreferences settings;
    public static String token ;
    static Activity_Main mainActivity;
    static String guestName;




    public static Activity_Main getInstance(){
        return   mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainActivity = this;



        imgView = (ImageView) findViewById(R.id.backgrounImage);

        frmLayout = (FrameLayout) findViewById(R.id.frameLayout);

        checkInButton = (Button) findViewById(R.id.gotocheckin);

        settings = getSharedPreferences(PREFS_NAME, 0);



        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(settings.getString("token","").matches("") ){

                    Intent intent = new Intent(getBaseContext(), Activity_Login.class);

                    startActivity(intent);

                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                }else {

                    String WEB_SERVICE_URL = "http://www.sinopiainn.com/api/login";

                    AsyncHttpClient client = new AsyncHttpClient();

                    client.setConnectTimeout(20000);
                    client.get(WEB_SERVICE_URL, new JsonHttpResponseHandler() {

                        @Override
                        public void onStart() {
                            // called before request is started
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                            String guestName = null;

                            Boolean guest = false;

                            for (int l = 0; l < json.length(); l++) {


                                JSONObject booking = null;



                                try {

                                    booking = (JSONObject) json.get(l);

                                    if (booking.getString("token").equals(settings.getString("token", "") ))

                                    {

                                        guest = true;

                                        StringBuilder fullname = new StringBuilder();

                                        fullname.append(booking.getString("fname"));

                                        fullname.append(" ");

                                        fullname.append(booking.getString("lname"));

                                        guestName = fullname.toString();



                                    }


                                } catch (JSONException e) {

                                    e.printStackTrace();


                                }


                            }

                            if (guest) {


                                verifyGuest(guestName);


                            } else {

                                loginGuest();

                            }



                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {
                            // Handle the failure and alert the user to retry
                            Log.e("ERROR", e.toString());
                        }
                        @Override
                        public void onRetry(int retryNo) {
                            // called when request is retried
                        }
                    });


                }




            }
        });


        reservationButton = (Button) findViewById(R.id.gotobooking);

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getBaseContext(), Activity_Home.class);

                startActivity(intent);

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        });

        imgView.setImageResource(R.drawable.main);

        bitmapClass = new ScaleBitMaps(this);


    }

    @Override
    public void onResume(){
        super.onResume();


    }

    @NonNull
    public void verifyGuest(String guestName) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME_1, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("c", guestName);
        editor.commit();


        Intent mServiceIntent = new Intent(this, Timeline_Service.class);

        mServiceIntent.putExtra("name", guestName);

        startService(mServiceIntent);



        //Intent intent = new Intent(getBaseContext(), Activity_CheckIn.class);

        //startActivity(intent);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

    public void loginGuest() {

        Intent intent = new Intent(getBaseContext(), Activity_Login.class);

        startActivity(intent);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }



}

package com.example.home.sinopiainntravelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import layout.Fragment_Error_Logging;

public class Activity_Main extends AppCompatActivity {

    ScaleBitMaps bitmapClass ;
    ImageView imgView;
    CoordinatorLayout frmLayout;
    Button checkInButton;
    Button reservationButton;
    public static final String PREFS_NAME = "checkinToken";
    public static final String PREFS_NAME_1 = "guestName";
    public static Boolean guest = false;
    SharedPreferences settings;
    public static String token ;
    static Activity_Main mainActivity;
    static String guestName,guestEmail;
    NetworkInfo activeNetworkInfo;
    private BottomSheetBehavior mBottomSheetBehavior;


    public static Activity_Main getInstance(){
        return   mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainActivity = this;

        imgView = (ImageView) findViewById(R.id.backgrounImage);

        frmLayout = (CoordinatorLayout) findViewById(R.id.frameLayout);

        checkInButton = (Button) findViewById(R.id.gotocheckin);

        settings = getSharedPreferences(PREFS_NAME, 0);

        Log.i("WEB_SERVICE_URL",settings.getString("token", ""));

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();


        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){





        }  else {


            BottomSheetDialogFragment bottomSheetDialogFragment = new Fragment_Error_Logging();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        }





        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){




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

                            String guestEmail = null;

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

                                        guestEmail = booking.getString("email");



                                    }


                                } catch (JSONException e) {

                                    e.printStackTrace();


                                }


                            }

                            if (guest) {


                                verifyGuest(guestEmail);


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



}else {


    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
}


            }
        });


        reservationButton = (Button) findViewById(R.id.gotobooking);

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){


                    settings.edit().clear().apply();

                   // settings.edit().putString("token"," ");

                    Log.i("AFTER_CONTINUE", settings.getString("token","") );

                    Intent intent = new Intent(getBaseContext(), Activity_Home.class);

                    startActivity(intent);

                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }else {


                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


                }

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


        Log.i("VERIFIYING GUEST","VERIFIYING GUEST");


        SharedPreferences settings = getSharedPreferences(PREFS_NAME_1, 0);

        SharedPreferences.Editor editor = settings.edit();

        editor.putString("c",guestName);

        editor.commit();

        Log.i("WEB_SERVICE_URL",guestName);

        Intent mServiceIntent = new Intent(this,Timeline_Service.class);

        mServiceIntent.putExtra("email",guestName);

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

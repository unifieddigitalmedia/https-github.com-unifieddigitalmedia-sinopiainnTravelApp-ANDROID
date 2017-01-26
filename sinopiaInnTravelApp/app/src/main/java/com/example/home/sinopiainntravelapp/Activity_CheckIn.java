package com.example.home.sinopiainntravelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

public class Activity_CheckIn extends AppCompatActivity {
    String[] menu = {
            "Our Villa", "Local Time", "Local Weather", "Menu","Plan A Trip","Bookshelf","Guest Book","Channels"


    };

    public static final String PREFS_NAME = "checkinToken";

    static  String mCurrentPhotoPath;

    private static final int REQUEST_CODE = 0;

    JSONArray Jsonroomlist;

    JSONArray Jsonamenitylist;

    JSONArray Jsonofferlist;

    JSONArray JsonBusinesses;

    JSONArray itinerary;

    ArrayList<String> businessTypeList;

    ArrayList<String> amenititesTypeList;

    private FrameLayout mViewPager;

    static final int REQUEST_IMAGE_CAPTURE = 2;

    ImageView overlay_close;

    ArrayFilter businessFilter ;

    private BottomSheetBehavior mBottomSheetBehavior;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    CoordinatorLayout.Behavior behavior;
    Bundle bundle;
    CircularProgressView progressView;

    RelativeLayout overlay;

    public static final String PREFS_NAME_1 = "checkinToken";

    SharedPreferences settings;

    ArrayList<String> guestbitmapArray;
    ArrayList<String> guestStringArray;

    JSONArray timelinefiles ;

    ArrayList<String> reservationsContainer ;
    CoordinatorLayout relativeLayout;
    Thread getBitmaps;
    Thread getGuestBitmaps;

    static Activity_CheckIn checkinActivity;
    static public int placePos = 0;
    AsyncHttpClient client;

    String fromdate;

    String location;

    String message;

    String todate;
    String type = "image/*";

File media;

    String name;
    String fname;
    String lname;

    String registrationToken;
Random random;
Bitmap bitmap;
    ArrayList<Bitmap> menubitmapArray = null;
    final ArrayList<Bitmap> bookbitmapArray = null;
    java.util.concurrent.ExecutorService executor ;
    JSONArray files ;
    ArrayList<Bitmap> bitmapArray ;
    public static Activity_CheckIn getInstance(){
        return   checkinActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        StringBuilder builder = new StringBuilder();

        settings = getSharedPreferences(PREFS_NAME_1, 0);

        name = builder.append(settings.getString("fname","")).append(" ").append(settings.getString("lname","")).toString();

        Log.i("name",name);

        fname= settings.getString("fname","");

        lname= settings.getString("lname","");

        random = new Random();

        guestbitmapArray =  new  ArrayList<>();

        guestStringArray = new  ArrayList<>();

        reservationsContainer = new ArrayList<>();

        progressView = (CircularProgressView) findViewById(R.id.progress_view);

        overlay = (RelativeLayout) findViewById(R.id.overlay);

        overlay.setVisibility(View.GONE);

        itinerary = new JSONArray();

        bundle = new Bundle();

        Fragment_Confirmation openingFragment = new Fragment_Confirmation();

        bundle.putString("fname",settings.getString("fname",""));

        bundle.putString("lname",settings.getString("lname",""));

        bundle.putString("fromdate",settings.getString("fromdate",""));

        bundle.putString("todate",settings.getString("todate", ""));

        //files = json;

        openingFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container,openingFragment,"confirmed").addToBackStack(null).commit();

        checkinActivity = this;

        client = new AsyncHttpClient();

        mViewPager = (FrameLayout) findViewById(R.id.container);



        executor = Executors.newSingleThreadExecutor();

        bitmapArray =  new  ArrayList<>();

        checkguestIn();


        fromdate = settings.getString("fromdate", "");

        todate = settings.getString("todate", "");

        relativeLayout = (CoordinatorLayout) findViewById(R.id.frameLayout);

        reservationsContainer = getIntent().getStringArrayListExtra("timeline");

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_logo), 0, true);

        tabLayout.addTab(tabLayout.newTab().setText("Trips").setIcon(R.drawable.ic_timeline_black_24dp), 1, false);

        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.ic_local_see_24dp), 2, false);

        tabLayout.addTab(tabLayout.newTab().setText("Guide").setIcon(R.drawable.ic_card_travel_black_24dp), 3, false);

        tabLayout.addTab(tabLayout.newTab().setText("Help").setIcon(R.drawable.ic_help_24dp), 4, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            tabLayout.getTabAt(0).getIcon().setTint(Color.argb(999,153,153,51));
            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
            tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(android.R.color.white));
            tabLayout.getTabAt(4).getIcon().setTint(getResources().getColor(android.R.color.white));
        }


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {
                    case 0:


                           getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Confirmation()).addToBackStack(null).commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            tabLayout.getTabAt(0).getIcon().setTint(Color.argb(999,153,153,51));
                            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(4).getIcon().setTint(getResources().getColor(android.R.color.white));



                        }



                        break;

                    case 1:



                            goToTimeline();



                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(1).getIcon().setTint(Color.argb(999,153,153,51));
                            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(4).getIcon().setTint(getResources().getColor(android.R.color.white));

                        }


                        break;

                    case 2:


                        dispatchTakePictureIntent();



                        break;

                    case 3:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Travel_Tips()).addToBackStack(null).commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(3).getIcon().setTint(Color.argb(999,153,153,51));
                            tabLayout.getTabAt(4).getIcon().setTint(getResources().getColor(android.R.color.white));

                        }


                        break;


                    case 4:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Contact_Page()).addToBackStack(null).commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(4).getIcon().setTint(Color.argb(999,153,153,51));

                        }

                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


                if (tab.getPosition() == 0)


                {


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Confirmation()).addToBackStack(null).commit();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tabLayout.getTabAt(0).getIcon().setTint(Color.argb(999,153,153,51));
                        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(4).getIcon().setTint(getResources().getColor(android.R.color.white));
                    }


                } else if (tab.getPosition() == 1)


                {


                    if(reservationsContainer.size() == 0 ) {



                        goToTimeline();


                    }else{


                        goToTimeline();


                    }


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(1).getIcon().setTint(Color.argb(999,153,153,51));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(4).getIcon().setTint(getResources().getColor(android.R.color.white));
                    }


                } else if (tab.getPosition() == 2)


                {


                    dispatchTakePictureIntent();



                }else if (tab.getPosition() == 3)


                {


                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Travel_Tips()).addToBackStack(null).commit();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(3).getIcon().setTint(Color.argb(999,153,153,51));
                        tabLayout.getTabAt(4).getIcon().setTint(getResources().getColor(android.R.color.white));
                    }


                }
                else if (tab.getPosition() == 4)


                {


                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Contact_Page()).addToBackStack(null).commit();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(3).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(4).getIcon().setTint(Color.argb(999,153,153,51));
                    }

                }

            }
        });


    }

    private void goToTimeline() {


        if(reservationsContainer.size() == 0){


            //progressView.startAnimation();

            //overlay.setVisibility(View.VISIBLE);

     Snackbar snackbar = Snackbar.make(relativeLayout, "You havent taken any pictures", Snackbar.LENGTH_LONG);

                            snackbar.show();


        } else {


            Fragment_Timeline new_fragment = new Fragment_Timeline();

            Bundle bundle = new Bundle ();

            bundle.putStringArrayList("photo_files", reservationsContainer);

            new_fragment.setArguments(bundle);

            homePageFadeTransition(new_fragment,"");


        }




    }


    @Override
    protected void onStop() {

        super.onStop();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);


        if(!"confirmed".equals(currentFragment.getTag())) {


            finish();


        }
    }

    public void homePageReplaceFragment(Fragment s,ImageView i ) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {


            getSupportFragmentManager().findFragmentById(R.id.container).setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.change_image_transform));

            getSupportFragmentManager().findFragmentById(R.id.container).setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));

            s.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.change_image_transform));

            s.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));

            getSupportFragmentManager().beginTransaction().addSharedElement(i, "mainPic").replace(R.id.container, s ).addToBackStack(null).commit();

        }

        else {


            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, s ).addToBackStack(null).commit();


        }


    }


    public void homePageFadeTransition(Fragment new_fragment,String tag) {


        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new_fragment,tag ).addToBackStack(null).commit();


    }


    public void addRooms(JSONArray roomlist) {

        Jsonroomlist = roomlist;

        getSupportFragmentManager().popBackStack();



    }

    public void addAmenities(JSONArray amenityArray) {

        Jsonamenitylist = amenityArray;

        getSupportFragmentManager().popBackStack();



    }

    public void addBuisnessTypes(ArrayList<String> newList, int fragment) {

        if(fragment == 0){

            businessTypeList = newList;

            getSupportFragmentManager().popBackStack();


        }

    }

    public void addBuisnessServices(ArrayList<String> jsonroomlist, JSONArray business) {

        amenititesTypeList = jsonroomlist;

        businessFilter = new ArrayFilter();

        try {
            JsonBusinesses = businessFilter.businessfilterList(business,jsonroomlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().popBackStack();

    }



    public void goToWeb (String url) {



        Fragment_Web new_fragment = new Fragment_Web();

        Bundle bundle1 = new Bundle();

        bundle1.putString("url",url);

        new_fragment.setArguments(bundle1);

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new_fragment ).addToBackStack(null).commit();




    }


    public void onBraintreeSubmit() {

        //Log.i();
        RequestParams params = new RequestParams();

        try {


        for(int x = 0; x < itinerary.length() ; x++) {


                params.put("places[]", String.valueOf(itinerary.get(x)));


        }

        } catch (JSONException e) {


            e.printStackTrace();

        }

        params.put("token",settings.getString("token", ""));

        params.put("name",name);

        client = new AsyncHttpClient();

        client.setConnectTimeout(20000);

        client.post("/api/mobile/booktrip/", params , new JsonHttpResponseHandler() {

            @Override
            public void onStart() {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {


                Snackbar snackbar = null;

                try {

                    snackbar = Snackbar.make(relativeLayout, String.valueOf(json.get("ERROR")), Snackbar.LENGTH_LONG);

                    snackbar.show();

                } catch (JSONException e) {

                    e.printStackTrace();

                }




            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {


                Snackbar snackbar = Snackbar.make(relativeLayout, "Processing your payment", Snackbar.LENGTH_LONG);

                snackbar.show();

            }

            @Override
            public void onRetry(int retryNo) {


            }


        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


            if (requestCode == REQUEST_CODE) {


            if (resultCode == BraintreePaymentActivity.RESULT_OK) {



                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE );

                String nonce = paymentMethodNonce.getNonce();




            }

        }else if (requestCode == 2 && resultCode == RESULT_OK){


                galleryAddPic();




                new Handler().post(new Runnable() {

                    public void run() {


                        Fragment_Photos_Big new_fragment = new Fragment_Photos_Big();

                        Bundle bundle1 = new Bundle();

                        bundle1.putString("image_path",mCurrentPhotoPath);

                        bundle1.putString("tab","2");

                        new_fragment.setArguments(bundle1);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new_fragment ).addToBackStack(null).commit();

                    }
                });

            }

    }




    public void selectMenuItem (final int position, String web_url) {


        client = new AsyncHttpClient();

        client.setConnectTimeout(20000);

        client.get(web_url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray json) {

                switch (position) {

                    case 0:



                        break;

                    case 1:




                    case 2:



                        break;
                    case 3:



                        Fragment_Food new_fragment = new Fragment_Food();

                        bundle.putInt("Activity", 1);

                        bundle.putString("Json", json.toString());

                        bundle.putInt("Menu", position);

                        new_fragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,new_fragment).addToBackStack(null).commit();


                      /*  menubitmapArray  = new ArrayList<>();

                        new Thread() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public void run() {


                        for (int i = 0; i < json.length(); i++) {


                            JSONObject item = null;
                            try {
                                item = (JSONObject) json.get(i);

                                final JSONObject firstItem  =  (JSONObject) item.getJSONArray("items").get(0);

                                try {

                                    try ( InputStream is = new URL(firstItem.getString("image_url").replaceAll(" ", "%20")).openStream() ) {

                                        final Bitmap bitmap = BitmapFactory.decodeStream( is );

                                        menubitmapArray.add(bitmap);




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } catch (IOException e) {e.printStackTrace();


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }









                                }



                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {


                                        Fragment_Photos new_fragment =  new Fragment_Photos() ;

                                        Bundle bundle = new Bundle ();

                                        bundle.putInt("Activity", 1);

                                        bundle.putInt("Menu", position);

                                        bundle.putString("Json", json.toString());

                                        new_fragment.setArguments(bundle);

                                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,new_fragment).addToBackStack(null).commit();


                                    }
                                });

                        }

                        }.start();

*/




                        break;
                    case 4:


                        Fragment_Location_List travel = new Fragment_Location_List();

                        Bundle bundle1 = new Bundle();

                        String jsonString = "[{\"name\":\"Portland\",\"description\":\"Even though it is a quiet and beautiful haven, Portland isn’t only visited for its charm. If you’ve got an eye for arts and crafts, and all the jewels Jamaica has to offer.\",\"image_url\":\"portland\",\"location\":\"portland\"},{\"name\":\"Ocho Rios\",\"description\":\"From exquisite luxury plazas, filled with world-class brands, to traditional stalls and stores, where one can uncover all kinds of charming mementos.\",\"image_url\":\"ocho_rios\",\"location\":\"ocho rios\"},{\"name\":\"Kingston\",\"description\":\"Kingston is one-of-a-kind. A busy cosmopolitan, half exotic jungle, bursting with sunshine, and half thriving business. With plenty to see and do.\",\"image_url\":\"ocho_rios\",\"location\":\"kingston\"},{\"name\":\"Montego Bay & Negril\",\"description\":\"Montego Bay & Negril's  white, sandy beaches, can offer you a perfectly long, lazy day, soaking up the sun  under deep blue skies.\",\"image_url\":\"negril\",\"location\":\"montego bay\"}]";

                        bundle1.putString("List",jsonString);

                        bundle1.putInt("Activity", 1);

                        bundle1.putInt("Menu",4);

                        travel.setArguments(bundle1);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,travel).addToBackStack(null).commit();


                       /* JsonBusinesses = json;

                        Fragment_Travel_Planner travel =  new Fragment_Travel_Planner() ;

                        bundle.putInt("Activity", 1);

                        bundle.putInt("Menu", position);

                        travel.setArguments(bundle);


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container,travel).addToBackStack(null).commit();

*/
                        break;
                    case 5:

                       /* menubitmapArray  = new ArrayList<>();

                        new Thread() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public void run() {


                                for (int i = 0; i < json.length(); i++) {


                                    JSONObject item = null;
                                    try {
                                        item = (JSONObject) json.get(i);

                                        final JSONObject firstItem  =  (JSONObject) item.getJSONArray("items").get(0);

                                        try {

                                            try ( InputStream is = new URL(firstItem.getString("image_url").replaceAll(" ", "%20")).openStream() ) {

                                                final Bitmap bitmap = BitmapFactory.decodeStream( is );

                                                menubitmapArray.add(bitmap);




                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } catch (IOException e) {e.printStackTrace();


                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }









                                }



                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {


                                        Fragment_Photos new_fragment =  new Fragment_Photos() ;

                                        Bundle bundle = new Bundle ();

                                        bundle.putInt("Activity", 1);

                                        bundle.putInt("Menu", position);

                                        bundle.putString("Json", json.toString());

                                        new_fragment.setArguments(bundle);

                                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,new_fragment).addToBackStack(null).commit();


                                    }
                                });

                            }

                        }.start();*/

                        new_fragment = new Fragment_Food();

                        bundle.putInt("Activity", 1);

                        bundle.putString("Json", json.toString());

                        bundle.putInt("Menu", position);

                        new_fragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,new_fragment).addToBackStack(null).commit();




                       /* Fragment_Photos bookShelf =  new Fragment_Photos() ;

                        bundle.putInt("Activity", 1);

                        bundle.putInt("Menu", position);

                        Log.i("Json",json.toString());
                        bundle.putString("Json",  json.toString());

                        bookShelf.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, bookShelf).addToBackStack(null).commit();
*/


                        break;

                    case 6:


                        Fragment_Food guestBook =  new Fragment_Food() ;

                        bundle.putInt("Activity", 1);

                        bundle.putInt("Menu", position);

                        bundle.putString("Json","[{\"name\":\"Camel\",\"date\":\"20-04-2016\",\"rating\":\"3\",\"comment\":\"The Vette Kat Harbour Bed & Breakfast has two distinct competitive edges that differentiates it from the competition.  The first is the never-ending attention to detail and customer service.  The St. Lucia's recognize that their mission is to ensure that their customers have the finest stay with them.  Both Kayman and Jenné will do whatever it takes to ensure the customer's happiness.  This will be showcased in breakfast which will offer Starbucks Authorized and Certified Training System of Coffee and Tazo Tea service.\"},{\"name\":\"puce\",\"date\":\"20-04-2016\",\"rating\":\"4\",\"comment\":\"test comment\"},{\"name\":\"Test\",\"date\":\"20-04-2016\",\"rating\":\"4\",\"comment\":\"test comment\"}]");

                        guestBook.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, guestBook).addToBackStack(null).commit();


                        break;

                    case 7:




                        break;


                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {


                    Snackbar snackbar = null;

                    snackbar = Snackbar
                            .make(relativeLayout, "One moment please" , Snackbar.LENGTH_LONG);
                    snackbar.show();




            }
            @Override
            public void onRetry(int retryNo) {

            }
        });





    }

    public void tripPlanner(final String location) {

        final JSONArray businessLocation = null;


        String WEB_SERVICE_URL = "http://www.sinopiainn.com/api/businesses";

        client = new AsyncHttpClient();

        client.setConnectTimeout(20000);

        client.get(WEB_SERVICE_URL, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                new MyAsyncTask(json,location).execute();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {


            }
            @Override
            public void onRetry(int retryNo) {

            }
        });


    }


    public Boolean checkItinerary(String businessname) throws JSONException {


        for(int i = 0; i < itinerary.length(); i++) {


            JSONObject f = (JSONObject) itinerary.get(i);

            if(f.get("businessname").toString().matches(businessname)){

                placePos = i;
                return true;

            }


        }

        return false;

    }

    @Override
    public void onBackPressed() {



        getSupportFragmentManager().popBackStack();


    }


    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {

            File photoFile = null;

            try {

                photoFile = createImageFile();

            } catch (IOException ex) {


            }


            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {


        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
       media = new File(mCurrentPhotoPath);



        Uri contentUri = Uri.fromFile(media);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);


    }


    public void goTochat() {



        SharedPreferences.Editor editor = settings.edit();

        editor.putString("registration_token", FirebaseInstanceId.getInstance().getToken());

        Fragment_Chats new_fragment = new Fragment_Chats();

        Bundle bundle = new Bundle();

        bundle.putString("registration_token",FirebaseInstanceId.getInstance().getToken());

        StringBuilder builder = new StringBuilder();

        bundle.putString("name", builder.append(settings.getString("fname", "")).append(" ").append(settings.getString("lname", "")).toString());

        bundle.putString("email","");

        homePageFadeTransition(new_fragment,"chat");

    }

    @Override
    protected void onNewIntent(Intent intent) {


        super.onNewIntent(intent);

        setIntent(intent);

                if( intent.getBooleanExtra("chat",false) ){


                    Fragment_Chats new_fragment = new Fragment_Chats();

                    Bundle bundle = new Bundle();

                    bundle.putString("message",intent.getExtras().getString("message"));

                    homePageFadeTransition(new_fragment,"chat");
                }else{


                    reservationsContainer = intent.getStringArrayListExtra("timeline");


                }




    }

    public void onRestart() {

        super.onRestart();
    }



    public void onStart() {

        super.onStart();
    }


    public void onResume() {

        super.onResume();
    }


    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }




    private class MyAsyncTask extends AsyncTask<Object, Object, JSONArray>

    {


        JSONArray businesses = null;

        String businesslocation = null;

        JSONArray businessLocation  = new JSONArray();


        public MyAsyncTask(JSONArray json, String location) {

            businesses = json;

            businesslocation = location;


        }


        @Override
        protected JSONArray doInBackground(Object... params) {


            try {



                for(int i = 0 ; i <  businesses.length(); i++ ) {


                    final JSONObject businessObject  = (JSONObject)  businesses.get(i) ;


                    if(businessObject.getString("location").equals(businesslocation) ){


                        businessLocation.put(businessObject);



                    }



                }

                /// JsonBusinesses = json;

            } catch (JSONException e) {
                e.printStackTrace();
            }



            return businessLocation;
        }
        @Override
        protected void onPostExecute(JSONArray result) {


            Fragment_Travel_Planner travel = new Fragment_Travel_Planner();

            bundle.putInt("Activity", 1);

            JsonBusinesses = result;

            bundle.putString("Json", result.toString());

            travel.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, travel ).addToBackStack(null).commit();



        }
    }
    public void uploadImageToTimeline(String msg, String address) {



        client = new AsyncHttpClient();

        client.setConnectTimeout(10000000);


        RequestParams params = new RequestParams();

Log.i("media", String.valueOf(media));

            if (media != null) {

                try {
                    params.put("displayImage", media);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }




        StringBuilder builder = new StringBuilder();

        String WEB_SERVICE_URL = null;
        try {


            name = name.replaceFirst("\\s++$", "");


            WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/api/upload-reservation-photo?resID=").append(settings.getString("reservationID", "")).append("&name=").append(URLEncoder.encode(name, "utf-8")).append("&message=").append(URLEncoder.encode(msg, "utf-8")).toString();

            Log.i("WEB_SERVICE_URL",WEB_SERVICE_URL);

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

try {

    client.post(WEB_SERVICE_URL, params, new JsonHttpResponseHandler() {

        @Override
        public void onStart() {


        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

            Log.i("params", String.valueOf(json));

            getSupportFragmentManager().popBackStack();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType(type);


            Uri uri = Uri.fromFile(media);


            //shareIntent.putExtra(Intent.EXTRA_STREAM, uri);


            //startActivity(Intent.createChooser(share, "Share to"));
            try {
                if(json.get("ERROR").toString().equals(""))
                {

                    try {

                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media
                                .insertImage(getContentResolver(), media.getAbsolutePath(), "test", "test")));

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    }

                    shareIntent.setType("image/jpeg");

                    startActivity(Intent.createChooser(shareIntent,"Share to"));

                }else{

                    Snackbar snackbar = null;

                    snackbar = Snackbar
                            .make(relativeLayout, json.get("ERROR").toString() , Snackbar.LENGTH_LONG);
                    snackbar.show();



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {


            Snackbar snackbar = null;

            snackbar = Snackbar
                    .make(relativeLayout, "One moment please", Snackbar.LENGTH_LONG);
            snackbar.show();


        }

        @Override
        public void onRetry(int retryNo) {


        }

    });

} catch (Exception e) {
    e.printStackTrace();

}
    }



    private void checkguestIn() {




        //java.util.concurrent.ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {

            @Override
            public void run() {

                SyncHttpClient client = new SyncHttpClient();

                client.setConnectTimeout(20000);

                client.get("http://www.sinopiainn.com/api/images/", new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {


                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                        System.err.println("files got");


                        files = json;


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {


                        System.err.println("files terminated");

                    }


                });

            }

        });


        //executor = Executors.newSingleThreadExecutor();

        executor.submit(new Runnable() {


            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void run() {

                System.err.println("starting");



                try {


                    SyncHttpClient client = new SyncHttpClient();

                    for (int l = 0; l < files.length(); l++) {

                        JSONObject item = (JSONObject) files.get(l);


                        StringBuilder builder = new StringBuilder();

                        String WEB_SERVICE_URL = null;

                            WEB_SERVICE_URL = item.get("image_url").toString();

                            Log.i("WEB_SERVICE_URL",WEB_SERVICE_URL);




                        try {

                            try (InputStream is = new URL(WEB_SERVICE_URL).openStream()) {

                                final Bitmap bitmap = BitmapFactory.decodeStream(is);

                                bitmapArray.add(bitmap);

                                System.err.println("bitmapArray");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });

        executor.shutdown();


    }

}

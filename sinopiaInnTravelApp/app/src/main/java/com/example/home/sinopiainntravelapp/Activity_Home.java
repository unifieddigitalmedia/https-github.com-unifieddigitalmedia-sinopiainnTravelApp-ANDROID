package com.example.home.sinopiainntravelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.LineItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class Activity_Home extends AppCompatActivity {


    public static final String PREFS_NAME = "checkinToken";

    private static final int REQUEST_CODE = 0;

    static JSONArray Jsonroomlist = null;

    static JSONArray Jsonamenitylist = null;

    static JSONArray Jsonofferlist = null;

    static JSONArray JsonBusinesses = null;

    static ArrayList<String> businessTypeList = null;

    static ArrayList<String> amenititesTypeList = null;

    JSONArray itinerary;

    static int numf_of_guest = 0;

    static int num_of_days = 0 ;

    static int num_of_adults = 0 ;

    static int num_of_children = 0 ;

    static int num_of_infants = 0 ;




    static Activity_Home homeActivity;



    Bundle bundle;



    public static Activity_Home getInstance(){
        return   homeActivity;
    }

    public String clientToken;

    AsyncHttpClient client;

    JSONArray jsonRooms;

    JSONArray jsonAmenities;

    public static String fromdate;

    public static String todate;

    public static String name;

    public static String phone;

    public static String email;

    public static String photofile;

    public static double totalDiscount ;

    public static double taxAmount ;

    static Double reservation_total ;

    public Double reservation_sub_total ;

    static public int placePos ;


    RelativeLayout relativeLayout;

    SharedPreferences settings;

    SharedPreferences.Editor editor;

    File f;

    CircularProgressView progressView;

    RelativeLayout overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        numf_of_guest = 1;

         num_of_adults = 1 ;

        num_of_children = 0 ;

        num_of_infants = 0 ;

       totalDiscount = 0.00;

         taxAmount = 0.00;

        reservation_total = 0.00;

        reservation_sub_total = 0.00;

       placePos = 0;

        client = new AsyncHttpClient();

        client.setConnectTimeout(100000);

        homeActivity = this;

        bundle = new Bundle();

        Jsonroomlist = new JSONArray();

        Jsonamenitylist = new JSONArray();

        Jsonofferlist = new JSONArray();

        JsonBusinesses = new JSONArray();

        itinerary = new JSONArray();

        jsonRooms = new JSONArray();

        jsonAmenities = new JSONArray();

        businessTypeList = new ArrayList<String>();

        amenititesTypeList = new ArrayList<String>();

        settings = getSharedPreferences(PREFS_NAME, 0);

        editor = settings.edit();

        progressView = (CircularProgressView) findViewById(R.id.progress_view);

        overlay = (RelativeLayout) findViewById(R.id.overlay);

        overlay.setVisibility(View.GONE);


        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLay);

        FrameLayout mViewPager = (FrameLayout) findViewById(R.id.container);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Reception").setIcon(R.drawable.ic_logo), 0, true);

        tabLayout.addTab(tabLayout.newTab().setText("Guide").setIcon(R.drawable.ic_map_24dp), 1, false);

        tabLayout.addTab(tabLayout.newTab().setText("Help").setIcon(R.drawable.ic_help_24dp), 2, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.getTabAt(1).getIcon().setTint(Color.argb(999,153,153,51));
            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
        }

        getSupportFragmentManager().beginTransaction().add(R.id.container, new Fragment_HomePage()).addToBackStack(null).commit();



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {
                    case 0:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_HomePage()).addToBackStack(null).commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tabLayout.getTabAt(0).getIcon().setTint(Color.argb(999,153,153,51));
                            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                        }



                        break;

                    case 1:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Travel_Tips()).addToBackStack(null).commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(1).getIcon().setTint(Color.argb(999,153,153,51));
                            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                        }


                        break;

                    case 2:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Contact_Page()).addToBackStack(null).commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                            tabLayout.getTabAt(2).getIcon().setTint(Color.argb(999,153,153,51));
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

                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_HomePage()).addToBackStack(null).commit();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tabLayout.getTabAt(0).getIcon().setTint(Color.argb(999,153,153,51));
                        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                    }


                } else if (tab.getPosition() == 1)


                {


                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Travel_Tips()).addToBackStack(null).commit();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(1).getIcon().setTint(Color.argb(999,153,153,51));
                        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
                    }


                } else if (tab.getPosition() == 2)


                {


                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Contact_Page()).addToBackStack(null).commit();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
                        tabLayout.getTabAt(2).getIcon().setTint(Color.argb(999,153,153,51));
                    }

                }


            }
        });



    }

    public void gotoSubMenuPage(int position) {




        switch (position) {
            case 0:

                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,  new Fragment_Reservation()).addToBackStack(null).commit();

                break;

            case 1:

                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,  new Fragment_Reservation()).addToBackStack(null).commit();

                break;

            case 2:


                client.setConnectTimeout(20000);

                client.get("http://www.sinopiainn.com/api/menu/", new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {


                        Fragment_Food new_fragment = new Fragment_Food();

                        bundle.putInt("Activity", 0);

                        bundle.putString("Json", json.toString());

                        new_fragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,new_fragment).addToBackStack(null).commit();



                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {

                    }
                    @Override
                    public void onRetry(int retryNo) {

                    }
                });


                break;

            case 3:


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


                         JsonBusinesses = json;

                        Fragment_Travel_Planner travel = new Fragment_Travel_Planner();

                        bundle.putInt("Activity", 0);

                        bundle.putString("Json", json.toString());

                        travel.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, travel ).addToBackStack(null).commit();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {


                    }
                    @Override
                    public void onRetry(int retryNo) {

                    }
                });


                break;

        }




    }



    public void homePageReplaceFragment(Fragment s,ImageView i ) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getSupportFragmentManager().findFragmentById(R.id.container).setSharedElementReturnTransition(TransitionInflater.from(
                    this).inflateTransition(R.transition.change_image_transform));


            getSupportFragmentManager().findFragmentById(R.id.container).setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));

            s.setSharedElementEnterTransition(TransitionInflater.from(
                    this).inflateTransition(R.transition.change_image_transform));
            s.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));

            getSupportFragmentManager().beginTransaction()
                    .addSharedElement(i, "mainPic").replace(R.id.container, s ).addToBackStack(null).commit();

        }
        else
        {

            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, s ).addToBackStack(null).commit();
        }


    }


    @Override
    public void onBackPressed() {



        getSupportFragmentManager().popBackStack();


    }

    public void homePageFadeTransition(Fragment fragment, String tag) {


  getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,fragment,tag).addToBackStack(null).commit();


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

        ArrayFilter businessFilter = new ArrayFilter();


        try {

            JsonBusinesses = businessFilter.businessfilterList(business,jsonroomlist);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        getSupportFragmentManager().popBackStack();



    }



    public void setNumOfGuest(int itemAtPosition) {

        numf_of_guest = itemAtPosition;

    }

    public void setNumbOfDays (Date fromdate, Date todate){


        num_of_days = (int) (( todate.getTime() - fromdate.getTime())/ (1000 * 60 * 60 * 24));




    }

    public String calDiscountAmount(double amount) {


        String amt = String.format("%.2f",(reservation_total * amount));

        reservation_total = reservation_total - Double.parseDouble(amt);

        totalDiscount = totalDiscount + Double.parseDouble(amt);

        return amt;


    }

    public double calTaxAmount() {



        double tax = reservation_total * .25 ;



        reservation_total = reservation_total + tax;

        taxAmount = tax;
        return tax;


    }

    public void setreservationTotal (double amt) {


        reservation_total = reservation_total + amt;



    }


    public void onBraintreeSubmit() {

        RequestParams params = new RequestParams();



            params.put("roomarray[]", Jsonroomlist);
            params.put("fromdate",fromdate);
            params.put("todate",todate);




        client = new AsyncHttpClient();

        client.setConnectTimeout(20000);


        client.get("http://www.sinopiainn.com/api/mobile/checkhotelavailability/", params , new JsonHttpResponseHandler() {

            @Override
            public void onStart() {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                try {

                    if(json.get("ERROR").equals("")){

                        getPaymentnonce();

                    }else{

                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, json.get("ERROR").toString(), Snackbar.LENGTH_LONG);

                        snackbar.show();

                    }
                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {


                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Checking Availability", Snackbar.LENGTH_LONG);

                snackbar.show();


            }

            @Override
            public void onRetry(int retryNo) {


            }

        });


    }



    public void getPaymentnonce() {


        client = new AsyncHttpClient();

        client.setConnectTimeout(20000);

        client.get("http://www.sinopiainn.com/api/checkout/", new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String clientToken) {


                double despoitTotal = ( reservation_total * .50 );


                Cart cart = Cart.newBuilder()
                        .setCurrencyCode("USD")
                        .setTotalPrice(String.format("%.2f",despoitTotal))
                        .addLineItem(LineItem.newBuilder()
                                .setCurrencyCode("USD")
                                .setDescription("Reservation Deposit")
                                .setQuantity("1")
                                .setUnitPrice(String.format("%.2f",despoitTotal))
                                .setTotalPrice(String.format("%.2f",despoitTotal))
                                .build()).build();

                PaymentRequest paymentRequest = new PaymentRequest().clientToken(clientToken);

                paymentRequest.tokenizationKey("sandbox_btkvgtj8_mb3k4ty88wspgh8m").androidPayCart(cart);

                paymentRequest.amount("USD "+ String.format("%.2f",despoitTotal))
                        .primaryDescription("Reservation Deposit")
                        .secondaryDescription(Jsonroomlist.length()+" Rooms From: "+fromdate+" To: "+todate)
                        .submitButtonText("Pay");


                startActivityForResult(paymentRequest.getIntent(Activity_Home.this), REQUEST_CODE);


            }

        });


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {


            Fragment uploadType = getSupportFragmentManager().findFragmentById(R.id.container);

            if (uploadType != null) {


                uploadType.onActivityResult(requestCode, resultCode, data);


            }

        }
        else if (requestCode == REQUEST_CODE) {


            if (resultCode == BraintreePaymentActivity.RESULT_OK) {

                progressView.startAnimation();
                overlay.setVisibility(View.VISIBLE);

                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);

                StringBuilder builder = new StringBuilder();

                String WEB_SERVICE_URL = null;

                WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/api/mobile/payment/?").toString();

                RequestParams params = new RequestParams();



                    if (f != null) {

                        try {
                            params.put("displayImage", f);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                    }



                if (itinerary.length() != 0) {


                    WEB_SERVICE_URL = builder.append("subtotaladmission=").append(String.format("%.2f", subtotaladmission())).append("&subtotalavergae=").append(String.format("%.2f", subtotalavergae())).append("&carhire=").append(String.format("%.2f", carhire())).append("&triptax=").append(String.format("%.2f", tax())).append("&triptotal=").append(String.format("%.2f", triptotal())).append("&tripID=").append("1").toString();


                    try {
                        WEB_SERVICE_URL = builder.append("&places[]=").append(URLEncoder.encode(String.valueOf(itinerary), "utf-8")).toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    reservation_total = reservation_total + triptotal();

                } else {

                    WEB_SERVICE_URL = builder.append("tripID=").append("").toString();

                }


                try {

                    WEB_SERVICE_URL = builder.append("&roomarray[]=").append(URLEncoder.encode(String.valueOf(Jsonroomlist), "utf-8")).append("&amenityarray[]=").append(URLEncoder.encode(String.valueOf(Jsonamenitylist), "utf-8")).append("&offerarray[]=").append(URLEncoder.encode(String.valueOf(Jsonofferlist), "utf-8")).append("&fname=").append(URLEncoder.encode(name, "utf-8")).append("&lname=").append("").append("&phone=").append(phone).append("&email=").append(URLEncoder.encode(email, "utf-8")).append("&expiry=").append("").append("&idtype=").append("").append("&idnumber=").append("").append("&numofdays=").append(num_of_days).append("&guests=").append(numf_of_guest).append("&numofadults=").append(num_of_adults).append("&numofchildren=").append(num_of_adults).append("&numofinfants=").append(num_of_infants).append("&fromdate=").append(fromdate).append("&todate=").append(todate).toString();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                double amenityTotal = (reservation_total - taxAmount) - (reservation_sub_total - totalDiscount);
                double despoitTotal = (reservation_total * .50);


                WEB_SERVICE_URL = builder.append("&subtotal=").append(String.format("%.2f", reservation_sub_total)).append("&discount=").append(String.format("%.2f", totalDiscount)).append("&amenityTotal=").append(String.format("%.2f", amenityTotal)).append("&tax=").append(String.format("%.2f", taxAmount)).append("&deposit=").append(String.format("%.2f", despoitTotal)).append("&total=").append(String.format("%.2f", reservation_total)).toString();


                WEB_SERVICE_URL = builder.append("&payment_method_nonce=").append(paymentMethodNonce.getNonce()).append("&amount=").append(String.format("%.2f", despoitTotal)).toString();




                    client.post(WEB_SERVICE_URL, params, new JsonHttpResponseHandler() {



                        @Override
                        public void onStart() {


                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject json) {


                            progressView.stopAnimation();

                            overlay.setVisibility(View.GONE);

                            try {

                                if (json.get("ERROR").toString().equals("")) {


                                    try {

                                        JSONObject resObj = json.getJSONObject("Reservation");

                                        JSONArray res = resObj.getJSONArray("ops");

                                        resObj = res.getJSONObject(0);


                                        editor.putString("fname", resObj.getString("fname"));
                                        editor.putString("lname", resObj.getString("lname"));
                                        editor.putString("fromdate", resObj.getString("fromdate"));
                                        editor.putString("todate", resObj.getString("todate"));
                                        editor.putString("reservationID",resObj.getString("_id"));


                                    } catch (JSONException e) {

                                        e.printStackTrace();

                                    }


                                    editor.commit();

                                    Intent intent = new Intent(getBaseContext(), Activity_CheckIn.class);
                                    startActivity(intent);


                                } else {


                                    editor.putString("fname", "");
                                    editor.putString("lname", "");
                                    editor.putString("fromdate", "");
                                    editor.putString("todate", "");



                                    Snackbar snackbar = Snackbar
                                            .make(relativeLayout, json.get("ERROR").toString(), Snackbar.LENGTH_LONG);

                                    snackbar.show();


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {

                            progressView.stopAnimation();

                            overlay.setVisibility(View.GONE);

                            Snackbar snackbar = Snackbar
                                    .make(relativeLayout, "Processing your payment", Snackbar.LENGTH_LONG);

                            snackbar.show();

                        }

                        @Override
                        public void onRetry(int retryNo) {


                        }

                    });


                }



        }


    }


    @Override
    protected void onStop() {

        super.onStop();


        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);



        if(!"personal".equals(currentFragment.getTag())) {


            finish();


        }


    }


    private class DownloadFilesTask extends AsyncTask<String, Void, String> {




        @Override
        protected String doInBackground(String... strings) {


            Bitmap imageToSend = BitmapFactory.decodeFile(f.getPath());

            String response = null;

            try

            {
                URL url = new URL("http://www.sinopiainn.com/upload-image");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Cache-Control", "no-cache");

                conn.setReadTimeout(35000);
                conn.setConnectTimeout(35000);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                nameValuePairs.add(new BasicNameValuePair("roomarray[]", String.valueOf(Jsonroomlist)));
                nameValuePairs.add(new BasicNameValuePair("amenityarray[]", String.valueOf(Jsonamenitylist)));
                nameValuePairs.add(new BasicNameValuePair("offerarray[]", String.valueOf(Jsonofferlist)));
                nameValuePairs.add(new BasicNameValuePair("fname", name));
                nameValuePairs.add(new BasicNameValuePair("lname", ""));
                nameValuePairs.add(new BasicNameValuePair("phone", phone));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("expiry", ""));
                nameValuePairs.add(new BasicNameValuePair("idtype", ""));
                nameValuePairs.add(new BasicNameValuePair("idnumber", ""));

                nameValuePairs.add(new BasicNameValuePair("nonce", ""));



                double amenityTotal = (reservation_total - taxAmount) - (reservation_sub_total - totalDiscount);
                double despoitTotal = ( reservation_total * .50 );

                nameValuePairs.add(new BasicNameValuePair("numofdays", String.valueOf(num_of_days)));
                nameValuePairs.add(new BasicNameValuePair("guests", String.valueOf(numf_of_guest)));
                nameValuePairs.add(new BasicNameValuePair("numofadults",String.valueOf(num_of_adults)));
                nameValuePairs.add(new BasicNameValuePair("numofchildren",String.valueOf(num_of_children)));
                nameValuePairs.add(new BasicNameValuePair("numofinfants",String.valueOf(num_of_infants)));
                nameValuePairs.add(new BasicNameValuePair("fromdate", fromdate));
                nameValuePairs.add(new BasicNameValuePair("todate",todate));
                nameValuePairs.add(new BasicNameValuePair("subtotal", "stackoverflow.com is Cool!"));
                nameValuePairs.add(new BasicNameValuePair("discount", String.format("%.2f",totalDiscount)));
                nameValuePairs.add(new BasicNameValuePair("amenityTotal", String.format("%.2f",amenityTotal)));
                nameValuePairs.add(new BasicNameValuePair("tax", String.format("%.2f",taxAmount)));
                nameValuePairs.add(new BasicNameValuePair("deposit", String.format("%.2f",despoitTotal)));
                nameValuePairs.add(new BasicNameValuePair("total", String.format("%.2f",reservation_total)));


                nameValuePairs.add(new BasicNameValuePair("payment_method_nonce",strings[0]));

                nameValuePairs.add(new BasicNameValuePair("amount",String.format("%.2f",despoitTotal)));



                OutputStream os = conn.getOutputStream();


                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(nameValuePairs));
                writer.flush();
                writer.close();


                imageToSend.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();


                InputStream in = new BufferedInputStream(conn.getInputStream());

                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                String line = "";

                StringBuilder stringBuilder = new StringBuilder();

                while ((line = responseStreamReader.readLine()) != null)

                    stringBuilder.append(line).append("\n");

                responseStreamReader.close();

                 response = stringBuilder.toString();

                conn.disconnect();
            }

            catch(MalformedURLException e) {

                e.printStackTrace();

            }

            catch(IOException e) {

                e.printStackTrace();

            }

            /*
            String url = "http://www.sinopiainn.com/api/mobile/payment/";


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            if(f != null ) {



            }


            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("roomarray[]", String.valueOf(Jsonroomlist)));
            nameValuePairs.add(new BasicNameValuePair("amenityarray[]", String.valueOf(Jsonamenitylist)));
            nameValuePairs.add(new BasicNameValuePair("offerarray[]", String.valueOf(Jsonofferlist)));
            nameValuePairs.add(new BasicNameValuePair("fname", name));
            nameValuePairs.add(new BasicNameValuePair("lname", ""));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("expiry", ""));
            nameValuePairs.add(new BasicNameValuePair("idtype", ""));
            nameValuePairs.add(new BasicNameValuePair("idnumber", ""));

            nameValuePairs.add(new BasicNameValuePair("nonce", ""));



            double amenityTotal = (reservation_total - taxAmount) - (reservation_sub_total - totalDiscount);
            double despoitTotal = ( reservation_total * .50 );

            nameValuePairs.add(new BasicNameValuePair("numofdays", String.valueOf(num_of_days)));
            nameValuePairs.add(new BasicNameValuePair("guests", String.valueOf(numf_of_guest)));
            nameValuePairs.add(new BasicNameValuePair("numofadults",String.valueOf(num_of_adults)));
            nameValuePairs.add(new BasicNameValuePair("numofchildren",String.valueOf(num_of_children)));
            nameValuePairs.add(new BasicNameValuePair("numofinfants",String.valueOf(num_of_infants)));
            nameValuePairs.add(new BasicNameValuePair("fromdate", fromdate));
            nameValuePairs.add(new BasicNameValuePair("todate",todate));
            nameValuePairs.add(new BasicNameValuePair("subtotal", "stackoverflow.com is Cool!"));
            nameValuePairs.add(new BasicNameValuePair("discount", String.format("%.2f",totalDiscount)));
            nameValuePairs.add(new BasicNameValuePair("amenityTotal", String.format("%.2f",amenityTotal)));
            nameValuePairs.add(new BasicNameValuePair("tax", String.format("%.2f",taxAmount)));
            nameValuePairs.add(new BasicNameValuePair("deposit", String.format("%.2f",despoitTotal)));
            nameValuePairs.add(new BasicNameValuePair("total", String.format("%.2f",reservation_total)));


            nameValuePairs.add(new BasicNameValuePair("payment_method_nonce",strings[0]));

            nameValuePairs.add(new BasicNameValuePair("amount",String.format("%.2f",despoitTotal)));


            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


/* InputStreamEntity reqEntity = null;
            try {
                reqEntity = new InputStreamEntity(new FileInputStream(f), -1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            reqEntity.setContentType("binary/octet-stream");
            reqEntity.setChunked(true); // Send in multiple parts if needed
            httppost.setEntity(reqEntity);

*/
           /* HttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
            } catch (IOException e) {
                e.printStackTrace();
            }




            return response.toString();*/


            return response;

        }

        protected void onPostExecute(String result) {




        }

    }


    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
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





    public double subtotaladmission(){


        double subtotaladmission = 0;


        try {

            for(int j = 0; j < itinerary.length() ; j++) {

                JSONObject IC = (JSONObject) itinerary.get(j);

                subtotaladmission = subtotaladmission +  IC.getDouble("averageprice") ;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return subtotaladmission;


    }



    public double subtotalavergae(){

        double subtotalavergae = 0;

        try {

        for(int j = 0; j < itinerary.length() ; j++) {

            JSONObject IC = (JSONObject) itinerary.get(j);

            subtotalavergae = subtotalavergae +  IC.getDouble("averageprice") ;


        }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return subtotalavergae;


    }


    public double carhire(){


        double carhire = 0;

        carhire = ( (numf_of_guest / 5 ) + 1 )  * 60;

        return carhire;


    }


    public double tax(){

        double tax = 0;

        double tripTotal = subtotalavergae() + subtotaladmission();

        tax = tripTotal * .25;

        return tax;


    }

    public double triptotal(){

        double triptotal = 0;

        triptotal = tax() + subtotalavergae() + subtotaladmission();

        return triptotal;


    }




}






package com.example.home.sinopiainntravelapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class Activity_Home extends AppCompatActivity {

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

    static Double reservation_total = 0.00;

    static Activity_Home homeActivity;

    static public int placePos = 0;

    Bundle bundle;

    public Double reservation_sub_total = 0.00;

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

    public static double totalDiscount = 0.00;

    public static double taxAmount = 0.00;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        client = new AsyncHttpClient();

        homeActivity = this;

        bundle = new Bundle();

        Jsonroomlist = new JSONArray();

        Jsonamenitylist = new JSONArray();

        Jsonofferlist = new JSONArray();

        JsonBusinesses = new JSONArray();

        itinerary = new JSONArray();


        jsonRooms = new JSONArray();

        jsonAmenities = new JSONArray();

        FrameLayout mViewPager = (FrameLayout) findViewById(R.id.container);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Reception").setIcon(R.drawable.ic_home_white_24dp), 0, true);

        tabLayout.addTab(tabLayout.newTab().setText("Guide").setIcon(R.drawable.ic_map_24dp), 1, false);

        tabLayout.addTab(tabLayout.newTab().setText("Help").setIcon(R.drawable.ic_help_24dp), 2, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));
            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));
        }

        getSupportFragmentManager().beginTransaction().add(R.id.container, new Fragment_HomePage()).commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {
                    case 0:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_HomePage()).addToBackStack(null).commit();

                        break;

                    case 1:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Travel_Tips()).addToBackStack(null).commit();

                        break;

                    case 2:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Contact_Page()).addToBackStack(null).commit();

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


                } else if (tab.getPosition() == 1)


                {


                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Travel_Tips()).addToBackStack(null).commit();


                } else if (tab.getPosition() == 2)


                {


                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Contact_Page()).addToBackStack(null).commit();


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
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        // Response is automatically parsed into a JSONArray
                        // json.getJSONObject(0).getLong("id");
                        // Here we want to process the json data into Java models.
                        Fragment_Food new_fragment = new Fragment_Food();

                        bundle.putInt("Activity", 0);

                        bundle.putString("Json", json.toString());

                        new_fragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container,new_fragment).addToBackStack(null).commit();



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
                        // Handle the failure and alert the user to retry
                        Log.e("ERROR", e.toString());
                    }
                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
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

    public void homePageFadeTransition(Fragment new_fragment) {


        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new_fragment ).addToBackStack(null).commit();


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


        // amt = String.valueOf(reservation_total * amount);


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

       File photoID = new File(photofile);


      try {


          //params.put("profile_picture", photoID);

          params.put("picture[image]", new File(photofile));

        } catch(FileNotFoundException e) {  }



        try {


        for(int x = 0; x < Jsonroomlist.length() ; x++) {


                params.put("roomarray[]", String.valueOf(Jsonroomlist.get(x)));


        }

            for(int y = 0; y < Jsonamenitylist.length() ; y++) {


                params.put("amenityarray[]", String.valueOf(Jsonamenitylist.get(y)));


            }

            for(int z = 0; z < Jsonofferlist.length() ; z++) {


                params.put("offerarray[]", String.valueOf(Jsonofferlist.get(z)));


            }



            params.put("fname",name);
            params.put("lname","");
            params.put("phone",phone);
            params.put("email",email);
            params.put("expiry","");
            params.put("idtype","");
            params.put("idnumber","");
            params.put("numofdays",num_of_days);
            params.put("guests",numf_of_guest);
            params.put("numofadults","");
            params.put("numofchildren","");
            params.put("numofinfants","");
            params.put("fromdate",fromdate);
            params.put("todate",todate);

            double amenityTotal = (reservation_total - taxAmount) - (reservation_sub_total - totalDiscount);

            params.put("subtotal",String.format("%.2f",reservation_sub_total));
            params.put("discount",String.format("%.2f",totalDiscount));
            params.put("amenityTotal",String.format("%.2f",amenityTotal));
            params.put("tax",String.format("%.2f",taxAmount));
            params.put("total",String.format("%.2f",reservation_total));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        client = new AsyncHttpClient();

        client.setConnectTimeout(20000);

        Log.i("params", String.valueOf(params));

        client.post("http://www.sinopiainn.com/file-upload/", params , new JsonHttpResponseHandler() {

            @Override
            public void onStart() {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {


                Log.i("Reservation Response", String.valueOf(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {

                Log.e("ERROR", e.toString());
            }

            @Override
            public void onRetry(int retryNo) {

            }

        });


       /* client = new AsyncHttpClient();

        client.setConnectTimeout(20000);

        client.get("http://www.sinopiainn.com/api/checkout/", new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String clientToken) {

                Cart cart = Cart.newBuilder()
                        .setCurrencyCode("USD")
                        .setTotalPrice(String.format("%.2f",reservation_total))
                        .addLineItem(LineItem.newBuilder()
                                .setCurrencyCode("USD")
                                .setDescription("Reservation Deposit")
                                .setQuantity("1")
                                .setUnitPrice(String.format("%.2f",reservation_total))
                                .setTotalPrice(String.format("%.2f",reservation_total))
                                .build())
                        .build();

                PaymentRequest paymentRequest = new PaymentRequest().clientToken(clientToken);

                paymentRequest.tokenizationKey("sandbox_btkvgtj8_mb3k4ty88wspgh8m").androidPayCart(cart);

                paymentRequest.amount("USD "+ String.format("%.2f",reservation_total))
                        .primaryDescription("Reservation Deposit")
                        .secondaryDescription(Jsonroomlist.length()+" Rooms From: "+fromdate+" To: "+todate)
                        .submitButtonText("Pay");


  startActivityForResult(paymentRequest.getIntent(Activity_Home.this), REQUEST_CODE);


            }
        });*/


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



         PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE );

                String nonce = paymentMethodNonce.getNonce();



               /*


            RequestParams requestParams = new RequestParams();
            requestParams.put("payment_method_nonce", paymentMethodNonce.getNonce());
            requestParams.put("amount", "10.00");

            client.post(SERVER_BASE + "/payment", requestParams, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(SDKActivity.this, responseString, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Toast.makeText(SDKActivity.this, responseString, Toast.LENGTH_LONG).show();
                }
            });



                */


           }

        }

    }

   @Override
    protected void onStop() {
        super.onStop();

        finish();
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


    }

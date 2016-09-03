package com.example.home.sinopiainntravelapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class Activity_CheckIn extends AppCompatActivity {
    String[] menu = {
            "Menu","Concierge","Bookshelf","Guest Book","TV"


    };

    private static final int REQUEST_CODE = 0;

    JSONArray Jsonroomlist;

    JSONArray Jsonamenitylist;

    JSONArray Jsonofferlist;

    JSONArray JsonBusinesses;

    JSONArray itinerary;
    
    ArrayList<String> businessTypeList;

    ArrayList<String> amenititesTypeList;

    private FrameLayout mViewPager;

    LinearLayout overlay;
    ImageView overlay_close;
    ArrayFilter businessFilter ;

    private BottomSheetBehavior mBottomSheetBehavior;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    CoordinatorLayout.Behavior behavior;
    Bundle bundle;

    public static final String PREFS_NAME_1 = "guestName";

    SharedPreferences settings;


    static Activity_CheckIn checkinActivity;
    static public int placePos = 0;
    AsyncHttpClient client;
    public static Activity_CheckIn getInstance(){
        return   checkinActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        itinerary = new JSONArray();

        bundle = new Bundle();

        checkinActivity = this;

         client = new AsyncHttpClient();

        mViewPager = (FrameLayout) findViewById(R.id.container);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Reception").setIcon(R.drawable.ic_home_white_24dp), 0, true);

        tabLayout.addTab(tabLayout.newTab().setText("Guide").setIcon(R.drawable.ic_map_24dp), 1, false);


        tabLayout.addTab(tabLayout.newTab().setText("Help").setIcon(R.drawable.ic_help_24dp), 2, false);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.white));

            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.white));

        }


        Fragment_Confirmation openingFragment = new Fragment_Confirmation();

        settings = getSharedPreferences(PREFS_NAME_1, 0);

        bundle.putString("fname",settings.getString("fname", ""));

        bundle.putString("lname",settings.getString("lname", ""));

        openingFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container,openingFragment).commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {
                    case 0:


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Confirmation()).addToBackStack(null).commit();

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

                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, new Fragment_Confirmation()).addToBackStack(null).commit();


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


    @Override
    public void onBackPressed() {



        getSupportFragmentManager().popBackStack();


    }

    public void showOverlay() {

        BottomSheetDialogFragment bottomSheetDialogFragment = new TutsPlusBottomSheetDialogFragment();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

    }

    @Override
    protected void onStop() {
        super.onStop();

        finish();
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


    }//

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

        businessFilter = new ArrayFilter();

        try {
            JsonBusinesses = businessFilter.businessfilterList(business,jsonroomlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().popBackStack();

    }

    public void goToChat() {


        Log.i("chat", "going to chat");

       /* Intent intent = new Intent(getBaseContext(), Chat.class);

        startActivity(intent);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/





    }


    public void onBraintreeSubmit() {


        RequestParams params = new RequestParams();
        try {

        for(int x = 0; x < itinerary.length() ; x++) {



                params.put("places[]", String.valueOf(itinerary.get(x)));


        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.put("token",settings.getString("token", ""));

        client = new AsyncHttpClient();

        client.setConnectTimeout(20000);

        Log.i("params", String.valueOf(params));

        client.post("http://www.sinopiainn.com/api/business-rates/", params , new JsonHttpResponseHandler() {

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



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


            if (requestCode == REQUEST_CODE) {


            if (resultCode == BraintreePaymentActivity.RESULT_OK) {



                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE );

                String nonce = paymentMethodNonce.getNonce();




            }

        }

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


    private class foodAdapter extends RecyclerView.Adapter<foodAdapter.ViewHolder> {

        private String[] foodDataset;
        private Random mRandom = new Random();

        private Integer[] icons = {

                R.drawable.ic_restaurant_black_24dp, R.drawable.ic_map_24dp, R.drawable.ic_bookmark_outline_24dp,R.drawable.ic_local_library_24dp,R.drawable.ic_local_movies_24dp,R.drawable.ic_shopping_basket_24dp
        };



        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView ;


            public ImageView image ;



            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);

                mTextView = (TextView) itemView.findViewById(R.id.name);

                image = (ImageView) itemView.findViewById(R.id.image);


            }


            @Override
            public void onClick(View v) {

                final int position = getLayoutPosition();

                // ((homePage)getActivity()).homePageReplaceFragment("roomsList");


                bundle.putInt("Activity", 1);



                switch (position) {
                    case 0:


                        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);




                        client = new AsyncHttpClient();

                        client.setConnectTimeout(20000);

                        client.get("http://www.sinopiainn.com/api/menu", new JsonHttpResponseHandler() {

                            @Override
                            public void onStart() {
                                // called before request is started
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {


                                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                                Fragment_Food new_fragment =  new Fragment_Food() ;

                                bundle.putInt("Activity", 1);

                                bundle.putInt("Menu", position);

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

                    case 1:

                        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);


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

                                Fragment_Travel_Planner travel =  new Fragment_Travel_Planner() ;

                                travel.setArguments(bundle);


                                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container,travel).addToBackStack(null).commit();

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

                    case 2:

                        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                        client = new AsyncHttpClient();

                        client.setConnectTimeout(20000);

                        client.get("http://www.sinopiainn.com/api/books", new JsonHttpResponseHandler() {

                            @Override
                            public void onStart() {
                                // called before request is started
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                                Fragment_Food bookShelf =  new Fragment_Food() ;

                                bundle.putInt("Activity", 1);

                                bundle.putInt("Menu", position);

                                bundle.putString("Json",  json.toString());

                                bookShelf.setArguments(bundle);

                                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, bookShelf).addToBackStack(null).commit();


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

    String json ="[{\"name\":\"Camel\",\"date\":\"20-04-2016\",\"rating\":\"3\",\"comment\":\"The Vette Kat Harbour Bed & Breakfast has two distinct competitive edges that differentiates it from the competition.  The first is the never-ending attention to detail and customer service.  The St. Lucia's recognize that their mission is to ensure that their customers have the finest stay with them.  Both Kayman and Jenné will do whatever it takes to ensure the customer's happiness.  This will be showcased in breakfast which will offer Starbucks Authorized and Certified Training System of Coffee and Tazo Tea service.\"},{\"name\":\"puce\",\"date\":\"20-04-2016\",\"rating\":\"4\",\"comment\":\"test comment\"},{\"name\":\"Test\",\"date\":\"20-04-2016\",\"rating\":\"4\",\"comment\":\"test comment\"}]";

                        Fragment_Food guestBook =  new Fragment_Food() ;

                        bundle.putInt("Activity", 1);

                        bundle.putInt("Menu", position);

                        bundle.putString("Json",json);

                        guestBook.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, guestBook).addToBackStack(null).commit();




                      /*  ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                        client = new AsyncHttpClient();

                        client.setConnectTimeout(20000);

                        client.get("http://www.sinopiainn.com/api/reviews", new JsonHttpResponseHandler() {

                            @Override
                            public void onStart() {
                                // called before request is started
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                                Fragment_Food guestBook =  new Fragment_Food() ;

                                bundle.putInt("Activity", 1);

                                bundle.putInt("Menu", position);

                                bundle.putString("Json",json.toString());

                                guestBook.setArguments(bundle);

                                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, guestBook).addToBackStack(null).commit();


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
*/

                        break;
                    case 4:


                        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                        client = new AsyncHttpClient();

                        client.setConnectTimeout(20000);

                        client.get("http://www.sinopiainn.com/api/tv", new JsonHttpResponseHandler() {

                            @Override
                            public void onStart() {
                                // called before request is started
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                                Fragment_Food tv =  new Fragment_Food() ;

                                bundle.putInt("Activity", 1);

                                bundle.putInt("Menu", position);

                                bundle.putString("Json",json.toString());

                                tv.setArguments(bundle);

                                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out).replace(R.id.container, tv).addToBackStack(null).commit();


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
                    case 5:


                       /* ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                        fragment_shop shop =  new fragment_shop() ;

                        shop.setArguments(bundle);


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, shop).addToBackStack(null).commit();
*/
                        break;


                }


            }

        }

        public foodAdapter(Object p0) {

        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public foodAdapter(Context context, String[] foodset) {

            foodDataset = foodset;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemamentitylayout, parent, false);

            ViewHolder vh = new ViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.mTextView.setText(foodDataset[position]);
            holder.image.setImageResource(icons[position]);


        }
        @Override
        public int getItemCount() {
            return foodDataset.length;
        }

    }


    public class TutsPlusBottomSheetDialogFragment extends BottomSheetDialogFragment {

        private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        };

        @Override
        public void setupDialog(Dialog dialog, int style) {

            super.setupDialog(dialog, style);

            View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);

            dialog.setContentView(contentView);

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();

            behavior = params.getBehavior();



            // overlay = (LinearLayout) contentView.findViewById(R.id.overlay);



            mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recycler);

            mRecyclerView.setFadingEdgeLength(150);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);


            mLayoutManager = new LinearLayoutManager(getActivity());


            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new foodAdapter(getActivity(),menu);

            // specify an adapter (see also next example)
            // mAdapter = new foodAdapter(foods);

            mRecyclerView.setAdapter(mAdapter);


            if( behavior != null && behavior instanceof BottomSheetBehavior ) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);

                ((BottomSheetBehavior) behavior).setPeekHeight(1200);

            }
        }
    }
}

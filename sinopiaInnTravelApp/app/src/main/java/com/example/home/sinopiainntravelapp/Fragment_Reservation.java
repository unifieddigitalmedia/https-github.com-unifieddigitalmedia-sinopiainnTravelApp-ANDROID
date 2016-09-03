package com.example.home.sinopiainntravelapp;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class Fragment_Reservation extends Fragment implements AdapterView.OnItemSelectedListener {

    public static  int mYear;

    public static  int mMonth;

    public static  int mDay;

    public static int DATE_DIALOG_ID = 1;


    public static int scrollX = 0;

    public static int scrollY = -1;

    NestedScrollView scrollView;

    ImageView mainImage;

    ScaleBitMaps bitmapClass ;

    EditText fromDate;

    EditText toDate;

    EditText rooms;

    EditText promo;

    EditText amenities;

    Button getTotal;

    BuildString TextSetter ;


    TextInputLayout fromDateError;
    TextInputLayout toDateError;
    TextInputLayout roomsError;
    TextInputLayout amenititesError;
    TextView errormessage ;

    Bundle bundle;

    public Fragment_Reservation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reservation, container, false);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Your Reservation");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.number_of_guest, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

         bundle = new Bundle();

         fromDate = (EditText) rootView.findViewById(R.id.fromdate);

         fromDateError = (TextInputLayout) rootView.findViewById(R.id.fromDateError);

         toDateError = (TextInputLayout) rootView.findViewById(R.id.toDateError);

         roomsError = (TextInputLayout) rootView.findViewById(R.id.roomsError);

         amenititesError = (TextInputLayout) rootView.findViewById(R.id.amenititesError);

         errormessage = (TextView) rootView.findViewById(R.id.errormessage);

         fromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                DATE_DIALOG_ID = 1;

                showdatedialog();


             }


        });


        toDate = (EditText) rootView.findViewById(R.id.todate);

        toDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                DATE_DIALOG_ID = 2;

                showdatedialog();


            }


        });



        rooms = (EditText) rootView.findViewById(R.id.rooms);

        rooms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if (!toDate.getText().toString().matches("")) {


                    String json = "[{\"_id\": \"57471cfa864730dd9d55d44d\",\"name\": \"Spring Bud\",\"description\": \"Double occupancy bedroom with ensuite bathroom\",\"occupancy\": \"2\",\"price\": \"165.00\",\"icon\": \"/images/parrot_thumb.png\",\"booking\": []},{\"_id\": \"57471cfa864730dd9d55d44e\",\"name\": \"Coquelicot\",\"description\": \"Quadruple occupancy bedroom with ensuite bathroom and lounge\",\"occupancy\": \"4\",\"price\": \"200.00\",\"icon\": \"/images/parrot_thumb.png\",\"booking\": []}]";

                    Fragment_Rooms new_fragment = new Fragment_Rooms();



                    bundle.putString("Rooms", ((Activity_Home) getActivity()).jsonRooms.toString());

                    new_fragment.setArguments(bundle);

                    ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment);


                }else{



                    roomsError.setError("You need to choose your check in and check out dates ");

                }


            }


        });




        amenities = (EditText) rootView.findViewById(R.id.amenities);

        amenities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!toDate.getText().toString().matches("")) {


                    Fragment_Amenities new_fragment = new Fragment_Amenities();



                    bundle.putString("Rooms",((Activity_Home) getActivity()).jsonAmenities.toString());

                    new_fragment.setArguments(bundle);

                    ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment);


                }else{



                    roomsError.setError("You need to choose your check in and check out dates ");

                }

            }


        });


        promo = (EditText) rootView.findViewById(R.id.promo);

        promo.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (!event.isShiftPressed()) {

                                StringBuilder builder = new StringBuilder();

                                String WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/api/checkavailability/?fromdate=")
                                        .append(((Activity_Home) getActivity()).fromdate).append("&todate=").append(((Activity_Home) getActivity()).todate).append("&promo=").append(promo.getText().toString()).toString();

                                checkAvilabilty(WEB_SERVICE_URL);

                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                });

        /**/



        getTotal = (Button) rootView.findViewById(R.id.goToTotal);

        getTotal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if (((Activity_Home) getActivity()).Jsonroomlist.length() > 0 || ((Activity_Home) getActivity()).numf_of_guest != 0 ) {




                    Fragment_Bill new_fragment = new Fragment_Bill();

                    Bundle bundle = new Bundle();

                    bundle.putString("RoomList", ((Activity_Home) getActivity()).Jsonroomlist.toString());

                    bundle.putString("OfferList", ((Activity_Home) getActivity()).Jsonofferlist.toString());

                    bundle.putString("AmenityList", ((Activity_Home) getActivity()).Jsonamenitylist.toString());

                    new_fragment.setArguments(bundle);

                    ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment);



                }else{



                    errormessage.setText("You need to choose your rooms");
                    errormessage.setTextColor(getResources().getColor(android.R.color.holo_red_light));


                }

            }


        });


        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(),R.drawable.room, 100, 100));

        scrollView = (NestedScrollView) rootView.findViewById(R.id.scrollView);



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));


        return rootView;


    }


    @Override
    public void onPause()

    {
        super.onPause();
        scrollX = scrollView.getScrollX();
        scrollY = scrollView.getScrollY();
    }

    @Override
    public void onResume(){

        super.onResume();

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(scrollX, scrollY);
            }
        });


        TextSetter = new BuildString(getActivity());

        rooms.setText(TextSetter.buildString(((Activity_Home) getActivity()).Jsonroomlist));

        amenities.setText(TextSetter.buildString(((Activity_Home)getActivity()).Jsonamenitylist));

    }


    public void showdatedialog() {


        final Calendar c = Calendar.getInstance();

        int mYear = c.get(Calendar.YEAR);

        int mMonth = c.get(Calendar.MONTH);

        int mDay = c.get(Calendar.DAY_OF_MONTH);

        //((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);

        new DatePickerDialog(getActivity(), mDateSetListener, mYear, mMonth, mDay).show();


    }

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mYear = year;

                mMonth = monthOfYear;

                mDay = dayOfMonth;

                Date date = new Date(mYear - 1900, mMonth, mDay);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                String cdate = formatter.format(date);

                formatter = new SimpleDateFormat("EEE MMM dd yyyy");

                String ndate = formatter.format(date);

                if (DATE_DIALOG_ID == 1) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                    ((Activity_Home) getActivity()).fromdate = cdate;

                    try {


                        Date fromdateObj = sdf.parse(((Activity_Home) getActivity()).fromdate);

                        if (new Date().after(fromdateObj)) {



                            String newdate = sdf.format(new Date());


                            if(sdf.parse(newdate).equals(fromdateObj))

                            {

                                fromDate.setText(ndate);

                                toDate.setText("Select Date");

                                toDate.setOnClickListener(new View.OnClickListener() {


                                    @Override
                                    public void onClick(View v) {


                                        DATE_DIALOG_ID = 2;

                                        showdatedialog();
                                        fromDateError.setError(null);

                                    }


                                });


                            } else {


                                ((Activity_Home) getActivity()).fromdate = null;

                                fromDateError.setError("Your check in date cannot be in the past.");


                            }


                        } else {


                            fromDate.setText(ndate);
                            fromDateError.setError(null);
                            toDate.setText("Select Date");


                            toDate.setOnClickListener(new View.OnClickListener() {


                                @Override
                                public void onClick(View v) {


                                    DATE_DIALOG_ID = 2;

                                    showdatedialog();


                                }


                            });


                        }

                    } catch (ParseException e) {
                        e.printStackTrace();


                    }


                } else {


                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                    ((Activity_Home) getActivity()).todate = cdate;

                    Date dateObj = null;

                    try {

                        dateObj = sdf.parse(((Activity_Home) getActivity()).todate);


                        Date fromdateObj = sdf.parse(((Activity_Home) getActivity()).fromdate);


                        String newdate = sdf.format(new Date());

                        if(sdf.parse(newdate).equals(dateObj)  )

                        {

                            toDateError.setError("You cannot check out today.");


                        }

                        else
                        {


                            if (new Date().after(dateObj) )


                            {



                                ((Activity_Home) getActivity()).todate = null;

                                toDateError.setError("Your check out date cannot be in the past.");




                            } else {


                                if(fromdateObj.after(dateObj))

                                {

                                    ((Activity_Home) getActivity()).todate = null;


                                    toDateError.setError("Your check out date cannot before your check in date.");

                                }
                                else

                                {

                                    if(sdf.parse(((Activity_Home) getActivity()).fromdate).equals(dateObj) )

                                    {


                                        toDateError.setError("Our minimum stay is one night.");

                                    }
                                    else

                                    {


                                        toDate.setText(ndate);

                                        toDateError.setError(null);

                                        ((Activity_Home) getActivity()).setNumbOfDays(sdf.parse(((Activity_Home) getActivity()).fromdate), sdf.parse(((Activity_Home) getActivity()).todate));

                                        StringBuilder builder = new StringBuilder();

                                       String WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/api/checkavailability/?fromdate=")
.append(((Activity_Home) getActivity()).fromdate).append("&todate=").append(((Activity_Home) getActivity()).todate).append("&promo=").append(promo.getText().toString()).toString();

checkAvilabilty(WEB_SERVICE_URL);




                                    }






                                }







                            }




                        }







                    } catch (ParseException e) {
                        e.printStackTrace();
                    }





                }


                //updateDisplay();


            }


        };
    }

    private void checkAvilabilty(String WEB_SERVICE_URL) {



        Log.i("WEB_SERVICE_URL",WEB_SERVICE_URL);

        AsyncHttpClient client = new AsyncHttpClient();

        client.setConnectTimeout(20000);
        client.get(WEB_SERVICE_URL, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                Log.i("json",json.toString());
                AsyncHttpClient client = new AsyncHttpClient();


                try {

                    ((Activity_Home) getActivity()).jsonRooms = (JSONArray) json.get(0);
                    ((Activity_Home) getActivity()).jsonAmenities = (JSONArray) json.get(2);

                    ((Activity_Home) getActivity()).Jsonofferlist = (JSONArray) json.get(1);


                } catch (JSONException e) {

                    e.printStackTrace();

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position != 0){


            ((Activity_Home) getActivity()).setNumOfGuest((int) Integer.parseInt((String) parent.getItemAtPosition(position)));


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

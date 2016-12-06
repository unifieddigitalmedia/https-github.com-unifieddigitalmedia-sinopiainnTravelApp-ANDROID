package com.example.home.sinopiainntravelapp;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_New_Comment extends Fragment {


    public TextView mTextView , nameTextView , dateTextView , commentTextView;

    public Button submitComment;

    public ImageButton rating1,rating2,rating3,rating4,rating5 ;

    public static int DATE_DIALOG_ID = 1;

    public static  int mYear, mMonth, mDay,rating;

    public static String fromdate;

    TextInputLayout fromDateError;

    CardView blistlayout;

    private Integer[] icons = {

            R.drawable.ic_star_outline_24dp, R.drawable.ic_star_24dp,  };


    public Fragment_New_Comment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment__new__comment, container, false);

        blistlayout = (CardView) rootView.findViewById(R.id.blistlayout);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        rating = 0;

        rating1 = (ImageButton) rootView.findViewById(R.id.rating1);

        rating1.setImageResource(icons[0]);

        rating1.setColorFilter(Color.argb(999,153,153,51));

        rating1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                rating1.setImageResource(icons[1]);
                rating2.setImageResource(icons[0]);
                rating3.setImageResource(icons[0]);
                rating4.setImageResource(icons[0]);
                rating5.setImageResource(icons[0]);

                rating = 1;

            }


        });


        rating2 = (ImageButton) rootView.findViewById(R.id.rating2);

        rating2.setImageResource(icons[0]);
        rating2.setColorFilter(Color.argb(999,153,153,51));
        rating2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                rating1.setImageResource(icons[1]);
                rating2.setImageResource(icons[1]);
                rating3.setImageResource(icons[0]);
                rating4.setImageResource(icons[0]);
                rating5.setImageResource(icons[0]);

                rating = 2;
            }


        });



        rating3 = (ImageButton) rootView.findViewById(R.id.rating3);

        rating3.setImageResource(icons[0]);
        rating3.setColorFilter(Color.argb(999,153,153,51));
        rating3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                rating1.setImageResource(icons[1]);
                rating2.setImageResource(icons[1]);
                rating3.setImageResource(icons[1]);
                rating4.setImageResource(icons[0]);
                rating5.setImageResource(icons[0]);

                rating = 3;

            }


        });

        rating4 = (ImageButton) rootView.findViewById(R.id.rating4);
        rating4.setImageResource(icons[0]);
        rating4.setColorFilter(Color.argb(999,153,153,51));
        rating4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                rating1.setImageResource(icons[1]);
                rating2.setImageResource(icons[1]);
                rating3.setImageResource(icons[1]);
                rating4.setImageResource(icons[1]);
                rating5.setImageResource(icons[0]);

                rating = 4;

            }


        });


        rating5 = (ImageButton) rootView.findViewById(R.id.rating5);

        rating5.setImageResource(icons[0]);
        rating5.setColorFilter(Color.argb(999,153,153,51));
        rating5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                rating1.setImageResource(icons[1]);
                rating2.setImageResource(icons[1]);
                rating3.setImageResource(icons[1]);
                rating4.setImageResource(icons[1]);
                rating5.setImageResource(icons[1]);

                rating = 5;

            }


        });


        fromDateError = (TextInputLayout) rootView.findViewById(R.id.fromDateError);

        nameTextView = (TextView) rootView.findViewById(R.id.name);

        dateTextView = (TextView) rootView.findViewById(R.id.date);

        dateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                DATE_DIALOG_ID = 1;

                showdatedialog();


            }


        });


        commentTextView = (TextView) rootView.findViewById(R.id.comment);

        submitComment = (Button) rootView.findViewById(R.id.submitComment);

        submitComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                RequestParams params = new RequestParams();



                params.put("date", fromdate);
                params.put("name",nameTextView.getText());
                params.put("comment",commentTextView.getText());
                params.put("rating",rating);



                AsyncHttpClient client = new AsyncHttpClient();

                client.setConnectTimeout(20000);

                client.post("http://www.sinopiainn.com/api/reviews/", new TextHttpResponseHandler() {


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String clientToken) {


                        Snackbar snackbar = Snackbar
                                .make(blistlayout, "Your comments have been posted", Snackbar.LENGTH_LONG);


                        snackbar.setActionTextColor(Color.GREEN);


                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();


                        ((Activity_CheckIn) getActivity()).onBackPressed();


                    }



                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {

                        Log.e("ERROR", e.toString());


                    }
                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }


                });




            }


        });

       return rootView;


    }

    private void showdatedialog() {
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

                fromdate = formatter.format(date);

                formatter = new SimpleDateFormat("EEE MMM dd yyyy");

                String ndate = formatter.format(date);

                if (DATE_DIALOG_ID == 1) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");



                    try {


                        Date fromdateObj = date;

                        if (new Date().after(fromdateObj)) {


                            String newdate = sdf.format(new Date());


                            if (sdf.parse(newdate).equals(fromdateObj))

                            {

                                dateTextView.setText(ndate);



                            } else {



                                dateTextView.setError("Your check in date cannot be in the past.");


                            }


                        } else {


                            dateTextView.setText(ndate);

                            fromDateError.setError(null);

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();


                    }


                }
            }
        };
    }

}

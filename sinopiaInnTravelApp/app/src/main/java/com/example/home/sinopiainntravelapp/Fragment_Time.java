package com.example.home.sinopiainntravelapp;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Time extends Fragment {

    TextView time;

    public Fragment_Time() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment__time, container, false);

        FrameLayout view = (FrameLayout) rootView.findViewById(R.id.framelayout);

        view.setDrawingCacheEnabled(true);

        view.buildDrawingCache();

        Bitmap bm = view.getDrawingCache();

        //bm.createScaledBitmap(bm, view.getMeasuredWidth(), view.getMeasuredHeight(),true);

        View backgroundView = (View) rootView.findViewById(R.id.bgtime);

        BitmapDrawable ob = new BitmapDrawable(getResources(), bm);

        backgroundView.setBackground(ob);

        time = (TextView) rootView.findViewById(R.id.time);

        //TimeZone timezone = TimeZone.getDefault();

        TimeZone timezone = TimeZone.getTimeZone("America/Jamaica");

        String TimeZoneName = timezone.getDisplayName();

        int TimeZoneOffset = timezone.getRawOffset()/(60 * 60 * 1000);


        Calendar calendar = Calendar.getInstance(timezone);


        Date currentLocalTime = calendar.getTime();

        DateFormat date = new SimpleDateFormat("HH:mm a");

        String localTime = date.format(currentLocalTime);


        TimeZone timeZone = TimeZone.getTimeZone("America/Jamaica");
        calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EE MMM dd HH:mm", Locale.UK);
        simpleDateFormat.setTimeZone(timeZone);

        System.out.println("Time zone: " + timeZone.getID());
        System.out.println("default time zone: " + TimeZone.getDefault().getID());
        System.out.println();

        System.out.println("UTC:     " + simpleDateFormat.format(calendar.getTime()));
        System.out.println("Default: " + calendar.getTime());

        time.setText("Local Date And Time Is\n" + simpleDateFormat.format(calendar.getTime()) );


        return rootView;
    }

}

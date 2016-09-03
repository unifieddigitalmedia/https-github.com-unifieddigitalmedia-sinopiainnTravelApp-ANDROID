package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Comment extends Fragment {


    public TextView mTextView , nameTextView , dateTextView , commentTextView;


    public Fragment_Comment() {
        // Required empty public constructor
    }

    JSONObject obj ;

    public ImageView rating1,rating2,rating3,rating4,rating5 ;

    private Integer[] icons = {

            R.drawable.ic_star_outline_24dp, R.drawable.ic_star_24dp,  };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment__comment, container, false);

        try {


            obj = new JSONObject(String.valueOf(getArguments().get("List")));

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(obj.getString("name"));

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        dateTextView = (TextView) rootView.findViewById(R.id.date);

            dateTextView.setText(obj.getString("date"));

        commentTextView = (TextView) rootView.findViewById(R.id.comments);

            commentTextView.setText(obj.getString("comment"));


            rating1 = (ImageView) rootView.findViewById(R.id.rating1);


            rating2 = (ImageView) rootView.findViewById(R.id.rating2);


            rating3 = (ImageView) rootView.findViewById(R.id.rating3);


            rating4 = (ImageView) rootView.findViewById(R.id.rating4);


            rating5 = (ImageView) rootView.findViewById(R.id.rating5);


            switch (Integer.parseInt(obj.getString("rating"))) {
                case 0:

                    rating1.setImageResource(icons[0]);
                    rating2.setImageResource(icons[0]);
                    rating3.setImageResource(icons[0]);
                    rating4.setImageResource(icons[0]);
                    rating5.setImageResource(icons[0]);

                    break;

                case 1:

                    rating1.setImageResource(icons[1]);
                    rating2.setImageResource(icons[0]);
                    rating3.setImageResource(icons[0]);
                    rating4.setImageResource(icons[0]);
                    rating5.setImageResource(icons[0]);

                    break;

                case 2:

                    rating1.setImageResource(icons[1]);
                    rating2.setImageResource(icons[1]);
                    rating3.setImageResource(icons[0]);
                    rating4.setImageResource(icons[0]);
                    rating5.setImageResource(icons[0]);

                    break;

                case 3:

                    rating1.setImageResource(icons[1]);
                    rating2.setImageResource(icons[1]);
                    rating3.setImageResource(icons[1]);
                    rating4.setImageResource(icons[0]);
                    rating5.setImageResource(icons[0]);

                    break;

                case 4:

                    rating1.setImageResource(icons[1]);
                    rating2.setImageResource(icons[1]);
                    rating3.setImageResource(icons[1]);
                    rating4.setImageResource(icons[1]);
                    rating5.setImageResource(icons[0]);

                    break;

                case 5:

                    rating1.setImageResource(icons[1]);
                    rating2.setImageResource(icons[1]);
                    rating3.setImageResource(icons[1]);
                    rating4.setImageResource(icons[1]);
                    rating5.setImageResource(icons[1]);

                    break;



            }



            } catch (JSONException e) {


            e.printStackTrace();


        }



        return rootView;
    }

}

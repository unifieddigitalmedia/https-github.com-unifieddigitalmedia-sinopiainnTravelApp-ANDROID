package com.example.home.sinopiainntravelapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listview_Item_Description extends Fragment {

    Button btnNext,btnPrevious;
    ViewAnimator viewAnimator;
    View rootView;
    ViewFlipper mViewFlipper;
    JSONObject obj ;
    private GestureDetector mGestureDetector;
    public Listview_Item_Description() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {


            obj = new JSONObject(String.valueOf(getArguments().get("Meta")));



        } catch (JSONException e) {


            e.printStackTrace();


        }

        Log.i("Object lENGTH", String.valueOf(getArguments().getInt("Menu")));


        if(getArguments().getInt("Menu") != 2 ){


            rootView = inflater.inflate(R.layout.fragment_listview_item_description, container, false);

            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

            TextView description = (TextView) rootView.findViewById(R.id.description);

            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

            collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

            viewAnimator = (ViewAnimator) rootView.findViewById(R.id.viewAnimator1);

            viewAnimator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // This is all you need to do to 3D flip
                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
                }

            });

            //viewAnimator.setInAnimation(getActivity(), android.R.anim.fade_in);

            //viewAnimator.setOutAnimation(getActivity(), android.R.anim.fade_out);

            try {

                collapsingToolbar.setTitle(obj.getString("name"));


                description.setText(obj.getString("description"));


            } catch (JSONException e) {


                e.printStackTrace();


            }


            FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab);

            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewAnimator.showNext();


                }
            });


        }else {


             rootView = inflater.inflate(R.layout.fragment_listview_book_description, container, false);

             mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);

            mViewFlipper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // This is all you need to do to 3D flip
                    AnimationFactory.flipTransition(mViewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                }

            });


            try {


                JSONArray list = new JSONArray(obj.getString("ingridients"));


                for (int i = 0; i < list.length(); i++) {


                    TextView title = new TextView(getActivity());
                    title.setTextColor(Color.BLACK);
                    title.setText(list.getString(i));
                    mViewFlipper.addView(title);


                }

                
                //mViewFlipper.setInAnimation(inFromLeftAnimation());
                //mViewFlipper.setInAnimation(inFromLeftAnimation());
                //mViewFlipper.setInAnimation(getActivity(), android.R.anim.fade_in);
                //mViewFlipper.setOutAnimation(getActivity(), android.R.anim.fade_out);


            } catch (JSONException e) {


                e.printStackTrace();


            }

            FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab);

            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mViewFlipper.showNext();


                }
            });



        }





        /*viewAnimator = (ViewAnimator) rootView.findViewById(R.id.viewAnimator1);

        final Animation inAnim = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        final Animation outAnim = AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_out);

        viewAnimator.setInAnimation(inAnim);
        viewAnimator.setOutAnimation(outAnim);*/










        return rootView;

    }

    private Animation inFromLeftAnimation() {


        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f);

        inFromLeft.setDuration(400);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }


}

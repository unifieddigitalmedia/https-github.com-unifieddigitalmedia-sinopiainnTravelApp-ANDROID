package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Itinerary extends Fragment {

    static ScaleBitMaps bitmapClass;
    JSONObject obj;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page_child, container, false);

        bitmapClass = new ScaleBitMaps(getActivity());

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        try {
             obj = new JSONObject(getArguments().getString("Place"));

            collapsingToolbar.setTitle(obj.getString("businessname"));

            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

            collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

            ImageView image = (ImageView) rootView.findViewById(R.id.image);

            //image.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), images[getArguments().getInt(ARG_OBJECT)], 100, 100));

            TextView descriptionTextView = (TextView) rootView.findViewById(R.id.section_label_descriptiontext);


            descriptionTextView.setText(obj.getString("businessdescription"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button cardButton = (Button) rootView.findViewById(R.id.card_button);

        TextView swipe = (TextView) rootView.findViewById(R.id.section_label_subtext);

        swipe.setVisibility(View.GONE);

         bundle = this.getArguments();

        Log.i("Activity", String.valueOf(bundle.getInt("Activity")));
        try {


            if (bundle.getInt("Activity") == 0) {


                if(((Activity_Home) getActivity()).checkItinerary(obj.getString("businessname")))

                {
                    cardButton.setText("REMOVE FROM ITINERARY");


                }else{


                    cardButton.setText("ADD TO ITINERARY");
                }

            }else{


                if(((Activity_CheckIn) getActivity()).checkItinerary(obj.getString("businessname")))

                {
                    cardButton.setText("REMOVE FROM ITINERARY");


                }else{


                    cardButton.setText("ADD TO ITINERARY");
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        cardButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (bundle.getInt("Activity") == 0) {


                    try {

                        if(((Activity_Home) getActivity()).checkItinerary(obj.getString("businessname")))

                        {

                            ((Activity_Home) getActivity()).itinerary.remove(((Activity_Home) getActivity()).placePos);

                            ((Activity_Home) getActivity()).getSupportFragmentManager().popBackStack();


                        }else{


                            ((Activity_Home) getActivity()).itinerary.put(obj);

                            ((Activity_Home) getActivity()).getSupportFragmentManager().popBackStack();

                        }


                    } catch (JSONException e) {

                        e.printStackTrace();

                    }



                } else {

                    try {

                        if(((Activity_CheckIn) getActivity()).checkItinerary(obj.getString("businessname")))

                        {
                            ((Activity_CheckIn) getActivity()).itinerary.remove(((Activity_CheckIn) getActivity()).placePos);

                            ((Activity_CheckIn) getActivity()).getSupportFragmentManager().popBackStack();

                        } else {

                            ((Activity_CheckIn) getActivity()).itinerary.put(obj);

                            ((Activity_CheckIn) getActivity()).getSupportFragmentManager().popBackStack();

                        }


                    } catch (JSONException e) {

                        e.printStackTrace();

                    }


                }


            }


        });


        return rootView;

    }

}

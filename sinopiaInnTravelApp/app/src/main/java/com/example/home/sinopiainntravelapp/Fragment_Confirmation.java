package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Confirmation extends Fragment {

    ImageView mainImage;

    ScaleBitMaps bitmapClass ;

    public Fragment_Confirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_confirmation, container, false);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        StringBuilder guestNme = new StringBuilder();

        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.welcome, 100, 100));


        guestNme.append("Welcome").append(" ").append(getArguments().getString("fname")).append(" ").append(getArguments().getString("lname"));

        collapsingToolbar.setTitle("Welcome");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((Activity_CheckIn)getActivity()).showOverlay();

            }
        });

        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));

        return rootView;
    }

}

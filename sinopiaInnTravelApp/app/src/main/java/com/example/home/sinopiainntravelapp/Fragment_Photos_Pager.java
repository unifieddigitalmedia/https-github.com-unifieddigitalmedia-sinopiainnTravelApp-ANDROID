package com.example.home.sinopiainntravelapp;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Photos_Pager extends Fragment {

    static Fragment_Photos.ImageItem imageItems = null;
    static ArrayList<String> bitmaps = null;
    static ArrayList<String> descriptions = null;

    static JSONArray description = null;

    static Boolean fromGridview;
    public Fragment_Photos_Pager() {
        // Required empty public constructor
    }

    static ScaleBitMaps bitmapClass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        bitmapClass = new ScaleBitMaps(getActivity());


        fromGridview = getArguments().getBoolean("fromGridview");

        if(!fromGridview){

            bitmaps = getArguments().getStringArrayList("Image Item");

            descriptions = getArguments().getStringArrayList("Image Description");


        }else{

            imageItems = getArguments().getParcelable("Image Item");


        }

        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mViewPager.setAdapter(new CollectionPagerAdapter(getChildFragmentManager()));

        return rootView;

    }

    static private class CollectionPagerAdapter extends FragmentStatePagerAdapter {


        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            Fragment fragment = new ChildFragment();

            Bundle args = new Bundle();

            args.putInt(ChildFragment.ARG_OBJECT, i);

            fragment.setArguments(args);

            return fragment;

        }

        @Override
        public int getCount() {



            if(!fromGridview){

                return bitmaps.size();
            }

            return 1;
        }

        static public class ChildFragment extends Fragment {

            public static final String ARG_OBJECT = "object";

            private String[] titles = {

                    "Rooms","Rewards And Recognition","Culinary Experience","You’ve one life. Why not live it ?"
            };

            private String[] buttonText = {

                    "BOOK NOW","BOOK NOW","GET RECEIPES","PLAN A TRIP"
            };

            private String[] descriptionText = {

                    "The rooms are fitted with double beds and ensuite bathrooms. In an environment that will give you relaxation and tranquility needed for a great nights sleep"
                    ,"What makes us truly exclusive are our offers, discounted rates and the amazing range of experiences – authentic adventures that explore behind the scenes and beneath the surface to discover Jamaica’s hidden gems. These unique experiences are specially tailored to immerse you in Portland’s surroundings and make your time with us unforgettable. Book with us today and start to receive promo codes for member-only rates and special offers"
                    ,"The taste of our traditional breakfast is real foodie territory.Its where you'll find rare and flavoursome West Indian dishes with a fusion of international spices from countries across the globe."
                    ,"Head to Portland Jamaica and plan your own trip with Dragon Bay Inn to venues across the island where you’ll find rare flavoursome dishes, estates of coffee premium liquors and a natural blend of fruits to produce a exotic taste made for paradise. Experience relaxing oceanic view, lush green mountain tops and skies streaking with colour as our island excursion include experimental trips; local culture; multi generation travel ; authentic activities and a personalised service provided by the local people of Jamaica."
            };

            private Integer[] images = {

                    R.drawable.room, R.drawable.rewards, R.drawable.food, R.drawable.travel,
            };


            @Override
            public View onCreateView(LayoutInflater inflater,
                                     ViewGroup container, Bundle savedInstanceState) {

                final View rootView = inflater.inflate(R.layout.fragment_home_pager_child, container, false);

                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

                collapsingToolbar.setTitle(titles[getArguments().getInt(ARG_OBJECT)]);

                collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

                collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

                final ImageView image = (ImageView) rootView.findViewById(R.id.image);



                if(fromGridview){

                    image.setImageBitmap(imageItems.getImage());

                    TextView descriptionTextView = (TextView) rootView.findViewById(R.id.section_label_descriptiontext);

                    descriptionTextView.setText(imageItems.getTitle());

                    TextView  section_label_locationtext = (TextView) rootView.findViewById(R.id.section_label_locationtext);



                }else{


                    new Thread() {
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        public void run() {


                            try {

                                try ( InputStream is = new URL(bitmaps.get(getArguments().getInt(ARG_OBJECT))).openStream() ) {

                                    final Bitmap bitmap = BitmapFactory.decodeStream( is );

                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {




                                            TextView descriptionTextView = (TextView) rootView.findViewById(R.id.section_label_descriptiontext);

                                            descriptionTextView.setText(descriptions.get(getArguments().getInt(ARG_OBJECT)));

                                            TextView  section_label_locationtext = (TextView) rootView.findViewById(R.id.section_label_locationtext);



                                            image.setImageBitmap(bitmap);

                                        }
                                    });


                                }
                            } catch (IOException e) {e.printStackTrace();

                            }




                        }
                    }.start();



                }




                return rootView;

            }

        }


    }



}

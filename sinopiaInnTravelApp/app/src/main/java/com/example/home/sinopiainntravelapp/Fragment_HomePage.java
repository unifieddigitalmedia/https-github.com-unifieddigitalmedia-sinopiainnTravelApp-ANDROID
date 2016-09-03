package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Fragment_HomePage extends Fragment {


    static ScaleBitMaps bitmapClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mViewPager.setAdapter(new CollectionPagerAdapter(getChildFragmentManager()));

        bitmapClass = new ScaleBitMaps(getActivity());

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
            return 4;
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

                View rootView = inflater.inflate(R.layout.fragment_home_page_child, container, false);

                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

                collapsingToolbar.setTitle(titles[getArguments().getInt(ARG_OBJECT)]);

                collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

                collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

                ImageView image = (ImageView) rootView.findViewById(R.id.image);

                image.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), images[getArguments().getInt(ARG_OBJECT)], 100, 100));

                TextView descriptionTextView = (TextView) rootView.findViewById(R.id.section_label_descriptiontext);

                Button cardButton = (Button) rootView.findViewById(R.id.card_button);

                descriptionTextView.setText(descriptionText[getArguments().getInt(ARG_OBJECT)]);

                cardButton.setText(buttonText[getArguments().getInt(ARG_OBJECT)]);

                cardButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        ((Activity_Home)getActivity()).gotoSubMenuPage(getArguments().getInt(ARG_OBJECT));


                    }


                });


                return rootView;

            }

        }


    }



}

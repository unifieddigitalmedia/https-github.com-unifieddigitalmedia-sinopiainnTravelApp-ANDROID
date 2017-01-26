package com.example.home.sinopiainntravelapp;


import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment_Confirmation extends Fragment {

    EditText fromDate;

    EditText toDate;

    ImageView mainImage;

    ScaleBitMaps bitmapClass ;

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView.ItemDecoration itemDecoration;

    String WEB_SERVICE_URL;

    Bundle bundle1;

    TextInputLayout fromDateError;
    TextInputLayout toDateError;

    ArrayList<String> bitmapArray ;

    JSONArray files;

    public String[] menu = {
            "Our Villa", "Local Time", "Local Weather - (* this feature of our app will be launching soon)", "Menu","Concierge","Bookshelf","Guest Book","Channels - (* this feature of our app will be launching soon)"


    };

    public Fragment_Confirmation() {

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_confirmation, container, false);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        bundle1 = new Bundle();



        bitmapArray = new ArrayList<String>();

        StringBuilder guestNme = new StringBuilder();

        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.welcome, 100, 100));


        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");


        String[] dateArray =  ((Activity_CheckIn) getActivity()).fromdate.split("-");

        String D = String.valueOf(Integer.parseInt(dateArray[2]) - 1900);




        Date date = new Date( Integer.parseInt(dateArray[2]) - 1900, Integer.parseInt(dateArray[1] ) - 1, Integer.parseInt(dateArray[0]));

        String ndate = formatter.format(date);

        fromDate = (EditText) rootView.findViewById(R.id.fromdate);

        fromDateError = (TextInputLayout) rootView.findViewById(R.id.fromDateError);

        toDateError = (TextInputLayout) rootView.findViewById(R.id.toDateError);


        fromDate.setText(ndate);

        toDate = (EditText) rootView.findViewById(R.id.todate);

        dateArray = ((Activity_CheckIn) getActivity()).todate.split("-");

        date = new Date( Integer.parseInt(dateArray[2]) - 1900, Integer.parseInt(dateArray[1])-1, Integer.parseInt(dateArray[0]));

        formatter = new SimpleDateFormat("EEE MMM dd yyyy");

        ndate = formatter.format(date);

        toDate.setText( ndate);

        //guestNme.append("Welcome").append(" ").append(((Activity_CheckIn) getActivity()).fname).append(" ").append(((Activity_CheckIn) getActivity()).lname);

        guestNme.append("Welcome").append(" ").append(((Activity_CheckIn) getActivity()).fname).append(" ").append(((Activity_CheckIn) getActivity()).lname);


        collapsingToolbar.setTitle(guestNme);

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.setNestedScrollingEnabled(false);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle bundle = this.getArguments();

        mAdapter = new listAdapter(menu);

        mRecyclerView.setAdapter(mAdapter);

        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        TabLayout tabLayout = (TabLayout)  rootView.findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Contact").setIcon(R.drawable.ic_record_voice_over_black_24dp), 0, true);

        tabLayout.addTab(tabLayout.newTab().setText("Map").setIcon(R.drawable.ic_map_24dp), 1, false);

        tabLayout.addTab(tabLayout.newTab().setText("Photos").setIcon(R.drawable.ic_photo_library_black_24dp), 2, false);

        tabLayout.setTabTextColors(Color.argb(999,153,153,51), Color.argb(999,153,153,51));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            tabLayout.getTabAt(0).getIcon().setTint(Color.argb(999,153,153,51));
            tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(android.R.color.black));
            tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(android.R.color.black));


        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {
                    case 0:


                        goToChat();
                        break;

                    case 1:

                        goToMap();
                        break;

                    case 2:

                        goToPhotos();

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

                    goToChat();

                } else if (tab.getPosition() == 1)


                {

                    goToMap();

                } else if (tab.getPosition() == 2)


                {

                    goToPhotos();



                }


            }
        });

        return rootView;

    }

    public void goToChat () {




        ((Activity_CheckIn) getActivity()).goTochat();



    }

    public void goToMap () {

        Fragment_Map new_fragment = new Fragment_Map();

        bundle1.putDouble("latitude",18.166716);

        bundle1.putDouble("longitude",-76.380764);

        new_fragment.setArguments(bundle1);


            ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment,"");





    }

    public void goToPhotos ()  {

    Fragment_Photos new_fragment = new Fragment_Photos();

    bundle1.putInt("Menu", 10);

    //bundle1.putStringArrayList("photo_files",((Activity_CheckIn) getActivity()).bitmapArray);

    new_fragment.setArguments(bundle1);

    ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment,"");




}



    public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {

        private JSONArray[] foodDataset;

        private Integer[] icons = {

                R.drawable.ic_logo, R.drawable.ic_watch_later_black_24dp, R.drawable.ic_cloud_black_24dp, R.drawable.ic_restaurant_black_24dp, R.drawable.ic_airport_shuttle_black_24dp, R.drawable.ic_bookmark_black_24dp,R.drawable.ic_local_library_24dp, R.drawable.ic_local_movies_24dp,  R.drawable.ic_shopping_basket_24dp
        };


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView;

            public ImageView right, logo;

            private Integer[] Right = {

                    R.drawable.ic_done_24dp
            };


            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);

                mTextView = (TextView) itemView.findViewById(R.id.name);

                right = (ImageView) itemView.findViewById(R.id.right);

                logo = (ImageView) itemView.findViewById(R.id.image);

            }


            @Override
            public void onClick(View v) {


                final int position = getLayoutPosition();

                switch (position) {

                    case 0:


                        ((Activity_CheckIn) getActivity()).goToWeb("http://www.sinopiainn.com/our-villa.html");


                        break;

                    case 1:


                        Fragment_Time time_fragment = new Fragment_Time();


                        ((Activity_CheckIn) getActivity()).homePageFadeTransition(time_fragment,"");


                    case 2:


                        /*Fragment_Time weather_fragment = new Fragment_Time();


                        ((Activity_CheckIn) getActivity()).homePageFadeTransition(weather_fragment,"");
*/

                        break;
                    case 3:



                        WEB_SERVICE_URL = "http://www.sinopiainn.com/api/menu";

                        ((Activity_CheckIn) getActivity()).selectMenuItem(position,WEB_SERVICE_URL);



                        break;
                    case 4:

                        WEB_SERVICE_URL = "http://www.sinopiainn.com/api/businesses";


                        ((Activity_CheckIn) getActivity()).selectMenuItem(position,WEB_SERVICE_URL);


                        break;
                    case 5:


                        WEB_SERVICE_URL = "http://www.sinopiainn.com/api/books";


                        ((Activity_CheckIn) getActivity()).selectMenuItem(position,WEB_SERVICE_URL);



                        break;

                    case 6:


                        WEB_SERVICE_URL = "http://www.sinopiainn.com/api/reviews";


                        ((Activity_CheckIn) getActivity()).selectMenuItem(position,WEB_SERVICE_URL);


                        break;

                    case 7:


                       /* WEB_SERVICE_URL = "http://www.sinopiainn.com/api/tv";


                        ((Activity_CheckIn) getActivity()).selectMenuItem(position,WEB_SERVICE_URL);

*/

                        break;


                }



            }


        }

        public listAdapter(Object p0) {

        }

        @Override
        public listAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmationlistitemamentitylayout, parent, false);

            ViewHolder vh = new ViewHolder(v);



            return vh;



        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            holder.logo.setImageResource(icons[position]);

            holder.mTextView.setText(menu[position]);

            holder.right.setImageResource(R.drawable.ic_chevron_right_24dp);


        }

        @Override
        public int getItemCount() {

            return menu.length;

        }




    }



}

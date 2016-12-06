package com.example.home.sinopiainntravelapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Itinerary extends Fragment {


    public Fragment_Itinerary() {
        // Required empty public constructor
    }


    static ScaleBitMaps bitmapClass;
    JSONObject obj;
    Bundle bundle;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView.ItemDecoration itemDecoration;


    Bundle bundle1;

    public String[] placeItems ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_itinerary_page_child, container, false);

        bundle1 = new Bundle();

        bitmapClass = new ScaleBitMaps(getActivity());

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        placeItems =  new String[4];

        try {

            obj = new JSONObject(getArguments().getString("Place"));

            collapsingToolbar.setTitle(obj.getString("businessname"));

            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

            collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

            ImageView image = (ImageView) rootView.findViewById(R.id.image);

            //image.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), images[getArguments().getInt(ARG_OBJECT)], 100, 100));

            TextView descriptionTextView = (TextView) rootView.findViewById(R.id.section_label_descriptiontext);

            descriptionTextView.setText(obj.getString("businessdescription"));

            placeItems[0] = obj.getString("businessphone");

            placeItems[1] = obj.getString("businessemail");

            placeItems[2] = obj.getString("businesswebsite");

            placeItems[3] = obj.getString("businessaddress");

            JSONObject coordinates = new JSONObject(obj.getString("coordinates"));

            bundle1.putDouble("latitude",coordinates.getDouble("Latitude"));

            bundle1.putDouble("longitude",coordinates.getDouble("Longitude"));

            bundle1.putString("title",obj.getString("businessname"));

            bundle1.putString("url",obj.getString("businesswebsite"));




        } catch (JSONException e) {

            e.printStackTrace();

        }

        Button cardButton = (Button) rootView.findViewById(R.id.card_button);

        bundle = this.getArguments();

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


            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);



        mAdapter = new listAdapter(placeItems);

        mRecyclerView.setAdapter(mAdapter);



        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        return rootView;


    }


    public void goToWeb () {

        Fragment_Web new_fragment = new Fragment_Web();

        new_fragment.setArguments(bundle1);



        if(getArguments().getInt("Activity") == 0) {




                ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment,"");




        }else{




                ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment,"");


        }




    }


    public void goToMap () {

        Fragment_Map new_fragment = new Fragment_Map();

        new_fragment.setArguments(bundle1);


        if(getArguments().getInt("Activity") == 0) {



                ((Activity_Home) getActivity()).homePageFadeTransition( new_fragment,"");




        }else{



                ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment,"");




        }



    }


    public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {

        private JSONArray[] foodDataset;

        private Integer[] icons = {

                R.drawable.ic_local_phone_black_24dp, R.drawable.ic_email_black_24dp, R.drawable.ic_public_black_24dp,R.drawable.ic_map_24dp,R.drawable.ic_local_library_24dp,R.drawable.ic_shopping_basket_24dp
        };



        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView ;

            public ImageView right ,logo;

            private Integer[] Right = {

                    R.drawable.ic_chevron_right_24dp
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


;

                switch (getLayoutPosition()) {
                    case 0:


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" +  bundle.getString(placeItems[(int) v.getTag()])));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);

                        break;

                    case 1:



                        break;

                    case 2:

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( placeItems[(int) v.getTag()]));
                        startActivity(browserIntent);

                        break;
                    case 3:

                    goToMap();

                        break;

                }


            }


        }

        public listAdapter(Object p0) {

        }

        @Override
        public listAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view



            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemamentitylayout, parent, false);

            ViewHolder vh = new ViewHolder(v);



            return vh;



        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {




            holder.itemView.setTag(position);
            holder.logo.setImageResource(icons[position]);
            holder.mTextView.setText(placeItems[position]);

            holder.right.setImageResource(R.drawable.ic_chevron_right_24dp);




        }

        @Override
        public int getItemCount() {

            return placeItems.length;

        }




    }



}

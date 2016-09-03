package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_Amenities extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;

    ImageView mainImage;
    ScaleBitMaps bitmapClass ;
    ArrayList<String> roomlist;
    public JSONArray rooms = null;

    JSONArray Jsonroomlist;


    public Fragment_Amenities() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);

        Jsonroomlist = new JSONArray();

        roomlist = new ArrayList<>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        // mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.breakfast, 100, 100));

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Available Amenities");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ((Activity_Home)getActivity()).addAmenities(new JSONArray(roomlist.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));



        try {


            rooms = new JSONArray(getArguments().getString("Rooms"));

            mAdapter = new listAdapter(rooms);

            mRecyclerView.setAdapter(mAdapter);


        } catch (JSONException e) {


            e.printStackTrace();


        }


        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        return rootView;


    }


    public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {

        private JSONArray[] foodDataset;

        private Integer[] icons = {

                R.drawable.ic_restaurant_black_24dp, R.drawable.ic_airport_shuttle_black_24dp, R.drawable.ic_directions_car_24dp,R.drawable.ic_local_movies_24dp,R.drawable.ic_local_library_24dp,R.drawable.ic_shopping_basket_24dp
        };



        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView ;

            public ImageView right ,logo;

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




                try {

                    JSONObject f = (JSONObject) rooms.get(getLayoutPosition());


                    if (roomlist.contains(rooms.get(getLayoutPosition()).toString())) {

                        right.setImageResource(0);

                        roomlist.remove(rooms.get(getLayoutPosition()).toString());

                    }else {

                        right.setImageResource(Right[0]);

                        roomlist.add(rooms.get(getLayoutPosition()).toString());
                    }



                } catch (JSONException e) {

                    e.printStackTrace();

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




            try {

                JSONObject room = (JSONObject) rooms.get(position);
                holder.logo.setImageResource(icons[position]);
                holder.mTextView.setText(room.getString("name"));

            } catch (JSONException e) {

                e.printStackTrace();

            }



        }

        @Override
        public int getItemCount() {

            return rooms.length();

        }




    }



}

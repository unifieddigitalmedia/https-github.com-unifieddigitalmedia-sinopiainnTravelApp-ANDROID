package com.example.home.sinopiainntravelapp;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;



public class Fragment_Timeline extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    ImageView mainImage;
    ScaleBitMaps bitmapClass ;
    ArrayList<String> rooms = null;
    ArrayList<String> roomlist;
    ArrayList<String> bitmaps = null;
    ArrayList<String> images = null;


    public Fragment_Timeline() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);

        roomlist = new ArrayList<>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);


        mainImage = (ImageView) rootView.findViewById(R.id.image);

        bitmapClass = new ScaleBitMaps(getActivity());

        rooms = getArguments().getStringArrayList("photo_files"); //getString("photo_files"));


        JSONObject room = null;

        try {
            room = new JSONObject(rooms.get(0));


            bitmaps = new ArrayList<String>(Arrays.asList(room.getString("BITMAPS").substring(1, room.getString("BITMAPS").length()-1).split(","))) ;


            new Thread() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                public void run() {


                    try {

                        try ( InputStream is = new URL(bitmaps.get(0)).openStream() ) {

                            final Bitmap bitmap = BitmapFactory.decodeStream( is );
                            //  holder.logo.setImageBitmap(BitmapFactory.decodeFile(bitmaps.get(0)));

                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {


                                    mainImage.setImageBitmap(bitmap);

                                }
                            });


                        }


                    } catch (IOException e) {e.printStackTrace();

                    }




                }
            }.start();

        } catch (JSONException e) {
            e.printStackTrace();
        }





        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Your Timeline");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setVisibility(View.GONE);



            mAdapter = new listAdapter(rooms);

            mRecyclerView.setAdapter(mAdapter);



        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);






        return rootView;


    }


    public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {

        private JSONArray[] foodDataset;

        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView place , location ,date;

            public ImageView logo;

            RelativeLayout overlay;

            CircularProgressView progressView;


            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);

                place = (TextView) itemView.findViewById(R.id.place);

                location = (TextView) itemView.findViewById(R.id.location);

                date = (TextView) itemView.findViewById(R.id.date);

                logo = (ImageView) itemView.findViewById(R.id.image);

                progressView = (CircularProgressView) itemView.findViewById(R.id.progress_view);

                overlay = (RelativeLayout) itemView.findViewById(R.id.overlay);

                overlay.setVisibility(View.VISIBLE);

                progressView.startAnimation();



            }


            @Override
            public void onClick(View v) {

                JSONObject room = null;

                try {

                    room = new JSONObject(rooms.get(getLayoutPosition()));


                    StringBuilder item = new StringBuilder();

                    bitmaps = new ArrayList<String>(Arrays.asList(room.getString("BITMAPS").substring(1, room.getString("BITMAPS").length()-1).split(","))) ;

                    images = new ArrayList<String>(Arrays.asList(room.getString("DESCRIPTIONS").substring(1, room.getString("DESCRIPTIONS").length()-1).split(","))) ;


                    //Fragment_Photos_Pager new_fragment = new Fragment_Photos_Pager();

                    Fragment_Photos new_fragment = new Fragment_Photos();

                    Bundle bundle1 = new Bundle();

                    bundle1.putStringArrayList("Image Item",bitmaps);

                    bundle1.putStringArrayList("Image Description",images);

                    bundle1.putBoolean("fromGridview",false);

                    new_fragment.setArguments(bundle1);

                    ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment,"");


                } catch (JSONException e) {
                    e.printStackTrace();
                }







            }


        }

        public listAdapter(Object p0) {

        }

        @Override
        public listAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fragment__timeline, parent, false);

            ViewHolder vh = new ViewHolder(v);



            return vh;



        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {




            try {

                JSONObject room = new JSONObject(rooms.get(position));


                StringBuilder item = new StringBuilder();


                bitmaps = new ArrayList<String>(Arrays.asList(room.getString("BITMAPS").substring(1, room.getString("BITMAPS").length()-1).split(","))) ;


                new Thread() {
                    public void run() {


                        try {

                            try ( InputStream is = new URL(bitmaps.get(0)).openStream() ) {

                                final Bitmap bitmap = BitmapFactory.decodeStream( is );

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        holder.logo.setImageBitmap(bitmap);

                                        holder.overlay.setVisibility(View.GONE);

                                        holder.progressView.stopAnimation();
                                    }
                                });


                            }
                        } catch (IOException e) {e.printStackTrace();

                        }




                    }
                }.start();

                holder.place.setText(room.getString("name"));

                holder.location.setText(room.getString("location"));

                holder.date.setText("");


            } catch (JSONException e) {

                e.printStackTrace();

            }


        }

        @Override
        public int getItemCount() {

            return rooms.size();

        }




    }



}

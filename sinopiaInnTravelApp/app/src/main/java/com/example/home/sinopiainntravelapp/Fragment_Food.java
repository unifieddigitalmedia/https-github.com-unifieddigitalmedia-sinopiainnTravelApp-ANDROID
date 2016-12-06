package com.example.home.sinopiainntravelapp;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Food extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;

    public Integer[] icons = {

            R.drawable.ic_star_outline_24dp, R.drawable.ic_star_24dp,  };
    Bundle bundle;

    private static String[] foods = {

            "BOOK NOW","BOOK NOW","GET RECEIPES","BOOK NOW"
    };


    ImageView mainImage;

    ScaleBitMaps bitmapClass ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Our Menu");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));

        if(getArguments().getInt("Menu") == 0){


            fab.setImageResource(R.drawable.ic_record_voice_over_black_24dp);

            fab.setVisibility(View.GONE);

        }else if (getArguments().getInt("Menu") == 2){


                fab.setVisibility(View.GONE);




        }else if (getArguments().getInt("Menu") == 6) {


            fab.setImageResource(R.drawable.ic_edit_black_24dp);



        }

        else{


            fab.setImageResource(R.drawable.ic_done_24dp);

            fab.setVisibility(View.GONE);

        }


             if(getArguments().getInt("Menu") == 6){


                 fab.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {



                         ((Activity_CheckIn)getActivity()).homePageFadeTransition(new Fragment_New_Comment(),"");


                }

                 });




                //((checkIn) getActivity()).goToChat();


            }





        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        if(getArguments().getInt("Menu") != 2 ) {

            mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.chef, 100, 100));

        }else{


            mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.chef, 100, 100));

        }

        mRecyclerView.setHasFixedSize(true);

        if(getArguments().getInt("Menu") == 6) {


            mLayoutManager = new LinearLayoutManager(getActivity());



        }else {


            //mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

            mLayoutManager = new LinearLayoutManager(getActivity());

        }

        mRecyclerView.setLayoutManager(mLayoutManager);



        try {

            mAdapter = new foodAdapter(getActivity(), new JSONArray(this.getArguments().getString("Json")));


        } catch (JSONException e) {

            e.printStackTrace();

        }

        mRecyclerView.setAdapter(mAdapter);

        return rootView;


    }


    private class foodAdapter extends RecyclerView.Adapter<foodAdapter.ViewHolder> {

        private JSONArray foodDataset;
        public ImageView rating1,rating2,rating3,rating4,rating5 ;
        private Random mRandom = new Random();

        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView , nameTextView , dateTextView , commentTextView;

            public ImageView mImageView ;
            public ImageView rating1,rating2,rating3,rating4,rating5 ;


            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);

                if(getArguments().getInt("Menu") != 6){

                    mTextView = (TextView) itemView.findViewById(R.id.foodName);

                    mImageView = (ImageView) itemView.findViewById(R.id.placeHolder);



                }else{



                    nameTextView = (TextView) itemView.findViewById(R.id.name);

                    dateTextView = (TextView) itemView.findViewById(R.id.date);

                    commentTextView = (TextView) itemView.findViewById(R.id.comment);

                    rating1 = (ImageView) itemView.findViewById(R.id.rating1);


                    rating2 = (ImageView) itemView.findViewById(R.id.rating2);


                    rating3 = (ImageView) itemView.findViewById(R.id.rating3);


                    rating4 = (ImageView) itemView.findViewById(R.id.rating4);


                    rating5 = (ImageView) itemView.findViewById(R.id.rating5);


                }


            }


            @Override
            public void onClick(View v) {

                if(getArguments().getInt("Menu") != 6){

                    ImageView staticImage = (ImageView) v.findViewById(R.id.placeHolder);

                    Fragment_Food_Category_List new_fragment = new Fragment_Food_Category_List();

                    Bundle bundle1 = new Bundle();

                    //foodDataset.get(getLayoutPosition())

                    try {


                        JSONObject f = (JSONObject) foodDataset.get(getLayoutPosition());

                        JSONArray list = new JSONArray(f.getString("items"));

                        bundle1.putString("List", f.getString("items"));

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                    bundle1.putInt("Activity", getArguments().getInt("Activity"));

                    bundle1.putInt("Menu", getArguments().getInt("Menu"));

                    new_fragment.setArguments(bundle1);



                    //

                    if(getArguments().getInt("Activity") == 0){


                        ((Activity_Home)getActivity()).homePageReplaceFragment(new_fragment, staticImage);


                    }else {

                        ((Activity_CheckIn)getActivity()).homePageReplaceFragment(new_fragment,staticImage);

                    }




                }else{

                  Fragment_Comment new_fragment = new Fragment_Comment();

                    Bundle bundle1 = new Bundle();

                    try {


                        JSONObject f = (JSONObject) foodDataset.get(getLayoutPosition());

                        bundle1.putString("List",f.toString() );

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }


                    new_fragment.setArguments(bundle1);


                   // ((Activity_CheckIn)getActivity()).homePageFadeTransition(new_fragment);

                }


            }

        }

        public foodAdapter(Object p0) {

        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public foodAdapter(Context context, JSONArray foodset) {

            foodDataset = foodset;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v;


            if(getArguments().getInt("Menu") != 6){

                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodlayout, parent, false);


            }else{


                 v = LayoutInflater.from(parent.getContext()).inflate(R.layout.guestbooklayout, parent, false);

            }

            ViewHolder vh = new ViewHolder(v);

            return vh;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            if(getArguments().getInt("Menu") != 6) {





                    try {

                        JSONObject item = (JSONObject) foodDataset.get(position);

                        final JSONObject firstItem  =  (JSONObject) item.getJSONArray("items").get(0);

                        new Thread() {
                            public void run() {


                                try {

                                    try ( InputStream is = new URL(firstItem.getString("image_url").replaceAll(" ", "%20")).openStream() ) {

                                        final Bitmap bitmap = BitmapFactory.decodeStream( is );
                                        //  holder.logo.setImageBitmap(BitmapFactory.decodeFile(bitmaps.get(0)));

                                        getActivity().runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {



                                                holder.mImageView.setImageBitmap(bitmap);

                                            }
                                        });


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } catch (IOException e) {e.printStackTrace();

                                }




                            }
                        }.start();






                    holder.mTextView.setText(item.getString("type"));


                } catch (JSONException e) {

                    e.printStackTrace();

                }

                holder.mTextView.getLayoutParams().height = getRandomIntInRange(250, 75);



            }else{

                try {

                    JSONObject item = (JSONObject) foodDataset.get(position);


                    holder.nameTextView.setText(item.getString("name"));

                    holder.dateTextView.setText(item.getString("date"));

                    holder.commentTextView.setText(item.getString("comment"));

                    switch (Integer.parseInt(item.getString("rating"))) {
                        case 0:

                            holder.rating1.setImageResource(icons[0]);
                            holder.rating2.setImageResource(icons[0]);
                            holder.rating3.setImageResource(icons[0]);
                            holder.rating4.setImageResource(icons[0]);
                            holder.rating5.setImageResource(icons[0]);

                            break;

                        case 1:

                            holder.rating1.setImageResource(icons[1]);
                            holder.rating2.setImageResource(icons[0]);
                            holder.rating3.setImageResource(icons[0]);
                            holder.rating4.setImageResource(icons[0]);
                            holder.rating5.setImageResource(icons[0]);

                            break;

                        case 2:

                            holder.rating1.setImageResource(icons[1]);
                            holder.rating2.setImageResource(icons[1]);
                            holder.rating3.setImageResource(icons[0]);
                            holder.rating4.setImageResource(icons[0]);
                            holder.rating5.setImageResource(icons[0]);

                            break;

                        case 3:

                            holder.rating1.setImageResource(icons[1]);
                            holder.rating2.setImageResource(icons[1]);
                            holder.rating3.setImageResource(icons[1]);
                            holder.rating4.setImageResource(icons[0]);
                            holder.rating5.setImageResource(icons[0]);

                            break;

                        case 4:

                            holder.rating1.setImageResource(icons[1]);
                            holder.rating2.setImageResource(icons[1]);
                            holder.rating3.setImageResource(icons[1]);
                            holder.rating4.setImageResource(icons[1]);
                            holder.rating5.setImageResource(icons[0]);

                            break;

                        case 5:

                            holder.rating1.setImageResource(icons[1]);
                            holder.rating2.setImageResource(icons[1]);
                            holder.rating3.setImageResource(icons[1]);
                            holder.rating4.setImageResource(icons[1]);
                            holder.rating5.setImageResource(icons[1]);

                            break;



                    }


                } catch (JSONException e) {

                    e.printStackTrace();

                }




            }



        }

        // Custom method to get a random number between a range
        protected int getRandomIntInRange(int max, int min) {
            return mRandom.nextInt((max - min) + min) + min;
        }


        // Replace the contents of a view (invoked by the layout manager)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return foodDataset.length();
        }

    }
}

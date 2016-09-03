package com.example.home.sinopiainntravelapp;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Bookshelf extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;

    Bundle bundle;

    private static String[] authors = {

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //((checkIn) getActivity()).goToChat();


            }
        });



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);


        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        // mainImage.setImageResource(R.drawable.chef);

        // mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.chef, 100, 100));


        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        mRecyclerView.setLayoutManager(mLayoutManager);

        bundle = this.getArguments();


        try {

            mAdapter = new foodAdapter(getActivity(), new JSONArray(bundle.getString("Json")));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //mAdapter = new foodAdapter(getActivity(),authors);

        mRecyclerView.setAdapter(mAdapter);


        bundle = this.getArguments();

        return rootView;
    }


    private class foodAdapter extends RecyclerView.Adapter<foodAdapter.ViewHolder> {

        private JSONArray foodDataset;
        private Random mRandom = new Random();

        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView ;


            public ImageView mImageView ;

            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);

                mTextView = (TextView) itemView.findViewById(R.id.foodName);

                mImageView = (ImageView) itemView.findViewById(R.id.placeHolder);

            }


            @Override
            public void onClick(View v) {


                ImageView staticImage = (ImageView) v.findViewById(R.id.placeHolder);

                Fragment_Food_Category_List new_fragment = new Fragment_Food_Category_List();

                Bundle bundle1 = new Bundle();

                bundle1.putInt("Activity", bundle.getInt("Activity"));

                bundle1.putInt("Position", getLayoutPosition());

                try {
                    bundle1.putString("List", String.valueOf(foodDataset.get(getLayoutPosition())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new_fragment.setArguments(bundle1);



                //

                if(bundle.getInt("Activity") == 0){


                    ((Activity_Home)getActivity()).homePageReplaceFragment(new_fragment, staticImage);


                }else {

                    ((Activity_CheckIn)getActivity()).homePageReplaceFragment(new_fragment,staticImage);

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

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodlayout, parent, false);

            ViewHolder vh = new ViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            try {

                JSONObject item = (JSONObject) foodDataset.get(position);


                holder.mTextView.setText(item.getString("type"));

            } catch (JSONException e) {

                e.printStackTrace();

            }

            holder.mTextView.getLayoutParams().height = getRandomIntInRange(250, 75);

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

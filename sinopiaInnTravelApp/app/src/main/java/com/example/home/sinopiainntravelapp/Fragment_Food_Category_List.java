package com.example.home.sinopiainntravelapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import layout.Fragment_Book_Description;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Food_Category_List extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;

    Bundle bundle;
    ImageView mainImage;
    ScaleBitMaps bitmapClass ;

    private String[] foods = {

            "Mackrel Run Down","Coffee" };


    public Fragment_Food_Category_List() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        // mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.chef, 100, 100));

        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(getActivity());


        mRecyclerView.setLayoutManager(mLayoutManager);


        bundle = this.getArguments();

        try {


            mAdapter = new listAdapterFoodCatergory(new JSONArray(getArguments().getString("List")));


        } catch (JSONException e) {

            e.printStackTrace();

        }


        mRecyclerView.setAdapter(mAdapter);


        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

if(!getActivity().getTitle().toString().matches("Activity_Home")){


    fab.setVisibility(View.GONE);

}



        return rootView;


    }

    private class listAdapterFoodCatergory extends RecyclerView.Adapter<listAdapterFoodCatergory.ViewHolder> {

        private JSONArray foodDataset;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView , descriptionTextView;

            public ImageView right ,logo;




            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);


                mTextView = (TextView) itemView.findViewById(R.id.name);
                descriptionTextView = (TextView) itemView.findViewById(R.id.description);
                logo = (ImageView) itemView.findViewById(R.id.image);

            }


            @Override
            public void onClick(View v) {


                if(getArguments().getInt("Menu") == 3) {


                    Listview_Item_Description new_fragment = new Listview_Item_Description();

                    Bundle bundle1 = new Bundle();

                    bundle1.putInt("Activity",bundle.getInt("Activity"));

                    bundle1.putInt("Activity",bundle.getInt("Activity"));
                    //bundle1.putString("Meta", String.valueOf((JSONObject) new JSONArray(String.valueOf(foodDataset.get(getLayoutPosition()))).get(0)));


                    try {
                        bundle1.putString("Meta", String.valueOf((JSONObject) foodDataset.get(getLayoutPosition())));


                        bundle1.putInt("Menu", bundle.getInt("Menu"));

                        new_fragment.setArguments(bundle1);

                        if(bundle.getInt("Activity") == 0) {


                            ((Activity_Home)getActivity()).homePageFadeTransition(new_fragment,"");


                        }else {

                            ((Activity_CheckIn)getActivity()).homePageFadeTransition(new_fragment,"");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{





                try {

                    ImageView staticImage = (ImageView) v.findViewById(R.id.image);


                    Fragment_Book_Description new_fragment = new Fragment_Book_Description();

                    Bundle bundle1 = new Bundle();

                    bundle1.putInt("Activity",bundle.getInt("Activity"));
                    //bundle1.putString("Meta", String.valueOf((JSONObject) new JSONArray(String.valueOf(foodDataset.get(getLayoutPosition()))).get(0)));


                    bundle1.putString("Meta", String.valueOf((JSONObject) foodDataset.get(getLayoutPosition())));

                    bundle1.putInt("Menu", bundle.getInt("Menu"));

                    new_fragment.setArguments(bundle1);

                    if(bundle.getInt("Activity") == 0) {


                        ((Activity_Home)getActivity()).homePageFadeTransition(new_fragment,"");


                    }else {

                        ((Activity_CheckIn)getActivity()).homePageFadeTransition(new_fragment,"");

                    }
                } catch (JSONException e) {

                    e.printStackTrace();

                }





                }



            }

        }

        public listAdapterFoodCatergory(Object p0) {

        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public listAdapterFoodCatergory(JSONArray foodset) {

            foodDataset = foodset;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.catergorylist, parent, false);

            ViewHolder vh = new ViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {


              //  JSONObject f = (JSONObject) new JSONArray(String.valueOf(foodDataset.get(position))).get(0);



            try {

                final JSONObject f = (JSONObject) foodDataset.get(position);
                holder.mTextView.setText(f.getString("name"));
                holder.descriptionTextView.setText(f.getString("description"));


            new Thread() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                public void run() {


                    try {

                        try ( InputStream is = new URL(f.getString("image_url")).openStream() ) {

                            final Bitmap bitmap = BitmapFactory.decodeStream( is );
                            //  holder.logo.setImageBitmap(BitmapFactory.decodeFile(bitmaps.get(0)));

                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    holder.logo.setImageBitmap(bitmap);


                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {e.printStackTrace();

                    }




                }
            }.start();

            } catch (JSONException e) {
                e.printStackTrace();
            }



            //URL url = new URL(f.getString("image_url"));
                //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //holder.logo.setImageBitmap(bmp);




        }

        @Override
        public int getItemCount() {
            return foodDataset.length();
        }


    }

}

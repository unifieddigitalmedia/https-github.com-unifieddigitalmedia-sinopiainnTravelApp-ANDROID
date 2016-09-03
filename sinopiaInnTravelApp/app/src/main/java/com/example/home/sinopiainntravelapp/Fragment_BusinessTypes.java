package com.example.home.sinopiainntravelapp;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_BusinessTypes extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;


    public JSONArray rooms = null;
    public JSONArray icons = null;




    ArrayList<String> Jsonroomlist;

    ArrayFilter businessFilter ;

    private ArrayList<String> foodDataset;

    ArrayList<String> roomlist;

    Bundle bundle;


    public Fragment_BusinessTypes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);

        Jsonroomlist = new ArrayList<String>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);


        /*responseArray = new JSONArray(getIntent().getExtras().getString("ROOMS"));*/

        //rooms = new JSONArray('');

        bundle = this.getArguments();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bundle.getInt("Activity") == 0) {

                    ((Activity_Home) getActivity()).addBuisnessTypes(Jsonroomlist, 0);

                }
                else {

                    ((Activity_CheckIn) getActivity()).addBuisnessTypes(Jsonroomlist, 0);

                }



            }
        });


        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));



        try {




            String images = "[{ \"Type\":\"Culture\",\"Image\":\"ic_local_see_24dp\" },{ \"Type\":\"Beaches\",\"Image\":\"ic_beach_access_black_24dp\"},{ \"Type\":\"Event\",\"Image\":\"ic_event_seat_black_24dp\"},{ \"Type\":\"Food and Drink\",\"Image\":\"ic_restaurant_black_24dp\"},{ \"Type\":\"Lesiure\",\"Image\":\"ic_local_activity_black_24dp\"},{ \"Type\":\"Nightlife\",\"Image\":\"\"},{ \"Type\":\"Shopping\",\"Image\":\"ic_favorite_border_black_24dp\" }]";
            icons = new JSONArray(images);


            Bundle bundle1 = this.getArguments();

            rooms = new JSONArray( bundle1.getString("BuinsessTypes"));

            businessFilter = new ArrayFilter();

            foodDataset = businessFilter.businessfilterList(rooms);

            mAdapter = new listAdapter(foodDataset);

            mRecyclerView.setAdapter(mAdapter);



        } catch (JSONException e) {


            e.printStackTrace();


        }


        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);





        return rootView;


    }


    public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {



        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView ;

            public ImageView right,logo ;

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



                if (Jsonroomlist.contains(foodDataset.get(getLayoutPosition()))) {

                    right.setImageResource(0);


                    Jsonroomlist.remove(foodDataset.get(getLayoutPosition()));

                }else {

                    //holder.right.setImageResource(Right[0]);


                    right.setImageResource(Right[0]);


                    Jsonroomlist.add(foodDataset.get(getLayoutPosition()));


                }








            }


        }


        // Provide a suitable constructor (depends on the kind of dataset)
        public listAdapter(Context context, ArrayList<String> foodset) {

            foodDataset = foodset;



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

            holder.mTextView.setText(foodDataset.get(position));

            for(int j = 0; j < icons.length() ; j++) {


                JSONObject IC = (JSONObject) icons.get(j);


            if(IC.get("Type").toString().matches(foodDataset.get(position))) {


                Log.i("BuinsessTypesIcon",IC.get("Image").toString());

                holder.logo.setImageResource(getResources().getIdentifier(IC.get("Image").toString(),"drawable","com.example.home.sinopiainntravelapp"));


            }





            }




        } catch (JSONException e) {
            e.printStackTrace();
        }



        }

        @Override
        public int getItemCount() {

            return foodDataset.size();

        }




    }



}

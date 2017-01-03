package com.example.home.sinopiainntravelapp;


import android.content.Context;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Services extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;


    ArrayList<String> roomlist;
    public JSONArray rooms = null;

    ArrayList<String> Jsonroomlist;

    ArrayFilter businessFilter ;

    private JSONArray foodDataset;

    Bundle bundle;

    public JSONArray icons = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

                try {

                    if(bundle.getInt("Activity") == 0) {


                        ((Activity_Home) getActivity()).addBuisnessServices(Jsonroomlist,new JSONArray(bundle.getString("BuinsessTypes")));

                    }
                    else {

                        ((Activity_CheckIn) getActivity()).addBuisnessServices(Jsonroomlist,new JSONArray(bundle.getString("BuinsessTypes")));

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }

                //  ((homePage)getActivity()).addBuisnessTypes(foodDataset, bundle.getInt("Fragment"));

            }
        });

        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));


        try {

            rooms = new JSONArray(bundle.getString("BuinsessTypes"));

            businessFilter = new ArrayFilter();

            foodDataset = businessFilter.filterList(rooms,bundle.getStringArrayList("types"),1);

            mAdapter = new listAdapter(foodDataset);

            mRecyclerView.setAdapter(mAdapter);

            String images = "[{ \"Type\":\"Culture\",\"Image\":\"ic_local_see_24dp\" },{ \"Type\":\"Beaches\",\"Image\":\"ic_beach_access_black_24dp\"},{ \"Type\":\"Event\",\"Image\":\"ic_event_seat_black_24dp\"},{ \"Type\":\"Food and Drink\",\"Image\":\"ic_restaurant_black_24dp\"},{ \"Type\":\"Lesiure and Nightlife\",\"Image\":\"ic_local_activity_black_24dp\"},{ \"Type\":\"Nightlife\",\"Image\":\"\"},{ \"Type\":\"Shopping\",\"Image\":\"ic_favorite_border_black_24dp\" }]";

            icons = new JSONArray(images);


        } catch (JSONException e) {


            e.printStackTrace();


        }


        itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        ScaleBitMaps bitmapClass = new ScaleBitMaps(getActivity());

        ImageView image = (ImageView) rootView.findViewById(R.id.image);

        bitmapClass = new ScaleBitMaps(getActivity());

        image.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(),R.drawable.jamaica_map_800, 100, 100));


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


                try {
                    JSONObject room = (JSONObject) foodDataset.get(getLayoutPosition());


                    if (Jsonroomlist.contains(room.getString("service"))) {

                        right.setImageResource(0);


                        Jsonroomlist.remove(room.getString("service"));

                    }else {

                        //holder.right.setImageResource(Right[0]);


                        right.setImageResource(Right[0]);


                        Jsonroomlist.add(room.getString("service"));


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }


        // Provide a suitable constructor (depends on the kind of dataset)
        public listAdapter(Context context, JSONArray foodset) {

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

                JSONObject room = (JSONObject) foodDataset.get(position);

                holder.mTextView.setText(room.getString("service"));

                for(int j = 0; j < icons.length() ; j++) {


                    JSONObject IC = (JSONObject) icons.get(j);


                    if(IC.get("Type").toString().matches(room.getString("type"))) {


                        holder.logo.setImageResource(getResources().getIdentifier(IC.get("Image").toString(),"drawable","com.example.home.sinopiainntravelapp"));


                    }





                }


            } catch (JSONException e) {

                e.printStackTrace();

            }





            // holder.right.setImageResource(R.drawable.ic_star_border_black_24dp);

            /*holder.txtName.setText(f.getString("room"));


          */

        }

        @Override
        public int getItemCount() {

            return foodDataset.length();

        }




    }



}


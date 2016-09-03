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
public class Fragment_Places extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;


    public JSONArray rooms = null;

    ArrayList<String> Jsonroomlist;

    ArrayFilter businessFilter ;

    private JSONArray foodDataset;

    ArrayList<String> roomlist;

    private Integer[] Right = {

            R.drawable.ic_done_24dp
    };

    Bundle bundle1;
    Bundle bundle;
    public Fragment_Places() {
        // Required empty public constructor
    }


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



                if(bundle.getInt("Activity") == 0) {

                    ((Activity_Home) getActivity()).onBackPressed();

                }
                else {

                    ((Activity_CheckIn) getActivity()).onBackPressed();

                }

            }
        });


        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));



        try {






             bundle1 = this.getArguments();

            rooms = new JSONArray( bundle1.getString("Places"));

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



        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView ;

            public ImageView right,logo ;



            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);

                mTextView = (TextView) itemView.findViewById(R.id.name);

                right = (ImageView) itemView.findViewById(R.id.right);

            }


            @Override
            public void onClick(View v) {


                Fragment_Itinerary new_fragment = new Fragment_Itinerary();


                try {


                    if (bundle.getInt("Activity") == 0) {

                        Bundle bundle = new Bundle();

                        bundle.putString("Place", String.valueOf(rooms.get(getLayoutPosition())));

                        bundle.putInt("Activity", bundle1.getInt("Activity"));


                        new_fragment.setArguments(bundle);

                        ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment);


                    } else {

                        Bundle bundle = new Bundle();

                        bundle.putString("Place", String.valueOf(rooms.get(getLayoutPosition())));

                        bundle.putInt("Activity", bundle1.getInt("Activity"));


                        new_fragment.setArguments(bundle);

                        ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment);


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

                JSONObject place = (JSONObject) rooms.get(position);

                StringBuilder item = new StringBuilder();

                holder.mTextView.setText(place.getString("businessname"));


                if(bundle.getInt("Activity") == 0) {

                    if(((Activity_Home) getActivity()).checkItinerary(place.getString("businessname")))

                    {
                        holder.right.setImageResource(Right[0]);


                    }else{


                        holder.right.setImageResource(0);

                    }

                }
                else {

                    if(((Activity_CheckIn) getActivity()).checkItinerary(place.getString("businessname")))

                    {
                        holder.right.setImageResource(Right[0]);


                    }else{


                        holder.right.setImageResource(0);

                    }

                }



            } catch (JSONException e) {

                e.printStackTrace();

            }

            //holder.right.setImageResource(R.drawable.ic_star_border_black_24dp);
           // *holder.txtName.setText(f.getString("room"));




        }

        @Override
        public int getItemCount() {

            return rooms.length();

        }




    }



}

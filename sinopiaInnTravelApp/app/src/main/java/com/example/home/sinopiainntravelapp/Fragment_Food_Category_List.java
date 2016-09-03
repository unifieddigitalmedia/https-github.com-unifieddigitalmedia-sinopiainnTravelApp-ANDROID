package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
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
                right = (ImageView) itemView.findViewById(R.id.right);
                logo = (ImageView) itemView.findViewById(R.id.image);

            }


            @Override
            public void onClick(View v) {


                if(getArguments().getInt("Menu") == 4) {


                }else{



                 ImageView staticImage = (ImageView) v.findViewById(R.id.image);


                Listview_Item_Description new_fragment = new Listview_Item_Description();

                Bundle bundle1 = new Bundle();

                bundle1.putInt("Activity",bundle.getInt("Activity"));

                try {


                    //bundle1.putString("Meta", String.valueOf((JSONObject) new JSONArray(String.valueOf(foodDataset.get(getLayoutPosition()))).get(0)));


                    bundle1.putString("Meta", String.valueOf((JSONObject) foodDataset.get(getLayoutPosition())));


                } catch (JSONException e) {

                    e.printStackTrace();

                }

                bundle1.putInt("Menu", bundle.getInt("Menu"));

                new_fragment.setArguments(bundle1);

                if(bundle.getInt("Activity") == 0) {


                   ((Activity_Home)getActivity()).homePageReplaceFragment(new_fragment,staticImage);


                }else {

                   ((Activity_CheckIn)getActivity()).homePageReplaceFragment(new_fragment,staticImage);

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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemamentitylayout, parent, false);

            ViewHolder vh = new ViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            try {




              //  JSONObject f = (JSONObject) new JSONArray(String.valueOf(foodDataset.get(position))).get(0);

                JSONObject f = (JSONObject) foodDataset.get(position);

                holder.mTextView.setText(f.getString("name"));

                holder.right.setImageResource(R.drawable.ic_chevron_right_24dp);

            } catch (JSONException e) {

                e.printStackTrace();

            }





        }

        @Override
        public int getItemCount() {
            return foodDataset.length();
        }


    }

}

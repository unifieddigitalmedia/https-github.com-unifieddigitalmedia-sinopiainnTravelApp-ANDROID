package com.example.home.sinopiainntravelapp;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Location_List extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;

    Bundle bundle;
    ImageView mainImage;
    ScaleBitMaps bitmapClass ;

    private String[] foods = {

            "Mackrel Run Down","Coffee" };


    public Fragment_Location_List() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rooms_1, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

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




        return rootView;


    }

    private class listAdapterFoodCatergory extends RecyclerView.Adapter<listAdapterFoodCatergory.ViewHolder> {

        private JSONArray foodDataset;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            public TextView mTextView , descriptionTextView;

            public ImageView right ,logo;

            RelativeLayout overlay, layout;

            CircularProgressView progressView;



            public ViewHolder(View itemView) {

                super(itemView);

                itemView.setOnClickListener(this);

                progressView = (CircularProgressView) itemView.findViewById(R.id.progress_view);

                overlay = (RelativeLayout) itemView.findViewById(R.id.overlay);

                layout = (RelativeLayout) itemView.findViewById(R.id.placeholder);

                overlay.setVisibility(View.VISIBLE);

                progressView.startAnimation();

                mTextView = (TextView) itemView.findViewById(R.id.name);

                descriptionTextView = (TextView) itemView.findViewById(R.id.description);

                logo = (ImageView) itemView.findViewById(R.id.image);

            }


            @Override
            public void onClick(View v) {


                Log.i("MENU ITEM", String.valueOf(getArguments().getInt("Menu")));

                Listview_Item_Description new_fragment = new Listview_Item_Description();

                if(getArguments().getInt("Menu") == 3 && bundle.getInt("Activity") == 0 ) {

                    Bundle bundle1 = new Bundle();

                    bundle1.putInt("Activity",bundle.getInt("Activity"));

                    //bundle1.putString("Meta", String.valueOf((JSONObject) new JSONArray(String.valueOf(foodDataset.get(getLayoutPosition()))).get(0)));

                    try {

                        bundle1.putString("Meta", String.valueOf((JSONObject) foodDataset.get(getLayoutPosition())));

                        bundle1.putInt("Menu", bundle.getInt("Menu"));

                        new_fragment.setArguments(bundle1);

                        if(bundle.getInt("Activity") == 0 && getArguments().getInt("Menu") == 3 ) {

                            final JSONObject locationObject  = (JSONObject) foodDataset.get(getLayoutPosition());

                            ((Activity_Home)getActivity()).tripPlanner(locationObject.getString("location"));

                        }else if(bundle.getInt("Activity") == 1 && getArguments().getInt("Menu") == 3 ){

                            ((Activity_CheckIn)getActivity()).homePageFadeTransition(new_fragment,"");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{

                    try {

                        ImageView staticImage = (ImageView) v.findViewById(R.id.image);


                        // Fragment_Book_Description new_fragment = new Fragment_Book_Description();

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

                Log.i("image_url",f.getString("image_url").replaceAll(" ", "%20"));

                int resID = 0;

                resID = getResources().getIdentifier(f.getString("image_url") , "drawable", getActivity().getPackageName());

                BitmapDrawable ob = new BitmapDrawable(getResources(),  bitmapClass.decodeSampledBitmapFromResource(getResources(),resID, 100, 100));

                holder.layout.setBackgroundDrawable(ob);

                //holder.logo.setImageDrawable(getResources().getDrawable(resID));

                //holder.logo.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(),resID, 100, 100));

                holder.overlay.setVisibility(View.GONE);

                holder.progressView.stopAnimation();

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

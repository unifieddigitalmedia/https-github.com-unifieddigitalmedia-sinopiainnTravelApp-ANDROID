package com.example.home.sinopiainntravelapp;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class Listview_Item_Description extends Fragment {

    private BottomSheetBehavior mBottomSheetBehavior;
    ExpandableListView route1;
    ExpandableListView route2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;

    Bundle bundle;
    ImageView mainImage;
    ScaleBitMaps bitmapClass;

    JSONObject obj ;


    static ArrayList<String> listDataHeader;

    static ArrayList<JSONArray>  listDataChild;

    JSONArray childitems;

    CoordinatorLayout relativeLayout;

    private String[] foods = {

            "Mackrel Run Down", "Coffee"};


    public Listview_Item_Description() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_listview_item_description, container, false);

        relativeLayout = (CoordinatorLayout) rootView.findViewById(R.id.CoordinatorLayout);

        route1 = (ExpandableListView) rootView.findViewById(R.id.route1);

        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        // mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.chef, 100, 100));

        bundle = this.getArguments();

        try {

            obj = new JSONObject(String.valueOf(getArguments().get("Meta")));


        } catch (JSONException e) {

            e.printStackTrace();

        }

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.i("MENU ITEM food", String.valueOf(getArguments().getInt("Menu")));

                if(getArguments().getInt("Menu") == 3 || getArguments().getInt("Menu") == 2) {

                    BottomSheetDialogFragment bottomSheetDialogFragment = new TutsPlusBottomSheetDialogFragment();

                    Bundle bundle3 = new Bundle();

                    bundle3.putString("Meta",getArguments().getString("Meta"));



                    bottomSheetDialogFragment.setArguments(bundle3);

                    bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                    Log.i("MENU ITEM food","");


                }else{


                AsyncHttpClient client = new AsyncHttpClient();

                try {
                    client.get(obj.getString("pdf"), new FileAsyncHttpResponseHandler(getContext()) {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, File response) {


                            if(getArguments().getInt("Activity") == 1) {


                                Intent target = new Intent(Intent.ACTION_VIEW);

                                target.setDataAndType(Uri.fromFile(file), "application/pdf");

                                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                Intent intent = Intent.createChooser(target, "Open File");

                                try {

                                    startActivity(intent);

                                } catch (ActivityNotFoundException e) {


                                    Snackbar snackbar = Snackbar
                                            .make(relativeLayout, "A PDF reader meds to be installed to view this item", Snackbar.LENGTH_LONG);

                                    snackbar.show();

                                }


                            }else{


                                Snackbar snackbar = Snackbar
                                        .make(relativeLayout, "You need to purchase this item", Snackbar.LENGTH_LONG);

                                snackbar.show();


                            }

                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }
            }
        });


        listDataHeader = new ArrayList<String>();

        listDataChild = new ArrayList<JSONArray>();


        listDataHeader.add(0,"");


        listDataHeader.add(1,"");


        try {

            childitems = new JSONArray();

            JSONObject item = new JSONObject();

            item.put("name",obj.get("name"));

            childitems.put(item);

            listDataChild.add(0,childitems);

            childitems = new JSONArray();

            childitems.put(obj.get("description"));

            listDataChild.add(1, childitems);

        } catch (JSONException e) {


            e.printStackTrace();


        }





        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        route1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });

        route1.setAdapter(listAdapter);

        route1.expandGroup(0);
        route1.expandGroup(1);






        return rootView;


    }

    public static class ExpandableListAdapter extends BaseExpandableListAdapter {

        private LayoutInflater mInflater;

        public ExpandableListAdapter(Context contactPage, ArrayList<String> listDataHeader, ArrayList<JSONArray> listDataChild) {

            mInflater = LayoutInflater.from(contactPage);

        }


        @Override
        public int getGroupCount() {


            return listDataHeader.size();

        }

        @Override
        public int getChildrenCount(int groupPosition) {


            return listDataChild.get(groupPosition).length();

        }

        @Override
        public Object getGroup(int groupPosition) {

            if(groupPosition == 0){

                return null;

            }

            return listDataHeader.get(groupPosition);

        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {

            try {

                return listDataChild.get(groupPosition).get(childPosition);

            } catch (JSONException e) {

                e.printStackTrace();

            }

            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }



        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            String headerTitle = (String) getGroup(groupPosition);

            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.listitem, null);


            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.name);

            lblListHeader.setTextSize(24);

            lblListHeader.setTypeface(null, Typeface.BOLD);

            lblListHeader.setText(headerTitle);

            return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


            ViewHolder holder = null;


            if (convertView == null) {


                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.contactchilditem, null);

                holder.name = (TextView) convertView.findViewById(R.id.name);

                convertView.setTag(holder);

            } else {


                holder = (ViewHolder) convertView.getTag();

            }


            try {

                if(groupPosition == 0){

                    JSONObject f = (JSONObject) listDataChild.get(groupPosition).get(childPosition);

                    holder.name.setText(f.getString("name"));

                }else{


                    holder.name.setText((CharSequence) listDataChild.get(groupPosition).get(childPosition));
                }






            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }


    static class ViewHolder {


        TextView name ;



    }


    static public class TutsPlusBottomSheetDialogFragment extends BottomSheetDialogFragment {


        ExpandableListView route2;

        ArrayList<String> listDataHeader;

        ArrayList<JSONArray>  listDataChild;

        JSONArray childitems;

        JSONObject obj ;

        private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_HIDDEN) {

                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }

        };

        @Override
        public void setupDialog(Dialog dialog, int style) {

            super.setupDialog(dialog, style);

            View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);

            route2 = (ExpandableListView) contentView.findViewById(R.id.route2);

            listDataHeader = new ArrayList<String>();

            listDataChild = new ArrayList<JSONArray>();


            listDataHeader.add(0,"");


            listDataHeader.add(1,"Ingredients");


            listDataHeader.add(2,"Method");

            try {

                obj = new JSONObject(String.valueOf(getArguments().get("Meta")));

                Log.i("obj:-", String.valueOf(obj));

            } catch (JSONException e) {

                e.printStackTrace();

            }

            try {

                childitems = new JSONArray();

                JSONObject item = new JSONObject();

                item.put("name",obj.get("description"));

                childitems.put(item);

                listDataChild.add(0,childitems);

                childitems = new JSONArray();

                childitems.put(item);

                listDataChild.add(1, obj.getJSONArray("ingridients"));

                listDataChild.add(2, obj.getJSONArray("method"));

            } catch (JSONException e) {


                e.printStackTrace();


            }


            ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

            route2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    return true;
                }
            });

            route2.setAdapter(listAdapter);

            route2.expandGroup(0);
            route2.expandGroup(1);


            dialog.setContentView(contentView);

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();

            CoordinatorLayout.Behavior behavior = params.getBehavior();

            if( behavior != null && behavior instanceof BottomSheetBehavior ) {

                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);

            }

        }

    }

}
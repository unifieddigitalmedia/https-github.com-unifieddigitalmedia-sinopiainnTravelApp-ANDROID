package com.example.home.sinopiainntravelapp;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Contact_Page extends Fragment implements OnMapReadyCallback {

    ExpandableListView route1;

    private static GoogleMap mMap;

    private static Double latitude, longitude;

    ArrayList<String> listDataHeader;

    ArrayList<JSONArray>  listDataChild;

    public Fragment_Contact_Page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_page, container, false);

        route1 = (ExpandableListView) rootView.findViewById(R.id.route1);

        listDataHeader = new ArrayList<String>();
        listDataChild = new ArrayList<JSONArray>();


        listDataHeader.add(0,"WHERE WE ARE");
        listDataHeader.add(1,"CONTACTS");



        String where = "[{ \"name\":\"Zion Hill\"},{ \"name\":\"Fairy Hill P.O Portland\"},{ \"name\":\"Jamaica\"}]";

        String contact = "[{ \"name\":\"Call : +1 212 888 7000\"},{ \"name\":\"Visit : www.sinopiainn.com\"},{ \"name\":\"Email : info@sinopiainn.com\"}]";



        try {

            listDataChild.add(0, new JSONArray(where));

            listDataChild.add(1, new JSONArray(contact));




        } catch (JSONException e) {


            e.printStackTrace();


        }





        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);


        route1.setAdapter(listAdapter);

        route1.expandGroup(0);
        route1.expandGroup(1);

       // SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();

        mMapFragment.getMapAsync(this);

        getChildFragmentManager().beginTransaction().add(R.id.container, mMapFragment).addToBackStack(null).commit();


        return rootView;




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        LatLng sydney = new LatLng( 18.166657, -76.380665);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Sinopia Inn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.animateCamera(zoom);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {


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


                convertView = mInflater.inflate(R.layout.billheaderitem, null);


            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.name);

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

                JSONObject f = (JSONObject) listDataChild.get(groupPosition).get(childPosition);


                holder.name.setText(f.getString("name"));



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

}

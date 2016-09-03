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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Contact_Page extends Fragment {

    ExpandableListView route1;
    ArrayList<String> listDataHeader;

    ArrayList<ArrayList<String>>  listDataChild;

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

        listDataHeader.add(0,"Route 1");
        listDataHeader.add(1,"Route 2");
        listDataHeader.add(2,"Route 3");


        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);


        route1.setAdapter(listAdapter);


        return rootView;




    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {


        private LayoutInflater mInflater;

        public ExpandableListAdapter(Context contactPage, ArrayList<String> listDataHeader, ArrayList<ArrayList<String>> listDataChild) {

            mInflater = LayoutInflater.from(contactPage);



        }

        @Override
        public int getGroupCount() {
            return listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return listDataChild.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return listDataHeader.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return listDataChild.get(groupPosition).get(childPosition);
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

            lblListHeader.setTypeface(null, Typeface.BOLD);

            lblListHeader.setText(headerTitle);

            return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

}

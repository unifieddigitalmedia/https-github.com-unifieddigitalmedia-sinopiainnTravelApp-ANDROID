package com.example.home.sinopiainntravelapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Bill extends Fragment {

    JSONArray booking;

    JSONArray total;

    int num_of_days;
    int num_of_people;
    int num_of_rooms;

    Button goToDetails;

    ArrayList<String> listDataHeader;

    ArrayList<JSONArray>  listDataChild;

    ExpandableListView bill;
    ImageView mainImage;
    ScaleBitMaps bitmapClass ;


    public Fragment_Bill() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bill, container, false);






        booking = new JSONArray();

        Bundle bundle = this.getArguments();

        listDataChild = new ArrayList<JSONArray>();

        num_of_people = ((Activity_Home) getActivity()).numf_of_guest;
        num_of_rooms = ((Activity_Home) getActivity()).Jsonroomlist.length();
        num_of_days =  ((Activity_Home) getActivity()).num_of_days;;

        try {


            String group4 = "[{ \"name\":\"Tax\"},{ \"name\":\"Total\"}]";

            listDataChild.add(0, new JSONArray(bundle.getString("RoomList")));

            listDataChild.add(1, new JSONArray(bundle.getString("OfferList")));

            listDataChild.add(2, new JSONArray(bundle.getString("AmenityList")));

            listDataChild.add(3,new JSONArray(group4));



        } catch (JSONException e) {

            e.printStackTrace();

        }


        bill = (ExpandableListView) rootView.findViewById(R.id.totals);

        listDataHeader = new ArrayList<String>();

        listDataHeader.add(0,"Rooms");

        listDataHeader.add(1, "Offers");

        listDataHeader.add(2,"Amentiites");

        listDataHeader.add(3,"");

        listDataHeader.add(4,"CONTINUE");

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);


        bill.setAdapter(listAdapter);


        bill.expandGroup(0);
        bill.expandGroup(1);
        bill.expandGroup(2);
        bill.expandGroup(3);


        bill.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {


                if(groupPosition != 4 ){

                    return false;

                }else{


                    Fragment_PersonalDetails new_fragment = new Fragment_PersonalDetails();

                    ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment,"personal");

                    return true;

                }



            }
        });



        Thread doCalculation = new Thread(new Runnable() {

            public void run() {

                for(int groupPosition = 0; groupPosition < 3; groupPosition++) {

                    for(int childPosition = 0; childPosition < listDataChild.size(); childPosition++) {

                        try {

                            JSONObject f = (JSONObject) listDataChild.get(groupPosition).get(childPosition);


                            switch (groupPosition) {

                                case 0:


                                    ((Activity_Home) getActivity()).setreservationTotal(num_of_days * Double.parseDouble(f.getString("price")));

                                    break;

                                case 1:





                                    break;

                                case 2:

                                    if(f.getString("frequency").equals("person") ){



                                        ((Activity_Home) getActivity()).setreservationTotal( ((Activity_Home) getActivity()).numf_of_guest * Double.parseDouble(f.getString("price")));

                                    }
                                    else if(f.getString("frequency").equals("room")){




                                        ((Activity_Home) getActivity()).setreservationTotal(num_of_rooms * Double.parseDouble(f.getString("price")));


                                    }
                                    else if(f.getString("frequency").equals("night")){




                                        ((Activity_Home) getActivity()).setreservationTotal(num_of_days * Double.parseDouble(f.getString("price")));



                                    }  else if(f.getString("frequency").equals("distance")){



                                        ((Activity_Home) getActivity()).setreservationTotal( Double.parseDouble(f.getString("price")));


                                    }


                                    ((Activity_Home) getActivity()).reservation_sub_total =  ((Activity_Home) getActivity()).reservation_total ;

                                    break;







                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }

                ((Activity_Home) getActivity()).calTaxAmount();

            }});

        doCalculation.start();


        return rootView;



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

            return groupPosition*100 + childPosition;

        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {




            String headerTitle = (String) listDataHeader.get(groupPosition);

            ViewHolder holder;

            if (convertView == null) {


                 convertView = mInflater.inflate(R.layout.billheaderitem, null);

                 holder = new ViewHolder();




            } else {


                holder = (ViewHolder) convertView.getTag();

            }


            holder.lblListHeader = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);

            holder.lblListHeader.setText(headerTitle);


            if(groupPosition == 4) {

                holder.lblListHeader.setGravity(Gravity.CENTER);
            }
                return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {




            ViewHolder holder = null;


            if (convertView == null) {

                if(groupPosition != 4) {


                    holder = new ViewHolder();

                    convertView = mInflater.inflate(R.layout.billchilditem, null);

                    holder.name = (TextView) convertView.findViewById(R.id.name);

                    holder.currency = (TextView) convertView.findViewById(R.id.currency);

                    holder.amount = (TextView) convertView.findViewById(R.id.amount);

                    convertView.setTag(holder);

                }

            }
            else {


                holder = (ViewHolder) convertView.getTag();

            }



            try {

                JSONObject f = (JSONObject) listDataChild.get(groupPosition).get(childPosition);


                holder.name.setText(f.getString("name"));

                switch (groupPosition) {

                    case 0:


                        holder.amount.setText("USD " + String.valueOf((int) (((Activity_Home) getActivity()).num_of_days * Double.parseDouble(f.getString("price")))));


                         break;

                    case 1:



                        holder.amount.setText("(" + ((Activity_Home) getActivity()).calDiscountAmount(Double.parseDouble(f.getString("amount"))) + ")");


                        break;

                    case 2:

                        if(f.getString("frequency").equals("person") ){


                            holder.amount.setText("USD " +String.valueOf((int) ( ((Activity_Home) getActivity()).numf_of_guest * Double.parseDouble(f.getString("price")))));


                        }
                        else if(f.getString("frequency").equals("room")){



                            holder.amount.setText("USD " +String.valueOf((int) (num_of_rooms * Double.parseDouble(f.getString("price")))));


                        }
                        else if(f.getString("frequency").equals("night")){



                            holder.amount.setText("USD " +String.valueOf((int) (num_of_days * Double.parseDouble(f.getString("price")))));




                        }  else if(f.getString("frequency").equals("distance")){

                            holder.amount.setText("USD " +String.valueOf(Double.parseDouble(f.getString("price"))));



                        }



                        break;


                    case 3:

if(childPosition == 0 ){


    holder.amount.setText("USD " +   String.format("%.2f",((Activity_Home) getActivity()).taxAmount) );


}else{


    holder.amount.setText("USD " +   String.format("%.2f",((Activity_Home) getActivity()).reservation_total) );


}

                        break;




                }




            } catch (JSONException e) {
                e.printStackTrace();
            }



            return  convertView;

        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {

            return false;

        }

    }

    static class ViewHolder {

        TextView lblListHeader;
        TextView name ;
        TextView currency;
        TextView amount;
        Button goTopersonalDetails;

    }





}

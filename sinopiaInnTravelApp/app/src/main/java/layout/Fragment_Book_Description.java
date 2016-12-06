package layout;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home.sinopiainntravelapp.R;
import com.example.home.sinopiainntravelapp.ScaleBitMaps;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Book_Description extends Fragment {

    ExpandableListView route1;

    Bundle bundle;
    ImageView mainImage;
    ScaleBitMaps bitmapClass;

    JSONObject obj ;


    ArrayList<String> listDataHeader;

    ArrayList<JSONArray>  listDataChild;

    JSONArray childitems;



    private String[] foods = {

            "Mackrel Run Down", "Coffee"};


    public Fragment_Book_Description() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_listview_item_description, container, false);

        route1 = (ExpandableListView) rootView.findViewById(R.id.route1);

        bitmapClass = new ScaleBitMaps(getActivity());

        mainImage = (ImageView) rootView.findViewById(R.id.image);

        // mainImage.setImageBitmap(bitmapClass.decodeSampledBitmapFromResource(getResources(), R.drawable.chef, 100, 100));

        bundle = this.getArguments();

        try {

            obj = new JSONObject(String.valueOf(getArguments().get("Meta")));


            Log.i("ITEM", String.valueOf(obj));

        } catch (JSONException e) {

            e.printStackTrace();

        }



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncHttpClient client = new AsyncHttpClient();

                try {
                    client.get(obj.getString("pdf"), new FileAsyncHttpResponseHandler(getContext()) {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                        }


                        @Override
                        public void onSuccess(int statusCode, Header[] headers, File response) {


                            if(getArguments().getInt("Activity") == 0) {


                                Intent target = new Intent(Intent.ACTION_VIEW);

                                target.setDataAndType(Uri.fromFile(file), "application/pdf");

                                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                Intent intent = Intent.createChooser(target, "Open File");

                                try {

                                    startActivity(intent);

                                } catch (ActivityNotFoundException e) {
                                    // Instruct the user to install a PDF reader here, or something

                                    Log.i("LOGCAT","Instruct the user to install a PDF reader here, or something");
                                }


                            }else{


                                Log.i("LOGCAT","Need to purchase");

                            }

                        }

                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        listDataHeader = new ArrayList<String>();
        listDataChild = new ArrayList<JSONArray>();


        listDataHeader.add(0,"");




        String where = "[{ \"name\":\"Zion Hill\"},{ \"name\":\"Fairy Hill P.O Portland\"},{ \"name\":\"Jamaica\"}]";

        String contact = "[{ \"name\":\"Call : +1 212 888 7000\"},{ \"name\":\"Visit : www.sinopiainn.com\"},{ \"name\":\"Email : info@sinopiainn.com\"}]";



        try {

            childitems = new JSONArray();

            JSONObject item = new JSONObject();

            item.put("name",obj.get("description"));

            childitems.put(item);

            listDataChild.add(0,childitems);

            childitems = new JSONArray();

            childitems.put(item);

            listDataChild.add(0, childitems);

            listDataChild.add(0, childitems);




        } catch (JSONException e) {


            e.printStackTrace();


        }





       ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);


        route1.setAdapter(listAdapter);

        route1.expandGroup(0);
        route1.expandGroup(1);


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
package com.example.home.sinopiainntravelapp;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Fragment_Photos extends Fragment {

    GridView gridview;

    JSONArray imgUrls ;

    ArrayList<String> bitmapArray ;

    static ScaleBitMaps bitmapClass;

    ArrayList<ImageItem> imageItems = null;

    public Fragment_Photos() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment__grid__view, container, false);

        ((Activity_CheckIn) getActivity()).progressView.stopAnimation();

        ((Activity_CheckIn) getActivity()).overlay.setVisibility(View.GONE);

        gridview = (GridView) rootView.findViewById(R.id.gridview);

        imgUrls = new JSONArray();

        bitmapClass = new ScaleBitMaps(getActivity());


        try {

            gridview.setAdapter(new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData()));

        } catch (JSONException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {



                if(getArguments().getInt("Menu") == 3 || getArguments().getInt("Menu") == 5){

                    Fragment_Food_Category_List new_fragment = new Fragment_Food_Category_List();

                    Bundle bundle1 = new Bundle();

                    //foodDataset.get(getLayoutPosition())

                    try {


                        JSONObject f = (JSONObject) new JSONArray(getArguments().getString("Json")).get(position);

                        JSONArray list = new JSONArray(f.getString("items"));

                        bundle1.putString("List", f.getString("items"));


                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                    bundle1.putInt("Activity", getArguments().getInt("Activity"));

                    bundle1.putInt("Menu", getArguments().getInt("Menu"));

                    new_fragment.setArguments(bundle1);


                    if(getArguments().getInt("Activity") == 0){


                        ((Activity_Home)getActivity()).homePageFadeTransition(new_fragment, "");


                    }else {

                        ((Activity_CheckIn)getActivity()).homePageFadeTransition(new_fragment,"");

                    }




                }else {

                    final ImageItem item = (ImageItem) parent.getItemAtPosition(position);


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    final AlertDialog dialog = builder.create();

                    //LayoutInflater inflater = getLayoutInflater();

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                    View dialogLayout = inflater.inflate(R.layout.go_pro_dialog_layout, null);

                    dialog.setView(dialogLayout);

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.show();

                    Log.i("item.getImage()", String.valueOf(item.getImage()));

                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface d) {

                            ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);

                            Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                                    R.drawable.logo);

                            image.setImageBitmap(item.getImage());


                            float imageWidthInPX = (float)image.getWidth();

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                    Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                            image.setLayoutParams(layoutParams);


                        }
                    });

                  /*  Fragment_Photos_Pager new_fragment = new Fragment_Photos_Pager();

                    Bundle bundle1 = new Bundle();

                    bundle1.putParcelable("Image Item", (Parcelable) imageItems.get(position));

                    bundle1.putBoolean("fromGridview", true);

                    new_fragment.setArguments(bundle1);

                    ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment, "");*/

                }

            }});



        return rootView;


    }

    private ArrayList<ImageItem> getData() throws JSONException, IOException {


       imageItems = new ArrayList<>();

        if(getArguments().getInt("Menu") == 3 || getArguments().getInt("Menu") == 5){




            for (int i = 0; i <  new JSONArray(this.getArguments().getString("Json")).length(); i++) {


                JSONObject item = (JSONObject)  new JSONArray(this.getArguments().getString("Json")).get(i);

                final JSONObject firstItem  =  (JSONObject) item.getJSONArray("items").get(0);

                imageItems.add(new ImageItem(((Activity_CheckIn) getActivity()).menubitmapArray.get(i), firstItem.getString("type")));


                    }

        }else{


            if(((Activity_CheckIn) getActivity()).executor.isTerminated()){


                Log.i("terminated", String.valueOf(((Activity_CheckIn) getActivity()).bitmapArray));


                for (int i = 0; i <  ((Activity_CheckIn) getActivity()).bitmapArray.size(); i++) {


                    //Bitmap  bitmap = BitmapFactory.decodeFile(bitmapArray.get(i));
                    imageItems.add(new ImageItem(((Activity_CheckIn) getActivity()).bitmapArray.get(i), "Image#" + i));


                }


            }else {

                ((Activity_CheckIn) getActivity()).progressView.startAnimation();

                ((Activity_CheckIn) getActivity()).overlay.setVisibility(View.VISIBLE);

                try {

                    ((Activity_CheckIn) getActivity()).executor.awaitTermination(5, TimeUnit.SECONDS);

                    ((Activity_CheckIn) getActivity()).executor.shutdownNow();

                    for (int i = 0; i <  ((Activity_CheckIn) getActivity()).bitmapArray.size(); i++) {


                        //Bitmap  bitmap = BitmapFactory.decodeFile(bitmapArray.get(i));
                        imageItems.add(new ImageItem(((Activity_CheckIn) getActivity()).bitmapArray.get(i), "Image#" + i));


                    }


                    ((Activity_CheckIn) getActivity()).progressView.stopAnimation();

                    ((Activity_CheckIn) getActivity()).overlay.setVisibility(View.GONE);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }


        }


        return imageItems;
    }






    public class ImageItem implements Parcelable{

        private Bitmap image;
        private String title;

        public ImageItem(Bitmap image, String title) {
            super();
            this.image = image;
            this.title = title;
        }

        protected ImageItem(Parcel in) {
            image = in.readParcelable(Bitmap.class.getClassLoader());
            title = in.readString();
        }

        public final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
            @Override
            public ImageItem createFromParcel(Parcel in) {
                return new ImageItem(in);
            }

            @Override
            public ImageItem[] newArray(int size) {
                return new ImageItem[size];
            }
        };

        public Bitmap getImage() {
            return image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(image, i);
            parcel.writeString(title);
        }
    }


    public class GridViewAdapter extends ArrayAdapter {
        private Context context;
        private int layoutResourceId;
        private ArrayList data = new ArrayList();

        public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null) {

                LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ViewHolder();

                holder.image = (ImageView) row.findViewById(R.id.image);

                row.setTag(holder);

            } else {
                holder = (ViewHolder) row.getTag();
            }

            ImageItem item = (ImageItem) data.get(position);

            holder.image.setImageBitmap(item.getImage());


            return row;
        }

        class ViewHolder {

            ImageView image;
        }
    }

}

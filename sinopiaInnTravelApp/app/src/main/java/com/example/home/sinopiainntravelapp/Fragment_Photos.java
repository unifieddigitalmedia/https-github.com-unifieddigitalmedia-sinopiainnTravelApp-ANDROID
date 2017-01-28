package com.example.home.sinopiainntravelapp;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.extras.Base64;


public class Fragment_Photos extends Fragment {

    GridView gridview;

    JSONArray imgUrls ;

    ArrayList<String> bitmapArray ;

    static ScaleBitMaps bitmapClass;

    ArrayList<ImageItem> imageItems = null;

    ArrayList<Integer> imageItemsPos = null;

    ArrayList <String> imageData = null;

    ArrayList <String> fileNames = null;

    static ArrayList<String> bitmaps = null;

    static ArrayList<String> descriptions = null;

    GridViewAdapter adapter =  null;

    private static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";

    public static String CALLBACKURL = "http://www.sinopiainn.com";

    public static String authURLString = null;


    String request_token;

    public Fragment_Photos() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment__grid__view, container, false);

        ((Activity_CheckIn) getActivity()).progressView.stopAnimation();

        ((Activity_CheckIn) getActivity()).overlay.setVisibility(View.GONE);

        gridview = (GridView) rootView.findViewById(R.id.gridview);

        Toolbar myToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle();

        setHasOptionsMenu(true);

        imgUrls = new JSONArray();

        bitmapClass = new ScaleBitMaps(getActivity());

        imageItems = new ArrayList<>();

        imageItemsPos = new ArrayList<>();

        imageData = new ArrayList<>();

        fileNames = new ArrayList<>();
        //ArrayList<ImageItem> data = getData();



        new DownloadFilesTask().execute();


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                if(getArguments().getInt("Menu") == 24 ) {


                    if (imageItemsPos.contains(position)) {


                        imageItemsPos.remove(imageItemsPos.indexOf(position));

                        //imageData.remove(imageItemsPos.indexOf(position));


                    } else {

                        imageItemsPos.add(position);



                        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        ImageItem item = (ImageItem) imageItems.get(position);

                        item.getImage().compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        byte[] imageBytes = baos.toByteArray();

                        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        imageData.add(encodedImage);*/


                    }



                } else {


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


                }




            }});



        return rootView;


    }

    private ArrayList<ImageItem> getData() throws JSONException, IOException {





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

        public void updateItemList(ArrayList newdata){

            this.data = newdata;
            notifyDataSetChanged();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sharemenu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
             case R.id.action_favorite:


                 authURLString = AUTHURL + "?client_id=" +getString(R.string.Client_ID)+ "&redirect_uri=" + CALLBACKURL + "&response_type=code&display=touch&scope=basic";


                 //String authURLString =  "https://api.instagram.com/oauth/authorize/?client_id="+getString(R.string.Client_ID)+"&redirect_uri="+CALLBACKURL+"&response_type=token";

                 Log.i("authURLString",authURLString);

                 ((Activity_CheckIn) getActivity()).goToWeb(authURLString);


                 return true;

            case R.id.action_done:


                new Thread() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    public void run() {


                        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                                getActivity().getApplicationContext(),
                                "us-west-2:0f15b5d4-65b4-402f-a20f-88343dcd8c5d", // Identity Pool ID
                                Regions.US_WEST_2 // Region
                        );

                        AmazonS3 s3client = new AmazonS3Client(credentialsProvider);

                        s3client.setEndpoint("s3-us-west-2.amazonaws.com");

                        AsyncHttpClient client = new SyncHttpClient();

                        client.setConnectTimeout(10000000);


                        RequestParams params = new RequestParams();



                        //https://s3-us-west-2.amazonaws.com/sinopiainn.reservations/Boe+Doe/588b603e83846d5846d6cc2b.jpg

                        TransferUtility transferUtility = new TransferUtility(s3client, getActivity().getApplicationContext());

               /* TransferObserver observer = transferUtility.upload(
                        MY_BUCKET,
                        OBJECT_KEY,
                        MY_FILE
                );*/


                        try {

                            System.out.println("Uploading a new object to S3 from a file\n");

                            for(int g = 0 ; g < imageItemsPos.size(); g++) {


                                params.put("fileNames[]", fileNames.get(g));

                                File filesDir = getActivity().getFilesDir();

                                File imageFile = new File(filesDir, fileNames.get(g));

                                OutputStream os;

                                try {

                                    os = new FileOutputStream(imageFile);
                                    imageItems.get(g).getImage().compress(Bitmap.CompressFormat.JPEG, 100, os);
                                    os.flush();
                                    os.close();

                                } catch (Exception e) {

                                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);

                                }


                                String name = ((Activity_CheckIn) getActivity()).name.replaceFirst("\\s++$", "");

                                String keyName =   name+"/"+fileNames.get(g);


                                System.out.println(keyName);


//apigateway.us-west-2.amazonaws.com  "http://sinopiainn.reservations.s3-website-us-west-2.amazonaws.com/"




                                TransferObserver observer = transferUtility.upload(
                                       "sinopiainn.reservations",
                                        keyName,
                                        imageFile
                                );
                                //s3client.putObject(new PutObjectRequest("sinopiainn.reservations", keyName, imageFile));

                                StringBuilder builder = new StringBuilder();

                                String WEB_SERVICE_URL = null;


                                try {
                                    WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/api/upload-bulk-reservation-photo?resID=").append(((Activity_CheckIn) getActivity()).settings.getString("reservationID", "")).append("&name=").append(URLEncoder.encode(name, "utf-8")).append("&message=").toString();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                Log.i("WEB_SERVICE_URL",WEB_SERVICE_URL);

                                    client.post(WEB_SERVICE_URL, params, new JsonHttpResponseHandler() {

                                        @Override
                                        public void onStart() {


                                        }

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                                            new refreshGridViewTask().execute();

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {




                                        }

                                        @Override
                                        public void onRetry(int retryNo) {


                                        }

                                    });




                            }


                        } catch (AmazonServiceException ase) {

                            System.out.println("Caught an AmazonServiceException, which " +
                                    "means your request made it " +
                                    "to Amazon S3, but was rejected with an error response" +
                                    " for some reason.");
                            System.out.println("Error Message:    " + ase.getMessage());
                            System.out.println("HTTP Status Code: " + ase.getStatusCode());
                            System.out.println("AWS Error Code:   " + ase.getErrorCode());
                            System.out.println("Error Type:       " + ase.getErrorType());
                            System.out.println("Request ID:       " + ase.getRequestId());
                        } catch (AmazonClientException ace) {
                            System.out.println("Caught an AmazonClientException, which " +
                                    "means the client encountered " +
                                    "an internal error while trying to " +
                                    "communicate with S3, " +
                                    "such as not being able to access the network.");
                            System.out.println("Error Message: " + ace.getMessage());
                        }




                    }

                }.start();










                return true;



            default:
                return super.onOptionsItemSelected(item);

        }
    }



    private class DownloadFilesTask extends AsyncTask<String, Void,  ArrayList<ImageItem>> {




        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected  ArrayList<ImageItem> doInBackground(String... strings) {


            imageItems = new ArrayList<>();
            bitmaps = getArguments().getStringArrayList("Image Item");


            if(getArguments().getInt("Menu") != 10 ) {



                for (int i = 0; i < bitmaps.size(); i++) {

                    String str = bitmaps.get(i);

                    Pattern pattern = Pattern.compile("\\w+\\.(jpg)");
                    Matcher matcher = pattern.matcher(str);
                    while (matcher.find()) {

                        System.out.println(matcher.group(0));



                        fileNames.add(matcher.group(0));
                    }


                    try (InputStream is = new URL(bitmaps.get(i)).openStream()) {

                        final Bitmap bitmap = BitmapFactory.decodeStream(is);

                        imageItems.add(new ImageItem(bitmap, ""));


                    } catch (IOException e) {
                        e.printStackTrace();

                    }



                }


            } else  if(getArguments().getInt("Menu") == 10 ) {


                for (int i = 0; i <  ((Activity_CheckIn) getActivity()).bitmapArray.size(); i++) {


                    //Bitmap  bitmap = BitmapFactory.decodeFile(bitmapArray.get(i));
                    imageItems.add(new ImageItem(((Activity_CheckIn) getActivity()).bitmapArray.get(i), "Image#" + i));


                }



            }

            return imageItems;

        }

        protected void onPostExecute(ArrayList<ImageItem> imageItems) {

            Log.i("imageItems", String.valueOf(imageItems.size()));

            //adapter.updateItemList(imageItems);


            adapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout,imageItems );

            gridview.setAdapter(adapter);



        }

    }




    private class refreshGridViewTask extends AsyncTask<String, Void,  ArrayList<ImageItem>> {




        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected  ArrayList<ImageItem> doInBackground(String... strings) {

            String response = null;
            imageItems = new ArrayList<>();

            try {

                StringBuilder builder = new StringBuilder();

                String WEB_SERVICE_URL = null;

                WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/api/timeline?email=").append(URLEncoder.encode(((Activity_CheckIn) getActivity()).email, "utf-8")).toString();

                Log.i("WEB_SERVICE_URL",WEB_SERVICE_URL);

                URL url = new URL(WEB_SERVICE_URL);

                InputStream inputStream = url.openConnection().getInputStream();

                response = streamToString(inputStream);

                //JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();

                JSONArray jsonArray = (JSONArray) new JSONTokener(response).nextValue();

                Log.i("jsonArray", String.valueOf(jsonArray));


            } catch (Exception e) {
                e.printStackTrace();
            }



            return imageItems;

        }

        protected void onPostExecute(ArrayList<ImageItem> imageItems) {




        }




    }


    public static String streamToString(InputStream p_is)
    {
        try
        {
            BufferedReader m_br;
            StringBuffer m_outString = new StringBuffer();
            m_br = new BufferedReader(new InputStreamReader(p_is));
            String m_read = m_br.readLine();
            while(m_read != null)
            {
                m_outString.append(m_read);
                m_read =m_br.readLine();
            }
            return m_outString.toString();
        }
        catch (Exception p_ex)
        {
            p_ex.printStackTrace();
            return "";
        }
    }


}

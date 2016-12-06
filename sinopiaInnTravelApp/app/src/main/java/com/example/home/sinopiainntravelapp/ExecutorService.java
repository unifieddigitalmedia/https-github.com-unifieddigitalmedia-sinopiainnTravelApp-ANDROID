package com.example.home.sinopiainntravelapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class ExecutorService {


    public void execute(Runnable r) {
        new Thread(r).start();
    }

    private static Future taskTwo = null;
    private static Future taskThree = null;
    private static JSONArray files ;
    private static ArrayList<Bitmap> bitmapArray ;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        java.util.concurrent.ExecutorService executor = Executors.newFixedThreadPool(2);

        System.err.println("start");

        executor.execute(new Runnable() {

            @Override
            public void run() {


                SyncHttpClient client = new SyncHttpClient();

                client.setConnectTimeout(20000);

                client.get("http://www.sinopiainn.com/api/images/", new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {


                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                        System.err.println("files got");


                        files = json;


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {


                        System.err.println("files terminated");

                    }


                });

            }

        });


        for(int i = 0; i < 2; i++) {
            // if this task is not created or is canceled or is completed
            if ((taskTwo == null) || (taskTwo.isDone()) || (taskTwo.isCancelled())) {
                // submit a task and return a Future
                taskTwo = executor.submit(new Runnable() {


                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void run() {


                        try {


                            SyncHttpClient client = new SyncHttpClient();

                            for (int l = 0; l < files.length(); l++) {

                                JSONObject item = (JSONObject) files.get(l);


                                StringBuilder builder = new StringBuilder();

                                String WEB_SERVICE_URL = null;

                                try {

                                    WEB_SERVICE_URL = builder.append("http://www.sinopiainn.com/").append(URLEncoder.encode(String.valueOf(item.get("image_url")), "utf-8")).toString();

                                } catch (UnsupportedEncodingException e) {

                                    e.printStackTrace();
                                }

                                try {

                                    try (InputStream is = new URL(WEB_SERVICE_URL).openStream()) {

                                        final Bitmap bitmap = BitmapFactory.decodeStream(is);


                                        bitmapArray.add(bitmap);

                                        //overlay.setVisibility(View.GONE);

                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                });
            }

            if(taskTwo.get() == null) {
                System.out.println(i+1 + ") TaskTwo terminated successfully");
            } else {
                // if it doesn't finished, cancel it
                taskTwo.cancel(true);
            }



        }
        executor.shutdown();
        System.out.println("-----------------------");
        // wait until all tasks are finished
        executor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("All tasks are finished!");

    }
}



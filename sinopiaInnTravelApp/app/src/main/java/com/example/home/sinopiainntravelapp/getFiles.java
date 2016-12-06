package com.example.home.sinopiainntravelapp;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Home on 02/12/2016.
 */

public class getFiles implements Runnable {




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






            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {



                System.err.println("files terminated");

            }


        });


    }

}

package com.example.home.sinopiainntravelapp;

import android.support.v4.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Home on 04/07/16.
 */
public class BuildString {
    public BuildString(FragmentActivity activity) {



    }


    public StringBuilder buildString (JSONArray newArray) {


        StringBuilder roomList = new StringBuilder();

        if(newArray != null)
        {


            for(int l=0; l < newArray.length(); l++) {


                JSONObject f = null;

                try {

                    f = (JSONObject) newArray.get(l);

                    roomList.append(f.getString("name"));

                    roomList.append(", ");





                } catch (JSONException e) {

                    e.printStackTrace();

                }




            }

        }

        return roomList;


    }



}

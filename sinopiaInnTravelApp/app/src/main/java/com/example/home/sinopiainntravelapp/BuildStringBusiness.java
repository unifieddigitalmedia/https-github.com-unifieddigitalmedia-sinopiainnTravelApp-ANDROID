package com.example.home.sinopiainntravelapp;

import android.support.v4.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Home on 07/07/16.
 */
public class BuildStringBusiness {

    public BuildStringBusiness(FragmentActivity activity) {



    }

    public StringBuilder buildStringJson (JSONArray newArray) {


        StringBuilder roomList = new StringBuilder();

        if(newArray != null)
        {


            for(int l=0; l < newArray.length(); l++) {


                JSONObject f = null;

                try {

                    f = (JSONObject) newArray.get(l);

                    roomList.append(f.getString("businessname"));

                    roomList.append(", ");





                } catch (JSONException e) {

                    e.printStackTrace();

                }




            }

        }

        return roomList;


    }


    public StringBuilder buildString (ArrayList<String> newArray) throws JSONException {


        StringBuilder roomList = new StringBuilder();

        if(newArray != null)
        {



            for(int l=0; l < newArray.size(); l++) {



                roomList.append(newArray.get(l));

                roomList.append(", ");







            }

        }

        return roomList;


    }



}

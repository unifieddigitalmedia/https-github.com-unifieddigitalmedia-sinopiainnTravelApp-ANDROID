package com.example.home.sinopiainntravelapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Home on 04/07/16.
 */
public class ArrayFilter {



    public JSONArray filterList(JSONArray business, ArrayList<String> types, int list) throws JSONException {

        ArrayList<String> newlist = null;


        JSONArray servicelist = null;


        newlist = new ArrayList<String>();

        servicelist = new JSONArray();


        if(list == 1)
        {



            for(int i = 0; i < business.length(); i++) {



                JSONObject f = (JSONObject) business.get(i);

                JSONArray activites = f.getJSONArray("activity");

                JSONObject businessType = (JSONObject) activites.get(0);




                for(int j = 0; j < types.size(); j++) {



                    if(businessType.getString("typeofbusiness").equals(types.get(j))) {




                        if (!newlist.contains(businessType.getString("typeofservice"))) {


                            newlist.add(businessType.getString("typeofservice"));
                            JSONObject newObj = new JSONObject();
                            newObj.put("service",businessType.getString("typeofservice"));
                            newObj.put("type",types.get(j));
                            servicelist.put(newObj);

                        }


                    }


                }



            }

Log.i("servicelist",servicelist.toString());
            return servicelist;

        }
        else
        {






            for(int i = 0; i < business.length(); i++) {



                JSONObject f = (JSONObject) business.get(i);

                JSONArray activites = f.getJSONArray("activity");

                JSONObject businessType = (JSONObject) activites.get(0);


                if (!newlist.contains(businessType.getString("typeofbusiness"))) {


                    // newlist.add(activites.get(0).toString());

                    newlist.add(businessType.getString("typeofbusiness"));

                }







            }



        }





        return servicelist;
    }

    public ArrayList<String> businessfilterList(JSONArray business) {

        ArrayList<String> newlist = null;

        newlist = new ArrayList<>();

        try {

            for(int i = 0; i < business.length(); i++) {


                JSONObject f = null;

                f = (JSONObject) business.get(i);


                JSONArray activites = f.getJSONArray("activity");

                JSONObject businessType = (JSONObject) activites.get(0);


                if (!newlist.contains(businessType.getString("typeofbusiness"))) {


                    // newlist.add(activites.get(0).toString());

                    newlist.add(businessType.getString("typeofbusiness"));

                }




            }

        }



        catch (JSONException e) {
            e.printStackTrace();
        }





        return newlist;

    }

    public JSONArray businessfilterList(JSONArray business, ArrayList<String> types) throws JSONException {



        JSONArray newlist;

        newlist = new JSONArray();

        for (int i = 0; i < business.length(); i++) {


            JSONObject f = (JSONObject) business.get(i);

            JSONArray activites = f.getJSONArray("activity");

            JSONObject businessType = (JSONObject) activites.get(0);


            for (int j = 0; j < types.size(); j++) {


                if (businessType.getString("typeofservice").equals(types.get(j))) {



                    newlist.put(business.get(i));



                }


            }


        }







        return newlist;

    }


}
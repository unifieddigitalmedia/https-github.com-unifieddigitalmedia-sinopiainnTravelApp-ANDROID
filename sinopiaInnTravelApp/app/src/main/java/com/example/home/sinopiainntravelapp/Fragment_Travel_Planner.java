package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Travel_Planner extends Fragment {


    EditText rooms;

    EditText amenities;

    Button getTotal;

    Bundle bundle;

    private static GoogleMap mMap;

    private static Double latitude, longitude;

    BuildStringBusiness TextSetter ;

    EditText placesofinterest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_travel_planner, container, false);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Travel Planner");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);


        bundle = this.getArguments();

        placesofinterest = (EditText) rootView.findViewById(R.id.placesofinterest);

        placesofinterest.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {



                                                    Fragment_Places new_fragment = new Fragment_Places();

                                                    Bundle bundle1 = new Bundle();


                                                    if(bundle.getInt("Activity") == 0){

                                                        bundle1.putInt("Fragment", 0);

                                                        bundle1.putInt("Activity", 0);

                                                        if (((Activity_Home) getActivity()).JsonBusinesses != null) {

                                                            bundle1.putString("Places",((Activity_Home) getActivity()).JsonBusinesses.toString());

                                                            new_fragment.setArguments(bundle1);

                                                            ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment);


                                                        }



                                                    }else {



                                                        bundle1.putInt("Fragment", 0);

                                                        bundle1.putInt("Activity", 1);

                                                        if (((Activity_CheckIn) getActivity()).JsonBusinesses != null) {

                                                            bundle1.putString("Places",((Activity_CheckIn) getActivity()).JsonBusinesses.toString());

                                                            new_fragment.setArguments(bundle1);

                                                            ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment);


                                                        }

                                                    }




                                                }

                                            });


        rooms = (EditText) rootView.findViewById(R.id.rooms);

        rooms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


               // String json = "[{\"_id\":\"5747200a864730dd9d55d455\",\"businessname\":\"Mille Fleurs\",\"businessaddress\":\"Matthews Ave Port Antonio, Jamaica\",\"businessphone\":\"+1 876-993-7267\",\"businesswebsite\":\"http://www.hotelmockingbirdhill.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1763329\",\"Longitude\":\"-76.44973749999997\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Local Taste\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472023864730dd9d55d456\",\"businessname\":\"Bush Bar\",\"businessaddress\":\"18.173906, -76.407411\",\"businessphone\":\"+1 876-993-7000\",\"businesswebsite\":\"http://www.geejamhotel.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.172243\",\"Longitude\":\"-76.40768559999998\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Fusion\"}],\"typeofservice\":[\"Fusion\"],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747202b864730dd9d55d457\",\"businessname\":\"Woody's\",\"businessaddress\":\"Matthews Ave Port Antonio, Jamaica\",\"businessphone\":\"+1 876-993-7888\",\"businesswebsite\":\"http://www.geejamhotel.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1763329\",\"Longitude\":\"-76.44973749999997\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Local Taste\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747203e864730dd9d55d458\",\"businessname\":\"San San Tropez\",\"businessaddress\":\"San San Tropez Portland Rd Port Antonio Jamaica\",\"businessphone\":\"+1 876-999-0305\",\"businesswebsite\":\"http://www.SanSanTropez.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1758487\",\"Longitude\":\"-76.45340139999996\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"European\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472047864730dd9d55d459\",\"businessname\":\"Blue Lagoon\",\"businessaddress\":\"Blue Hole Rd Port Antonio Jamaica\",\"businessphone\":\"+1 876-978-6245\",\"businesswebsite\":\"http://www.BlueLagoon.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1718019\",\"Longitude\":\"-76.38738690000002\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Lesiure and Nightlife\",\"typeofservice\":\"Adventure\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472054864730dd9d55d45a\",\"businessname\":\"Winnifred Beach\",\"businessaddress\":\"\",\"businessphone\":\"\",\"businesswebsite\":\"http://www.BlueLagoon.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.0844274\",\"Longitude\":\"-76.4100267\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Public\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472063864730dd9d55d45b\",\"businessname\":\"Frenchman's Cove\",\"businessaddress\":\"Frenchman's Cove beach Jamaica\",\"businessphone\":\"\",\"businesswebsite\":\"\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1755481\",\"Longitude\":\"-76.40021689999998\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Private\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747206d864730dd9d55d45c\",\"businessname\":\"San San Beach\",\"businessaddress\":\"San San Beach, Port Antonio, Portland\",\"businessphone\":\"\",\"businesswebsite\":\"\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1712764\",\"Longitude\":\"-76.44763209999996\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Private\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"}]";


                Fragment_BusinessTypes new_fragment = new Fragment_BusinessTypes();

                Bundle bundle1 = new Bundle();


                if(bundle.getInt("Activity") == 0){


                    bundle1.putInt("Fragment", 0);

                    bundle1.putInt("Activity", 0);

                    bundle1.putString("BuinsessTypes", ((Activity_Home) getActivity()).JsonBusinesses.toString());

                    new_fragment.setArguments(bundle1);

                    ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment);


                }else {



                    bundle1.putInt("Fragment", 0);

                    bundle1.putInt("Activity", 1);

                    bundle1.putString("BuinsessTypes", ((Activity_CheckIn) getActivity()).JsonBusinesses.toString());

                    new_fragment.setArguments(bundle1);

                    ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment);

                }




            }


        });




        amenities = (EditText) rootView.findViewById(R.id.amenities);

        amenities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Fragment_Services new_fragment = new Fragment_Services();

                Bundle bundle1 = new Bundle();

                bundle1.putInt("Fragment", 1);
                bundle1.putInt("Activity", bundle.getInt("Activity"));

                if(bundle.getInt("Activity") == 0){



                    if(((Activity_Home) getActivity()).businessTypeList != null)
                    {

                        String json = "[{\"_id\":\"5747200a864730dd9d55d455\",\"businessname\":\"Mille Fleurs\",\"businessaddress\":\"Matthews Ave Port Antonio, Jamaica\",\"businessphone\":\"+1 876-993-7267\",\"businesswebsite\":\"http://www.hotelmockingbirdhill.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1763329\",\"Longitude\":\"-76.44973749999997\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Local Taste\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472023864730dd9d55d456\",\"businessname\":\"Bush Bar\",\"businessaddress\":\"18.173906, -76.407411\",\"businessphone\":\"+1 876-993-7000\",\"businesswebsite\":\"http://www.geejamhotel.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.172243\",\"Longitude\":\"-76.40768559999998\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Fusion\"}],\"typeofservice\":[\"Fusion\"],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747202b864730dd9d55d457\",\"businessname\":\"Woody's\",\"businessaddress\":\"Matthews Ave Port Antonio, Jamaica\",\"businessphone\":\"+1 876-993-7888\",\"businesswebsite\":\"http://www.geejamhotel.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1763329\",\"Longitude\":\"-76.44973749999997\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Local Taste\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747203e864730dd9d55d458\",\"businessname\":\"San San Tropez\",\"businessaddress\":\"San San Tropez Portland Rd Port Antonio Jamaica\",\"businessphone\":\"+1 876-999-0305\",\"businesswebsite\":\"http://www.SanSanTropez.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1758487\",\"Longitude\":\"-76.45340139999996\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"European\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472047864730dd9d55d459\",\"businessname\":\"Blue Lagoon\",\"businessaddress\":\"Blue Hole Rd Port Antonio Jamaica\",\"businessphone\":\"+1 876-978-6245\",\"businesswebsite\":\"http://www.BlueLagoon.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1718019\",\"Longitude\":\"-76.38738690000002\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Lesiure and Nightlife\",\"typeofservice\":\"Adventure\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472054864730dd9d55d45a\",\"businessname\":\"Winnifred Beach\",\"businessaddress\":\"\",\"businessphone\":\"\",\"businesswebsite\":\"http://www.BlueLagoon.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.0844274\",\"Longitude\":\"-76.4100267\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Public\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472063864730dd9d55d45b\",\"businessname\":\"Frenchman's Cove\",\"businessaddress\":\"Frenchman's Cove beach Jamaica\",\"businessphone\":\"\",\"businesswebsite\":\"\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1755481\",\"Longitude\":\"-76.40021689999998\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Private\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747206d864730dd9d55d45c\",\"businessname\":\"San San Beach\",\"businessaddress\":\"San San Beach, Port Antonio, Portland\",\"businessphone\":\"\",\"businesswebsite\":\"\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1712764\",\"Longitude\":\"-76.44763209999996\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Private\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"}]";

                        bundle1.putString("BuinsessTypes",json);

                        bundle1.putStringArrayList("types", ((Activity_Home) getActivity()).businessTypeList);

                        new_fragment.setArguments(bundle1);

                        ((Activity_Home) getActivity()).homePageFadeTransition(new_fragment);


                    }

                }else {

                    if(((Activity_CheckIn) getActivity()).businessTypeList != null) {

                        String json = "[{\"_id\":\"5747200a864730dd9d55d455\",\"businessname\":\"Mille Fleurs\",\"businessaddress\":\"Matthews Ave Port Antonio, Jamaica\",\"businessphone\":\"+1 876-993-7267\",\"businesswebsite\":\"http://www.hotelmockingbirdhill.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1763329\",\"Longitude\":\"-76.44973749999997\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Local Taste\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472023864730dd9d55d456\",\"businessname\":\"Bush Bar\",\"businessaddress\":\"18.173906, -76.407411\",\"businessphone\":\"+1 876-993-7000\",\"businesswebsite\":\"http://www.geejamhotel.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.172243\",\"Longitude\":\"-76.40768559999998\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Fusion\"}],\"typeofservice\":[\"Fusion\"],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747202b864730dd9d55d457\",\"businessname\":\"Woody's\",\"businessaddress\":\"Matthews Ave Port Antonio, Jamaica\",\"businessphone\":\"+1 876-993-7888\",\"businesswebsite\":\"http://www.geejamhotel.com/\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1763329\",\"Longitude\":\"-76.44973749999997\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"Local Taste\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747203e864730dd9d55d458\",\"businessname\":\"San San Tropez\",\"businessaddress\":\"San San Tropez Portland Rd Port Antonio Jamaica\",\"businessphone\":\"+1 876-999-0305\",\"businesswebsite\":\"http://www.SanSanTropez.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1758487\",\"Longitude\":\"-76.45340139999996\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Food and Drink\",\"typeofservice\":\"European\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472047864730dd9d55d459\",\"businessname\":\"Blue Lagoon\",\"businessaddress\":\"Blue Hole Rd Port Antonio Jamaica\",\"businessphone\":\"+1 876-978-6245\",\"businesswebsite\":\"http://www.BlueLagoon.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1718019\",\"Longitude\":\"-76.38738690000002\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Lesiure and Nightlife\",\"typeofservice\":\"Adventure\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472054864730dd9d55d45a\",\"businessname\":\"Winnifred Beach\",\"businessaddress\":\"\",\"businessphone\":\"\",\"businesswebsite\":\"http://www.BlueLagoon.com\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.0844274\",\"Longitude\":\"-76.4100267\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Public\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"57472063864730dd9d55d45b\",\"businessname\":\"Frenchman's Cove\",\"businessaddress\":\"Frenchman's Cove beach Jamaica\",\"businessphone\":\"\",\"businesswebsite\":\"\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1755481\",\"Longitude\":\"-76.40021689999998\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Private\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"},{\"_id\":\"5747206d864730dd9d55d45c\",\"businessname\":\"San San Beach\",\"businessaddress\":\"San San Beach, Port Antonio, Portland\",\"businessphone\":\"\",\"businesswebsite\":\"\",\"businessemail\":\"\",\"businessdescription\":\"\",\"country\":\"\",\"coordinates\":{\"Latitude\":\"18.1712764\",\"Longitude\":\"-76.44763209999996\"},\"nameofevent\":\"\",\"timeofevent\":\"\",\"dateofevent\":\"\",\"activity\":[{\"typeofbusiness\":\"Beaches\",\"typeofservice\":\"Private\"}],\"typeofactivity\":[],\"contactname\":\"\",\"location\":\"\",\"logourl\":\"\",\"showcaseurl\":[],\"comments\":[],\"averagerating\":\"\",\"avergaeprice\":\"\",\"date\":\"\",\"enabled\":\"\"}]";

                        bundle1.putString("BuinsessTypes", json);

                        bundle1.putStringArrayList("types", ((Activity_CheckIn) getActivity()).businessTypeList);

                        new_fragment.setArguments(bundle1);

                        ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment);

                    }

                }



            }


        });

        getTotal = (Button) rootView.findViewById(R.id.goToBusinessTypes);

        getTotal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Fragment_Reservation new_fragment = new Fragment_Reservation();

                Fragment_Bill new_fragment_bill = new Fragment_Bill();





                    if(getArguments().getInt("Activity") == 0) {



                        if ( ((Activity_Home) getActivity()).itinerary.length() != 0) {



                            ((Activity_Home) getActivity()).homePageFadeTransition(new Fragment_Reservation());



                        }else {






                        }



                    }else{



                        if ( ((Activity_CheckIn) getActivity()).itinerary.length() != 0) {


                          ((Activity_CheckIn) getActivity()).onBraintreeSubmit();





                        }else {






                        }



                    }








            }


        });

        // SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        //


       /* SupportMapFragment mMapFragment = SupportMapFragment.newInstance();


        mMapFragment.getMapAsync(this);

        getChildFragmentManager().beginTransaction().add(R.id.container, mMapFragment).addToBackStack(null).commit();
*/

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });



        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));


        return rootView;



    }

    /***** Sets up the map if it is possible to do so *****/
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {

            // Try to obtain the map from the SupportMapFragment.

            // ((homePage) getActivity()).getSupportFragmentManager().findFragmentById(R.id.map);

            //SupportMapFragment mapFragment =  (SupportMapFragment)
            //mapFragment.getMapAsync(getActivity());

            //mMap = ((SupportMapFragment) MapsActivity.fragmentManager.findFragmentById(R.id.location_map)).getMap();

            // Check if we were successful in obtaining the map.

            // if (mMap != null)

            //  setUpMap();
        }
    }


    @Override
    public void onResume(){

        super.onResume();

        TextSetter = new BuildStringBusiness(getActivity());


        try {

            if(bundle.getInt("Activity") == 0){

                rooms.setText(TextSetter.buildString(((Activity_Home)getActivity()).businessTypeList));

                amenities.setText(TextSetter.buildString(((Activity_Home) getActivity()).amenititesTypeList));

                placesofinterest.setText(TextSetter.buildStringJson(((Activity_Home) getActivity()).itinerary));


              /*  if(((Activity_Home) getActivity()).JsonBusinesses != null){

                    for (int i = 0; i < ((Activity_Home) getActivity()).JsonBusinesses.length(); i++) {

                        JSONObject f = (JSONObject) ((Activity_Home) getActivity()).JsonBusinesses.get(i);

                        if(f.getJSONObject("coordinates").getString("Latitude") != null || f.getJSONObject("coordinates").getString("Longitude") != null  ){


                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(f.getJSONObject("coordinates").getString("Latitude")), Double.valueOf(f.getJSONObject("coordinates").getString("Longitude")))).title(f.getString("businessname")).snippet(f.getString("businessdescription")));

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(f.getJSONObject("coordinates").getString("Latitude")), Double.valueOf(f.getJSONObject("coordinates").getString("Longitude"))), 12.0f));


                        }


                    }




                }*/


            }
            else{

                rooms.setText(TextSetter.buildString(((Activity_CheckIn)getActivity()).businessTypeList));

                amenities.setText(TextSetter.buildString(((Activity_CheckIn) getActivity()).amenititesTypeList));

                placesofinterest.setText(TextSetter.buildStringJson(((Activity_CheckIn) getActivity()).itinerary));

               /* if(((Activity_CheckIn) getActivity()).JsonBusinesses != null) {


                    for (int i = 0; i < ((Activity_CheckIn) getActivity()).JsonBusinesses.length(); i++) {

                        JSONObject f = (JSONObject) ((Activity_CheckIn) getActivity()).JsonBusinesses.get(i);

                        if(f.getJSONObject("coordinates").getString("Latitude") != null || f.getJSONObject("coordinates").getString("Longitude") != null  ){


                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(f.getJSONObject("coordinates").getString("Latitude")), Double.valueOf(f.getJSONObject("coordinates").getString("Longitude")))).title(f.getString("businessname")).snippet(f.getString("businessdescription")));

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(f.getJSONObject("coordinates").getString("Latitude")), Double.valueOf(f.getJSONObject("coordinates").getString("Longitude"))), 12.0f));


                        }


                    }


                }*/


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    /*@Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }
*/

}

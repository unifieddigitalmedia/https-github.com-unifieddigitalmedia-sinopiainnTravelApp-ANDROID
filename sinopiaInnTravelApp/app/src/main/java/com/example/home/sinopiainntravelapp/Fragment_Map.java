package com.example.home.sinopiainntravelapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Map extends Fragment implements OnMapReadyCallback {

    private static GoogleMap mMap;

    private static Double latitude, longitude;

    private static String title;

    Bundle bundle;

    JSONObject obj;

    EditText location;
    public Fragment_Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment__map, container, false);

        obj = new JSONObject();

        latitude = getArguments().getDouble("latitude");

        longitude = getArguments().getDouble("longitude");

        title = getArguments().getString("title");

        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();

        mMapFragment.getMapAsync(this);

        getChildFragmentManager().beginTransaction().add(R.id.container, mMapFragment).addToBackStack(null).commit();

        location = (EditText) rootView.findViewById(R.id.promo);

        location.setVisibility(View.GONE);

        return rootView;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng spot = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(spot).title(title));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(spot));

        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.animateCamera(zoom);

    }


}
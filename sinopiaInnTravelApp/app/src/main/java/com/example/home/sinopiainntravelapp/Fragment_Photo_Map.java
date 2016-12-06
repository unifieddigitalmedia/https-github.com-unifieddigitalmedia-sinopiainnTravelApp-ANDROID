package com.example.home.sinopiainntravelapp;


import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Photo_Map extends Fragment implements OnMapReadyCallback {

    private static GoogleMap mMap;

    private static Double latitude, longitude;

    private static String title;

    Bundle bundle;

    JSONObject obj;

    EditText location;


    private LatLng latLng;
    private Marker marker;
    Geocoder geocoder;

    public Fragment_Photo_Map() {
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

        location.setOnEditorActionListener(

                new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||

                                actionId == EditorInfo.IME_ACTION_DONE ||

                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                            if (!event.isShiftPressed() ) {


                                ((Activity_CheckIn) getActivity()).location = v.getText().toString();

                                return true;


                            } else {





                            }
                        }

                        return false;
                    }
                });

        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        return rootView;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //save current location
                latLng = point;

                Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");

                List<android.location.Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(point.latitude, point.longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                android.location.Address address = addresses.get(0);

                if (address != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                        sb.append(address.getAddressLine(i) + "\n");
                    }
                    Toast.makeText(getActivity(), sb.toString(), Toast.LENGTH_LONG).show();
                }

                //remove previously placed Marker
                if (marker != null) {
                    marker.remove();
                }

                //place marker where user just clicked
                marker = mMap.addMarker(new MarkerOptions().position(point).title("Marker")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

            }
        });

        LatLng spot = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(spot).title(title));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(spot));

        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.animateCamera(zoom);

    }


}
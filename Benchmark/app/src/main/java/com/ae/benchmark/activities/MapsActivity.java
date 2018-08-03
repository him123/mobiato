package com.ae.benchmark.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    List<Customer> itemList = new ArrayList<>();
    DBManager dbManager;
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        dbManager = new DBManager(this);
        dbManager.open();

        itemList = dbManager.getAllCust();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        for (int i=0 ; i<itemList.size() ; i++){

            LatLng sydney = new LatLng(Double.parseDouble(itemList.get(i).cust_latitude),
                    Double.parseDouble(itemList.get(i).cust_longitude));
//
//            mMap.addMarker(new MarkerOptions().position(sydney).title(itemList.get(0).cust_name_en));
//


            myMarker = googleMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title(itemList.get(i).cust_name_en)
                    .snippet(itemList.get(i).cust_address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.equals(myMarker))
        {
            //handle click here
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="+ myMarker.getPosition().latitude+"&daddr="+myMarker.getPosition().latitude+",45.345"));
            startActivity(intent);
        }

        return false;
    }
}

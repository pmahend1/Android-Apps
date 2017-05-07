package com.uncc.prateek.locationtracker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    Marker currentMarker;
    LocationManager mLocationManager;
    public final int SETTINGS_CODE = 0x10;
    LocationListener mLocationListener;
    boolean trackingMode = false;
    PolylineOptions polyOptions=new PolylineOptions();
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    LatLngBounds bounds = null;
    Polyline polyline;
    ArrayList<LatLng> points = new ArrayList<LatLng>();
    Marker firstMarker ;
    Marker lastMarker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        LatLng charlotte = new LatLng(35.22, -80.84);
        //currentMarker = mMap.addMarker(new MarkerOptions().position(charlotte).title("Charlotte, NC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(charlotte));
        //currentMarker.setTag(0);

        mMap.setOnMapLongClickListener(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            //&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            // TODO: Consider calling

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Log.d("maps", "On my location button click");
                return false;
            }
        });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        trackingMode = !trackingMode;
        if (trackingMode) {
            points.clear();
            mMap.clear();
            Toast.makeText(MapsActivity.this, "Location Tracking has been enabled", Toast.LENGTH_LONG).show();

            //mMap.getCameraPosition()
        } else {
            Toast.makeText(MapsActivity.this, "Location Tracking has been disabled", Toast.LENGTH_LONG).show();
            trackingMode=false;

            try{
                lastMarker = mMap.addMarker(new MarkerOptions().position(points.get(points.size()-1)).title("Last Point"));
                points.clear();
            }catch (Exception e){
                Log.d("demo",e.getMessage());
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        Log.i("demo", "OK pressed");
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    } else {
                        Log.i("demo", "Cancel pressed");
                        finish();
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS not enabled");
            builder.setMessage("Would you like to turn on GPS?");
            builder.setPositiveButton("OK", listener).setNegativeButton("Cancel", listener);

            AlertDialog alert = builder.create();
            alert.show();
        } else {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    if(trackingMode){
                        Log.d("demo", location.getLatitude() + " " + location.getLongitude());
                        LatLng trackerLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                        mMap.clear();
                        points.add(trackerLatLng);
                        firstMarker = mMap.addMarker(new MarkerOptions().position(points.get(0)).title("Start Point"));
                        Log.d("demo","setting poly");
                        Log.d("demo","size "+ points.size() + " " + points.toString());


                        polyOptions = new PolylineOptions();
                        polyOptions.addAll(points);
                        polyOptions.width(6);
                        polyOptions.color(Color.BLUE);
                        polyline = mMap.addPolyline(polyOptions);
                        polyline.setVisible(true);
                        builder.include(new LatLng( location.getLatitude(),location.getLongitude()));
                        bounds = builder.build();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,5));
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ||
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                Log.d("map", "Setting not activated.");

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {


                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            SETTINGS_CODE);

                }
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, mLocationListener);Log.i("demo","here2");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SETTINGS_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                   // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, mLocationListener);Log.i("demo","here2");


                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}

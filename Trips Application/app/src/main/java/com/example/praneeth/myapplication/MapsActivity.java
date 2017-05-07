package com.example.praneeth.myapplication;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Button addButton;
    String resultLocation=null;
    Address address=null;
    DatabaseReference dbr;
    Trip currentTrip;

    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList.size()>0){
                 address = addressList.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                // Fetch the address lines using getAddressLine,
                // join them, and send them to the thread.
                for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                 resultLocation = TextUtils.join(System.getProperty("line.separator"),
                        addressFragments);
                mMap.addMarker(new MarkerOptions().position(latLng).title(resultLocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                addButton.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(this, "Address not found!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.d("d","In maps activity");
        addButton = (Button) findViewById(R.id.btnAddCurrentPlace);
        currentTrip = (Trip) getIntent().getSerializableExtra(HomeActivity.TRIP);

        addButton.setVisibility(View.INVISIBLE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dbr = FirebaseDatabase.getInstance().getReference("Trips").child(currentTrip.getTripID());
        addButton.setOnClickListener(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnAddCurrentPlace){
            if(mMap!=null && resultLocation!=null && address!=null){
                Place place = new Place(address.getLatitude(),address.getLongitude());
                place.setName(resultLocation);
                ArrayList<Place> places = currentTrip.places;
                if(places==null)
                    places=new ArrayList<>();
                places.add(place);
                dbr.child("places").setValue(places);
            }


        }

    }
}

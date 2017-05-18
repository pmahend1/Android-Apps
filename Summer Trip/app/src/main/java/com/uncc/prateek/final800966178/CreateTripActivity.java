package com.uncc.prateek.final800966178;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateTripActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etTripName;
    EditText etNumOfPeople;
    TextView tvCostValue;
    ArrayList<Place> places;
    private DatabaseReference databaseReference;

    Button btnViewTrip, btnAddToTrips, btnAddToWishList, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        try{
            DatabaseReference dbx = databaseReference.child("Places");
            dbx.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    places = new ArrayList<Place>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        GenericTypeIndicator<ArrayList<Place>> t = new GenericTypeIndicator<ArrayList<Place>>() {
                        };
                        Place tempDeal = child.getValue(Place.class);
                        if(tempDeal!=null){
                            places.add(tempDeal);
                            Log.d("d","deal is "+tempDeal.toString());
                        }

                    }
                    //temp.add(child.getValue(Trip.class));

  /*                  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvDeals);

                    RecyclerView.LayoutManager mLayoutManager = new
                            LinearLayoutManager(CreateTripActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);

                    RecyclerView.Adapter mAdapter = new DealsRVAdapter(CreateTripActivity.this,CreateTripActivity.this,places);
                    recyclerView.setAdapter(mAdapter);*/


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }catch (Exception e){

        }


        etTripName = (EditText) findViewById(R.id.etTripName);
        etNumOfPeople = (EditText) findViewById(R.id.etNumOfPeople);
        tvCostValue = (TextView) findViewById(R.id.tvCostValue);

        btnViewTrip = (Button) findViewById(R.id.btnViewTrip);
        btnAddToTrips = (Button) findViewById(R.id.btnAddToTrips);
        btnAddToWishList = (Button) findViewById(R.id.btnAddToWishList);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnViewTrip.setOnClickListener(this);
        btnAddToTrips.setOnClickListener(this);
        btnAddToWishList.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.btnViewTrip: {
                //Go to Trip
                Intent i1 = new Intent(this,MapsActivity.class);

                i1.putExtra("DEAL","deal");
                startActivity(i1);
                break;
            }
            case R.id.btnAddToTrips: {
                //add to trip
                break;
            }
            case R.id.btnAddToWishList: {
                //add to wishlist
                break;
            }
            case R.id.btnCancel: {
                //cancel
                finish();
                break;
            }

        }

    }
}

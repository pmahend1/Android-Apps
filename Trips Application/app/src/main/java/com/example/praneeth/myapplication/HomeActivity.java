package com.example.praneeth.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, TripAdapter.LinkData, MyTripAdapter.LinkData {
    public static final String TRIP = "TRIP";
    private static FirebaseUser firebaseUser;
    Button newTrip;
    ArrayList<Trip> joinableList;
    ArrayList<Trip> myTripsList;

    MyTripAdapter myTripsAdapter;
    TripAdapter joinableAdapter;
    RecyclerView joinableTripsView, myTripsView;
    String currentUserId = "";
    private FirebaseAuth firebaseAuthentication;
    private DatabaseReference databaseReference;

    /*@Override
    protected void onStart() {
        super.onStart();
        Query query = databaseReference.child("Trips");

        query.add(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Trip> temp = new ArrayList<Trip>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    temp.add(child.getValue(Trip.class));
                }

                for (Trip t : temp) {
                    Log.d("log", "Current user id " + currentUserId);
                    Log.d("log", "Trip creater user id " + t.getCreaterId());

                    if (!currentUserId.equals(t.getCreaterId())) {
                        Log.d("de", "Adding to joinable trips list");
                        joinableList.add(t);
                    } else if (currentUserId.equals(t.getCreaterId())) {
                        Log.d("de", "Adding to my trips list");
                        myTripsList.add(t);
                    } else {

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuthentication = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        newTrip = (Button) findViewById(R.id.button_newTrip);
        newTrip.setOnClickListener(this);
        joinableTripsView = (RecyclerView) findViewById(R.id.recyclerViewJoinableTripsList);
        myTripsView = (RecyclerView) findViewById(R.id.recyclerViewMyTripsList);
        try {
            currentUserId = firebaseAuthentication.getCurrentUser().getUid();

        } catch (Exception e) {

        }
        // getTripsList();
        joinableList = new ArrayList<Trip>();
        myTripsList = new ArrayList<Trip>();

        DatabaseReference dbx = databaseReference.child("Trips");
        dbx.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                joinableList = new ArrayList<Trip>();
                myTripsList = new ArrayList<Trip>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    GenericTypeIndicator<ArrayList<Trip>> t= new GenericTypeIndicator<ArrayList<Trip>>() {
                    };
                    Trip tempTrip  = child.getValue(Trip.class);
                    if (!currentUserId.equals(tempTrip.getCreaterId())
                            && tempTrip.isActive() && !tempTrip.getUsers().contains(currentUserId)) {
                        Log.d("de", "Adding to joinable trips list");
                        joinableList.add(tempTrip);
                    } else if (currentUserId.equals(tempTrip.getCreaterId()) || tempTrip.getUsers().contains(currentUserId)) {
                        Log.d("de", "Adding to my trips list");
                        myTripsList.add(tempTrip);
                    } else {

                    }
                    //temp.add(child.getValue(Trip.class));
                }


                joinableAdapter = new TripAdapter(HomeActivity.this, joinableList);
                joinableTripsView.setAdapter(joinableAdapter);
                joinableTripsView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(HomeActivity.this).build());
                LinearLayoutManager horizontalLayoutManagaer1
                        = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
                joinableTripsView.setLayoutManager(horizontalLayoutManagaer1);
                joinableAdapter.notifyDataSetChanged();

                //myTrips
                myTripsAdapter = new MyTripAdapter(HomeActivity.this, myTripsList);
                myTripsView.setAdapter(myTripsAdapter);
                myTripsView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(HomeActivity.this).build());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
                myTripsView.setLayoutManager(horizontalLayoutManagaer);
                myTripsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Other Trips to join


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trips_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contacts:
                Intent in = new Intent(HomeActivity.this, ContactsActivity.class);
                startActivity(in);
                break;
            case R.id.editProfile:
                Intent editIntent = new Intent(HomeActivity.this, UpdateProfile.class);
                startActivity(editIntent);
                break;
            case R.id.logout:
                firebaseAuthentication.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;

        }
        return (super.onOptionsItemSelected(item));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_newTrip:
                Intent intent = new Intent(HomeActivity.this, NewTripActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void goToChatRoom(Trip trip) {
        Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
        intent.putExtra(TRIP, trip);
        startActivity(intent);
    }


    @Override
    public void goToTrip(Trip trip) {
        Intent i = new Intent(this,TripDetailActivity.class);
        i.putExtra(TRIP,trip);
        startActivity(i);
    }

    @Override
    public void joinTrip(Trip trip, int position) {

        ArrayList<String> users = trip.getUsers();
        users.add(currentUserId);
        //update Firebase database
        DatabaseReference dbr = databaseReference.child("Trips");
        DatabaseReference dbrUpdate = databaseReference.child("Trips").child(trip.getTripID());

        dbrUpdate.child("users").setValue(users);

        joinableList.remove(position);

        myTripsList.add(trip);

    }


}

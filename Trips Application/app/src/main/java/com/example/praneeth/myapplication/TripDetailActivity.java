package com.example.praneeth.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TripDetailActivity extends AppCompatActivity implements View.OnClickListener , PlacesRecyclerAdapter.PlaceDataCallBack{

    Trip trip = null;
    ListView lvPeople;
    RecyclerView lvPlaces;

    Button btnAddPlaces, btnChatNow, btnWayTrip;
    ArrayList<String> users = new ArrayList<>();
    ArrayList<Place> placesList = new ArrayList<>();
    Map<String, User> userHashMap = new HashMap<String, User>();

    private FirebaseAuth firebaseAuthentication;
    private DatabaseReference dbRefUsers;

    private DatabaseReference dbRefTripPlaces;
    private DatabaseReference dbr;



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        lvPeople = (ListView) findViewById(R.id.lvPeople);
        lvPlaces = (RecyclerView) findViewById(R.id.lvPlaces);

        btnAddPlaces = (Button) findViewById(R.id.btnAddPlaces);
        btnChatNow = (Button) findViewById(R.id.btnChatNow);
        btnWayTrip = (Button) findViewById(R.id.btnWayTrip);

        btnAddPlaces.setOnClickListener(this);
        btnChatNow.setOnClickListener(this);
        btnWayTrip.setOnClickListener(this);

        trip = (Trip) getIntent().getSerializableExtra(HomeActivity.TRIP);

        firebaseAuthentication = FirebaseAuth.getInstance();
        dbRefUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        dbRefTripPlaces = FirebaseDatabase.getInstance().getReference().child("Trips").child(trip.getTripID()).child("places");

        dbr = FirebaseDatabase.getInstance().getReference("Trips").child(trip.getTripID());

        if (trip != null) {

            final ArrayList<String> usersKeys = trip.getUsers();
            //usersKeys = trip.getUsers();




            dbRefUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   /* GenericTypeIndicator<ArrayList<User>> gti = new GenericTypeIndicator<ArrayList<User>>() {
                    };
                    users = dataSnapshot.getValue(gti);*/
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        userHashMap.put(child.getKey(), child.getValue(User.class));
                    }
                    if (userHashMap.size() != 0) {
                        Log.d("demo", "here 1");
                        for (String key : usersKeys) {
                            User u = userHashMap.get(key);
                            Log.d("demo", "here 2");
                            users.add(u.getFirstname() + " " + u.getLastname());
                        }


                        ArrayAdapter adapter = new ArrayAdapter<String>(TripDetailActivity.this, android.R.layout.simple_expandable_list_item_1, users);
                        lvPeople.setAdapter(adapter);
                        adapter.setNotifyOnChange(true);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            dbRefTripPlaces.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<ArrayList<Place>> gtiPlace =  new GenericTypeIndicator<ArrayList<Place>>() {
                    };
                    placesList = dataSnapshot.getValue(gtiPlace);
                    if(placesList==null) {
                        placesList = new ArrayList<Place>();
                        btnWayTrip.setVisibility(View.INVISIBLE);
                    }

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TripDetailActivity.this);


                    PlacesRecyclerAdapter adapter1 = new PlacesRecyclerAdapter(TripDetailActivity.this,TripDetailActivity.this,placesList); //<Place>(TripDetailActivity.this, android.R.layout.simple_list_item_1, placesList);
                    lvPlaces.setLayoutManager(mLayoutManager);

                    lvPlaces.setAdapter(adapter1);

                    adapter1.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {
            finish();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddPlaces: {
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra(HomeActivity.TRIP, trip);
                startActivity(intent);

                break;
            }
            case R.id.btnChatNow: {
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(HomeActivity.TRIP, trip);
                startActivity(intent);
                break;
            }
            case R.id.btnWayTrip:{
/*                Intent i = new Intent(this,TestActivity.class);
                i.putExtra(HomeActivity.TRIP,trip);
                startActivity(i);*/
                //getFragmentManager().beginTransaction().add(R.id.container,new WayTripActivity(TripDetailActivity.this),"tag_my_fav_movies").commit();
                Intent i = new Intent(this,WayTripActivity.class);
                i.putExtra(HomeActivity.TRIP,trip);
                startActivity(i);
                Log.d("tag","Step 1");
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void deletePlace(int position) {
        placesList.remove(position);
        dbr.child("places").setValue(placesList);
    }
}

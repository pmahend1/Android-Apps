package com.example.praneeth.myapplication;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class TripAdapter extends RecyclerView.Adapter{

    HomeActivity activity;
    List<Trip> tripsList;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    public TripAdapter(HomeActivity activity, List<Trip> tripsList) {
        this.activity = activity;
        this.tripsList = tripsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.join_recycler_trips, parent, false);
        TripHolder tripHolder=new TripHolder(v);
        return tripHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Trip trip;
        int pos;
        pos=position;
        trip=tripsList.get(position);
        mAuth= FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com");
        final TripHolder holderObj = (TripHolder) holder;
        holderObj.tripNameH.setText(trip.getTitle());
        holderObj.locationH.setText(trip.getLocation());
        StorageReference imagesRef=storageRef.child("images/"+trip.getImageURL());
        Glide.with(activity)
                .using(new FirebaseImageLoader())
                .load(imagesRef)
                .into(holderObj.trip_picH);
        /*holderObj.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            activity.goToChatRoom(trip);
            }
        });*/
        holderObj.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.joinTrip(trip, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }
    public  interface LinkData{

        void joinTrip(Trip trip, int position);
    }
}

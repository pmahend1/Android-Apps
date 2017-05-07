package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class PendingAdapter extends RecyclerView.Adapter {

    FirebaseAuth mAuth;
    FirebaseStorage storage;
    ContactsActivity activity;
    Set<User>  friendRequestsList;

    List<User> finalList=new ArrayList<User>();

    public PendingAdapter(ContactsActivity activity,Set<User> friendRequestsList) {
        this.activity = activity;
        this.friendRequestsList = friendRequestsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pending_requests, parent, false);
        PendingHolder pendingHolder=new PendingHolder(v);
        return pendingHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final User user;
        int pos;
        pos=position;
        final PrettyTime time=new PrettyTime();;
        finalList.addAll(friendRequestsList);
        user=finalList.get(position);
        mAuth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com");
        final PendingHolder holderObj = (PendingHolder) holder;
        holderObj.UserNameP.setText(user.firstname+" "+user.lastname);
        holderObj.statusP.setText(user.getStatus());



        StorageReference imagesRef=storageRef.child("images/"+user.getProfilepicUri());
        Glide.with(activity)
                .using(new FirebaseImageLoader())
                .load(imagesRef)
                .into(holderObj.userPicP);
        holderObj.acceptP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date=new Date();
                Friend  friend =new Friend(user,time.format(date));
                activity.acceptFriendRequest(friend,user);
            }
        });
        holderObj.rejectP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return friendRequestsList.size();
    }
    public  interface LinkData{
        void acceptFriendRequest(Friend friend, User friendRequest);
        void rejectFriendRequest(FriendRequest friendRequest);
    }
}

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

import java.util.List;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter {
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    ContactsActivity activity;
    List<Friend> friendsList;

    public FriendsAdapter(ContactsActivity activity, List<Friend> friendsList) {
        this.activity = activity;
        this.friendsList = friendsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_friends, parent, false);
        FriendsHolder friendsHolder=new FriendsHolder(v);
        return friendsHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final User user;
        int pos;
        pos=position;
        user=friendsList.get(position);
        mAuth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com");
        final FriendsHolder holderObj = (FriendsHolder) holder;
        holderObj.UserName.setText(user.firstname+" "+user.lastname);
        holderObj.status.setText(user.getStatus());
        StorageReference imagesRef=storageRef.child("images/"+user.getProfilepicUri());
        Glide.with(activity)
                .using(new FirebaseImageLoader())
                .load(imagesRef)
                .into(holderObj.userPic);
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}

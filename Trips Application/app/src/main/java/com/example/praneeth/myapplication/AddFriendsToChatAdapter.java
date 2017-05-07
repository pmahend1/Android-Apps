package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class AddFriendsToChatAdapter extends RecyclerView.Adapter {
    AddFriendsToChat activity;
    List<Friend> friendList;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    public AddFriendsToChatAdapter(AddFriendsToChat activity, List<Friend> friendList) {
        this.activity = activity;
        this.friendList = friendList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_add_friends_to_chat, parent, false);
        AddFriendsToChatHolder addFriendsToChatHolder=new AddFriendsToChatHolder(v);
        return addFriendsToChatHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Friend friend;

        int pos;
        pos=position;
        friend=friendList.get(position);
        mAuth= FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com");
        final AddFriendsToChatHolder holderObj = (AddFriendsToChatHolder) holder;
        holderObj.usernamefriend.setText(friend.getFirstname()+ " "+ friend.getLastname());
        holderObj.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    activity.addToTrip(friend);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
    public  interface LinkData{
        void addToTrip(Friend friend);
    }
}

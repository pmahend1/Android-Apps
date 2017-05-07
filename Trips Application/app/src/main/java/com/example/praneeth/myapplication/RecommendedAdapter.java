package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class RecommendedAdapter extends RecyclerView.Adapter {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    ContactsActivity activity;
    Set<User> usersList;
    List<User> userList=new ArrayList<User>();

    public RecommendedAdapter(ContactsActivity activity, Set<User> usersList) {
        this.activity = activity;
        this.usersList = usersList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_recommendations, parent, false);
        RecommendedHolder recommendedHolder=new RecommendedHolder(v);
        return recommendedHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final User user;
        int pos;
        pos=position;
        userList.addAll(usersList);
        user=userList.get(pos);
        mAuth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com");
        final RecommendedHolder holderObj = (RecommendedHolder) holder;
        holderObj.UserName.setText(user.firstname+" "+user.lastname);
        holderObj.status.setText(user.getStatus());
        StorageReference imagesRef=storageRef.child("images/"+user.getProfilepicUri());
        Glide.with(activity)
                .using(new FirebaseImageLoader())
                .load(imagesRef)
                .into(holderObj.userPic);
        holderObj.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = databaseReference.child(mAuth.getCurrentUser().getUid()).child("Friend_Requests").push().getKey();
                FriendRequest request=new FriendRequest();
                request.setFromKey(mAuth.getCurrentUser().getUid());
                request.setToKey(user.getUserKey());
                request.setRequestAction(false);
                Map<String, Object> postValues = request.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/Friend_Requests/" + key, postValues);
                databaseReference.updateChildren(childUpdates);
                holderObj.addFriendText.setText("Request sent");
            }
        });
    }
    @Override
    public int getItemCount() {
        return usersList.size();
    }
}

package com.example.praneeth.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddFriendsToChat extends AppCompatActivity implements AddFriendsToChatAdapter.LinkData, View.OnClickListener {
    public static final String TRIP = "TRIP";
    private static FirebaseUser firebaseUser;
    List<Friend> friendList;
    ArrayList<Friend> friendListNew;
    Set<Friend> friendsListAdd = new HashSet<>();
    Button addToList;
    AddFriendsToChatAdapter adapter;
    RecyclerView rView;
    Trip trip;
    private FirebaseAuth firebaseAuthentication;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuthentication = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        trip = (Trip) getIntent().getParcelableExtra(HomeActivity.TRIP);
        Log.d("YO", trip.toString());
        setContentView(R.layout.activity_add_friends_to_chat);
        friendList = new ArrayList<>();
        friendListNew = new ArrayList<>();
        addToList = (Button) findViewById(R.id.button_DoneAddChat);
        addToList.setOnClickListener(this);
        getFriendsList();
        rView = (RecyclerView) findViewById(R.id.recycleView_addFriendsChat);
        adapter = new AddFriendsToChatAdapter(this, friendList);
        rView.setAdapter(adapter);
        rView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(AddFriendsToChat.this, LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(horizontalLayoutManagaer);
        adapter.notifyDataSetChanged();
    }

    public void getFriendsList() {
        Query query = databaseReference.child("Friends").child(firebaseAuthentication.getCurrentUser().getUid());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Friend friend = dataSnapshot.getValue(Friend.class);
                friendList.add(friend);
                Log.d("friendsList", friendList.toString());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addToTrip(Friend friend) {
        friendsListAdd.add(friend);
        Log.d("Lmao", friendsListAdd.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_DoneAddChat:
                friendListNew.addAll(friendsListAdd);
                databaseReference.child("Trips").child(firebaseAuthentication.getCurrentUser().getUid() + "," + trip.getTripID()).setValue(trip);
                Intent intent = new Intent(AddFriendsToChat.this, ChatActivity.class);
                intent.putExtra(TRIP, trip);
                startActivity(intent);
                break;
        }
    }
}

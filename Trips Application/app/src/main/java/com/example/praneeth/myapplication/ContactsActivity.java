package com.example.praneeth.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContactsActivity extends AppCompatActivity implements PendingAdapter.LinkData {
    private static FirebaseUser firebaseUser;
    ArrayList<User> usersList = new ArrayList<User>();
    ArrayList<User> recommendationsList = new ArrayList<User>();
    ArrayList<User> recommendationsNew = new ArrayList<User>();
    ArrayList<Friend> friendsList = new ArrayList<Friend>();
    ArrayList<FriendRequest> requests = new ArrayList<FriendRequest>();
    Set<User> friendRequests = new HashSet<User>();
    FriendsAdapter fAdapter;
    RecommendedAdapter rAdapter;
    PendingAdapter pAdapter;
    RecyclerView friendsView;
    RecyclerView pendingView;
    RecyclerView recommendedView;
    Set<User> recommenderList = new HashSet<>();
    FirebaseUser mCurrentUser;//=firebaseAuthentication.getCurrentUser();
    String currentUserId = "";
    private FirebaseAuth firebaseAuthentication;
    private DatabaseReference databaseReference;
    String keyToRemove=null;

    User currentUserObj=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        firebaseAuthentication = FirebaseAuth.getInstance();
        mCurrentUser = firebaseAuthentication.getCurrentUser();
        currentUserId = mCurrentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        friendsView = (RecyclerView) findViewById(R.id.recyclerView_FriendsList);
        recommendedView = (RecyclerView) findViewById(R.id.recyclerView_RecommendationsList);
        pendingView = (RecyclerView) findViewById(R.id.recyclerView_RequestsList);
        getFriendRequests();
        getFriendsList();
        getContactsList();
        fAdapter = new FriendsAdapter(ContactsActivity.this, friendsList);

        friendsView.setAdapter(fAdapter);
        friendsView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(ContactsActivity.this, LinearLayoutManager.VERTICAL, false);
        friendsView.setLayoutManager(horizontalLayoutManagaer);
        fAdapter.notifyDataSetChanged();

        databaseReference.child("Users").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUserObj = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void generateFriendRequestsFromList() {
        for (FriendRequest req : requests) {
            for (User user : usersList) {
                if (req.getFromKey().equals(user.getUserKey())) {
                    friendRequests.add(user);
                    break;
                }
            }
        }
        pAdapter = new PendingAdapter(ContactsActivity.this, friendRequests);
        pendingView.setAdapter(pAdapter);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(ContactsActivity.this, LinearLayoutManager.VERTICAL, false);
        pendingView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        pendingView.setLayoutManager(horizontalLayoutManagaer);
        pAdapter.notifyDataSetChanged();

    }

    public void getFriendsList() {
        mCurrentUser = firebaseAuthentication.getCurrentUser();
        String uid = null;
        if (mCurrentUser != null) {
            uid = firebaseAuthentication.getCurrentUser().getUid();
            Query query = databaseReference.child("Friends").child(uid);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Friend friend = dataSnapshot.getValue(Friend.class);
                    friendsList.add(friend);
                    Log.d("friendsList", friendsList.toString());
                    fAdapter.notifyDataSetChanged();

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

    }

    public void getContactsList() {
        Query query = databaseReference.child("Users");
        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if (mCurrentUser != null) {
                    if (!user.getEmail().equalsIgnoreCase(firebaseAuthentication.getCurrentUser().getEmail())) {
                        usersList.add(user);
                        Log.d("user", usersList.toString());
                        createRecommendationsList();
                        generateFriendRequestsFromList();
                    }
                }

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

    public void getFriendRequests() {
        Query query = databaseReference.child("Friend_Requests");
        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FriendRequest request = dataSnapshot.getValue(FriendRequest.class);
                requests.add(request);
                Log.d("XYZ", requests.toString());
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

    public void createRecommendationsList() {
        boolean flag = true;
        boolean rqFlag = false;
        for (User user : usersList) {
            for (Friend friend : friendsList) {
                if (!user.getEmail().equalsIgnoreCase(friend.getEmail())) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                recommenderList.add(user);
            }


        }
       /* recommendationsNew.addAll(recommenderList);
        for(User recommender:recommendationsNew){
            for(FriendRequest rq:requests){
                if(recommender.getUserKey().equals(rq.getToKey()) || recommender.getUserKey().equals(rq.getFromKey()) ){
                    rqFlag=true;
                    recommendationsNew.remove(recommender);
                    break;
                }

            }
        }
        recommenderList.clear();
        recommenderList.addAll(recommendationsNew);
        */

        rAdapter = new RecommendedAdapter(ContactsActivity.this, recommenderList);
        recommendedView.setAdapter(rAdapter);
        recommendedView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(ContactsActivity.this, LinearLayoutManager.VERTICAL, false);
        recommendedView.setLayoutManager(horizontalLayoutManagaer);
        rAdapter.notifyDataSetChanged();
    }

    @Override
    public void acceptFriendRequest(Friend friend,User user) {
        if (mCurrentUser != null) {
            String key = databaseReference.child(firebaseAuthentication.getCurrentUser().getUid()).child("Friends").push().getKey();
            Map<String, Object> postValues = friend.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/Friends/" + firebaseAuthentication.getCurrentUser().getUid() + "/" + key, postValues);
            databaseReference.updateChildren(childUpdates);
            friendsList.add(friend);
            fAdapter.notifyDataSetChanged();

            DatabaseReference dbr = databaseReference.child("Users").child(currentUserId);

            ArrayList<String> friends =currentUserObj.friends;
            if(friends==null)
                friends=new ArrayList<>();
            friends.add(friend.getUserKey());
            dbr.child("friends").setValue(friends);

            friendRequests.remove(user);

            String from = user.getUserKey();
            String to = currentUserId;
            int x = -1;
            for(int i = 0 ;i<requests.size();i++){
                FriendRequest fr = requests.get(i);
                if(fr.fromKey.equals(from) && fr.toKey.equals(to)){
                    x = i;
                }
            }
            if(x!=-1){
                requests.remove(x);
            }

            final String from1 = user.getUserKey();
            final String to1 = currentUserId;
            //String key="";
            ///remove all entries of from to from database
            DatabaseReference dbx = databaseReference.child("Friend_Requests");
            dbx.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        FriendRequest request = child.getValue(FriendRequest.class);
                        //requests.add(request);
                        Log.d("XYZ", requests.toString());
                        if(request.fromKey.equals(from1) && request.toKey.equals(to1)){
                            child.getKey();
                            updateRemovableKey(child.getKey());

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //FirebaseDatabase fb = dbx.getDatabase()




        }

    }

    @Override
    public void rejectFriendRequest(FriendRequest friend) {

    }

    public void updateRemovableKey(String key){
        keyToRemove= key;
        removeFriendRequest(key);
    }

    public void removeFriendRequest(String key){
        if(keyToRemove!=null){
            DatabaseReference dbx = databaseReference.child("Friend_Requests");
            dbx.child(key).removeValue();
        }else{
            Toast.makeText(this, "Friend request delete error", Toast.LENGTH_SHORT).show();
        }

    }
}

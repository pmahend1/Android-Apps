package com.example.praneeth.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NewTripActivity extends AppCompatActivity implements View.OnClickListener {
    private static FirebaseUser firebaseUser;
    final int SELECT_IMAGE = 1234;
    ImageView addImage;
    EditText title, location;
    Button save;
    FirebaseStorage storage;
    Uri selectedImage;
    Uri downloadUrl;
    StorageReference storageRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String id = "";
    private FirebaseAuth firebaseAuthentication;
    private DatabaseReference databaseReference;
    private FirebaseUser mCurrentUser;

    public void updateUser(String id) {
        this.id = id;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentUser = firebaseAuthentication.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("ddd", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("ddd", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthentication.addAuthStateListener(mAuthListener);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("ddd", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("ddd", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        mCurrentUser = firebaseAuthentication.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        firebaseAuthentication = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        title = (EditText) findViewById(R.id.editText_TripName);
        location = (EditText) findViewById(R.id.editText_Location);
        addImage = (ImageView) findViewById(R.id.imageView_FtripPic);
        save = (Button) findViewById(R.id.button_save);
        save.setOnClickListener(this);
        addImage.setOnClickListener(this);
        storageRef = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("ddd", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                          // User is signed out
                    Log.d("ddd", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        try{
            Log.d("drre",mUser.toString());
            mUser.getToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                // Send token to your backend via HTTPS
                                updateUser(id);
                                // ...
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        }catch (Exception e){

        }



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    selectedImage = data.getData();
                    addImage.setImageURI(selectedImage);
                    StorageReference imagesRef = storageRef.child("images/" + selectedImage.getLastPathSegment());
                    UploadTask uploadTask = imagesRef.putFile(selectedImage);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadUrl = taskSnapshot.getDownloadUrl();
                            Log.d("downloadUrl", downloadUrl + "hey");
                        }
                    });

                }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                String key = databaseReference.child("Trips").push().getKey();
                if (CheckFieldValidator.checkField(title) && CheckFieldValidator.checkField(location)) {
                   // Trip trip = new Trip(key, title.getText().toString(), location.getText().toString(), selectedImage.getLastPathSegment());
                    try {
                        id = firebaseAuthentication.getCurrentUser().getUid();
                    } catch (Exception e) {

                    }
                    String imageURL = "";
                    try{
                        imageURL = selectedImage.getLastPathSegment();
                    }catch (Exception e){

                    }

                    Trip trip = new Trip(key, title.getText().toString(), location.getText().toString(), imageURL,id);

                    ArrayList<String> users=new ArrayList<String>();
                    users.add(id);
                    trip.setUsers(users);

                    Map<String, Object> postValues = trip.toBasicMap();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/Trips/" + id + "," + trip.getTripID(), postValues);
                    //databaseReference.updateChildren(childUpdates);
                    databaseReference.child("Trips").child(key).setValue(trip);
                    Intent intent = new Intent(NewTripActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.imageView_FtripPic:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"), SELECT_IMAGE);
                break;

        }
    }
}

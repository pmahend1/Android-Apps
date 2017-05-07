package com.example.praneeth.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuthentication;
    DatabaseReference databaseReference;
    ImageView profilePic;
    FirebaseStorage storage;
    User loggedInUser=new User();
    final int SELECT_IMAGE = 1234;
    Uri selectedImage;
    Uri downloadUrl;
    StorageReference storageRef;
    TextView welcomeLabel;
    Button finish;
    String statusText;
    EditText status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuthentication = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        profilePic=(ImageView) findViewById(R.id.imageView_ProPic);
        status=(EditText) findViewById(R.id.textView_Status);
        finish = (Button) findViewById(R.id.button2);
        storageRef = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com");
        profilePic.setOnClickListener(this);
        finish.setOnClickListener(this);
        welcomeLabel= (TextView) findViewById(R.id.WelcomeLabel);
        DatabaseReference query=databaseReference.child("Users").child(firebaseAuthentication.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loggedInUser=dataSnapshot.getValue(User.class);
                welcomeLabel.setText("Welcome "+loggedInUser.getFirstname()+" "+loggedInUser.getLastname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


}
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    selectedImage = data.getData();
                    loggedInUser.setProfilepicUri(selectedImage.getLastPathSegment());
                    profilePic.setImageURI(selectedImage);
                    StorageReference imagesRef=storageRef.child("images/"+selectedImage.getLastPathSegment());
                    UploadTask uploadTask= imagesRef.putFile(selectedImage);

                    uploadTask.addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadUrl = taskSnapshot.getDownloadUrl();
                            Log.d("downloadUrl",downloadUrl+"hey");
                        }
                    });

                }
        }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imageView_ProPic:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"),SELECT_IMAGE);
                break;
            case R.id.button2:
                if(!status.getText().toString().equalsIgnoreCase("")) {
                    statusText = status.getText().toString();
                }
                loggedInUser.setStatus(statusText);
                databaseReference.child("Users").child(firebaseAuthentication.getCurrentUser().getUid()).setValue(loggedInUser);
                Intent in = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(in);
                break;

        }
    }



}

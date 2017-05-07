package com.example.praneeth.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewAdapter.ClickHandler {
    public static final String TRIP = "TRIP";
    public static final String TAG = "ChatAct";
    public static final String TRIP_ID = "tripId";
    public static String currentUserId = "";


    private static FirebaseUser firebaseUser;
    Trip trip;
    ImageView send, imgAttach;
    EditText message;
    ArrayList<Message> msgList;
    String tripId = "";
    private FirebaseAuth firebaseAuthentication;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private DatabaseReference dbRefServTripChatRoom;
    private DatabaseReference dbRefUserChatRoom;
    private TextView tvChatTitle;
    private ImageView logoutBtn, sendImg, btnAttachImage;
    private ListView messageListView;
    private RecyclerView messageRV;
    private int positionToComment;
    private String userNameFromUid;
    private Bitmap imageInBitmap;
    private String imageName;
    private ArrayList<Chat> allConversations;
    private ArrayList<Chat> allServerConversations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //get Trip title
        Trip currentTrip = (Trip) getIntent().getSerializableExtra(TRIP);

        if (currentTrip != null) {
            Log.d(TAG, currentTrip.toString());


            firebaseAuthentication = FirebaseAuth.getInstance();

            //trip = (Trip) getIntent().getParcelableExtra(HomeActivity.TRIP);

            Log.d("YO", currentUserId.toString());
            tvChatTitle = (TextView) findViewById(R.id.tvChatTitle);
            tvChatTitle.setText(currentTrip.getTitle());
            tripId = currentTrip.getTripID();


            final DatabaseReference dbRefServChatRooms = FirebaseDatabase.getInstance().getReference().child("ChatRooms");

            dbRefServChatRooms.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(tripId)) {
                        Log.d(TAG, "ChatRoom instantiated");

                    } else {
                        Log.d(TAG, "ChatRoom not instantiated in Firebase still");
                        //create chatroom for this trip
                        dbRefServChatRooms.push().setValue(tripId);

                        //dbRefUser.child("ChatRooms").push().setValue(tripId);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            dbRefServTripChatRoom = dbRefServChatRooms.child(tripId);

            final DatabaseReference dbRefUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

            dbRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("ChatRooms")) {
                        Log.d(TAG, "user chat exists");
                    }
                    //create chat for trip
                    else {
                        dbRefUser.child("ChatRooms").push().setValue(tripId);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            messageRV = (RecyclerView) findViewById(R.id.recyclerView_chat);
            dbRefUserChatRoom = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("ChatRooms").child(tripId);

            dbRefUserChatRoom.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<ArrayList<Chat>> gti = new GenericTypeIndicator<ArrayList<Chat>>() {
                    };
                    allConversations = dataSnapshot.getValue(gti);
                    RecyclerViewAdapter rva;
                    if (allConversations != null && allConversations.size() != 0)
                        rva = new RecyclerViewAdapter(getApplicationContext(), R.layout.message_item, allConversations, ChatActivity.this);
                    else
                        rva = new RecyclerViewAdapter(getApplicationContext(), R.layout.message_item, new ArrayList<Chat>(), ChatActivity.this);
                    messageRV.setAdapter(rva);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            send = (ImageView) findViewById(R.id.btnSend);
            imgAttach = (ImageView) findViewById(R.id.btnAttachImage);
            message = (EditText) findViewById(R.id.messageField);
            msgList = new ArrayList<Message>();

            send.setOnClickListener(this);
            imgAttach.setOnClickListener(this);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            storage = FirebaseStorage.getInstance();

            LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            messageRV.setLayoutManager(lm);


            try {
                dbRefServChatRooms.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<Chat>> gti = new GenericTypeIndicator<ArrayList<Chat>>() {
                        };
                        allServerConversations = dataSnapshot.child(tripId).getValue(gti);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } catch (Exception e) {
                Log.d("demo", "");
            }

        } else {
            Toast.makeText(this, "Chatroom doesnt exist", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addmembers:
                Intent intent13 = new Intent(ChatActivity.this, AddFriendsToChat.class);
                intent13.putExtra(TRIP, trip);
                startActivity(intent13);
                break;
            case R.id.editTrip:
                Intent intent1 = new Intent(ChatActivity.this, NewTripActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                firebaseAuthentication.signOut();
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend: {


                Message msg = new Message();
                msg.setMessageText(message.getText().toString());
                msg.setDate(new Date());
                msg.setMsgType("text");
                msg.setSender(firebaseAuthentication.getCurrentUser().getDisplayName());
                msg.setMessageText(message.getText().toString());
                msg.setSenderKey(firebaseAuthentication.getCurrentUser().getUid());
                msgList.add(msg);


                if(allServerConversations==null){
                    allServerConversations = new ArrayList<>();
                }

                String message = ((TextView) findViewById(R.id.messageField)).getText().toString().trim();
                if (message == null || message == "" || message.length() == 0) {
                    Toast.makeText(this, "No message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (allConversations == null || allConversations.size() == 0) {
                    ArrayList<Chat> conversations = new ArrayList<>();
                    conversations.add(new Chat(firebaseUser.getUid(), message, userNameFromUid, new Chat.Comments(), null, new Date()));
                    allServerConversations.add(new Chat(firebaseUser.getUid(), message, userNameFromUid, new Chat.Comments(), null, new Date()));

                    dbRefUserChatRoom.setValue(conversations);
                    dbRefServTripChatRoom.setValue(allServerConversations);
                } else {
                    allConversations.add(new Chat(firebaseUser.getUid(), message, userNameFromUid, new Chat.Comments(), null, new Date()));
                    allServerConversations.add(new Chat(firebaseUser.getUid(), message, userNameFromUid, new Chat.Comments(), null, new Date()));

                    dbRefUserChatRoom.setValue(allConversations);
                    dbRefServTripChatRoom.setValue(allServerConversations);
                }

                EditText etMessage = (EditText) findViewById(R.id.messageField);
                etMessage.setText(null);

                break;
            }
            case R.id.btnAttachImage:
                Intent openGallery = new Intent();
                openGallery.setType("image/*");
                openGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(openGallery, 200);
                break;
        }
    }

    private void download() {
        StorageReference storageReference = storage.getReferenceFromUrl("gs://planngo-d4574.appspot.com/")
                .child(firebaseUser.getUid())
                .child("images/");

        long data = 1024 * 1024;
        StorageReference imgRef = storageReference.child("image%3A19");
        imgRef.getBytes(data).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        });

    }

    private void uploadImage() {
        //StorageReference userImageFolder = storage.getReference().child(firebaseUser.getUid()).child("images/");
        StorageReference userImageFolder = storage.getReference().child("images/");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageInBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        final StorageReference imageRef = userImageFolder.child(imageName);
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("demo", uri.toString());
//                        allConversations = new ArrayList<Chat>();
                        allConversations.add(new Chat(firebaseUser.getUid(), null, userNameFromUid, new Chat.Comments(), uri.toString(), new Date()));
                        allServerConversations.add(new Chat(firebaseUser.getUid(), null, userNameFromUid, new Chat.Comments(), uri.toString(), new Date()));

                        dbRefUserChatRoom.setValue(allConversations);
                        dbRefServTripChatRoom.setValue(allServerConversations);
                    }
                });


            }
        });
    }

    @Override
    public void deleteMessage(int position) {
        Log.d("demo", allConversations.toString());
        if (allConversations.get(position).imageUrl != null) {
            Log.d("demo", allConversations.get(position).imageUrl);
            StorageReference imageToDelete = storage.getReferenceFromUrl(allConversations.get(position).imageUrl);
            imageToDelete.delete();
        }
        allConversations.remove(position);
        dbRefUserChatRoom.setValue(allConversations);
    }

    @Override
    public void commentMessage(int position) {
        positionToComment = position;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Comment");
        alert.setMessage("Your comment");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (input.getText().toString().trim() == null) {
                    Toast.makeText(ChatActivity.this, "No comment", Toast.LENGTH_SHORT).show();
                    return;
                }
                Chat c = allConversations.get(positionToComment);
                c.comments.add(new Chat.Comments(firebaseUser.getUid(), input.getText().toString().trim(), userNameFromUid, new Date()));
                dbRefUserChatRoom.setValue(allConversations);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 200:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        imageInBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        imageName = (new File(String.valueOf(Uri.parse(data.getDataString())))).getName();
                        uploadImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode != 0)
                    Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

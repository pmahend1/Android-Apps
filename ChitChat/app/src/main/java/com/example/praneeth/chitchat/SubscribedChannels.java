package com.example.praneeth.chitchat;
/*a. Assignment #. In Class 09
        b. File Name. ___
        c. Full name of all students in your group. : Prateek , Praneeth*/
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubscribedChannels extends AppCompatActivity  implements View.OnClickListener,AsyncGetSubSciptions.IContextSubScription, AsyncGetChannels.IContextChannel{

    private ArrayList<Channel> allChannelList = new ArrayList<Channel>();
    private ArrayList<Channel> subscriptionList = new ArrayList<Channel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_channels);

        final Button btnAddMore = (Button) findViewById(R.id.button_AddMore);

        new AsyncGetChannels(this).execute();
        new AsyncGetSubSciptions(this).execute();

        btnAddMore.setOnClickListener(this);



        //show available channels which are not addded


        //on click add channel to subscriptions
    }

  

 

    @Override
    public void onSubscriptionCall(ArrayList<Channel> list) {
        subscriptionList= list;

        if(subscriptionList!=null){
            if(subscriptionList.size()!=0){
                    for(int i=0;i<subscriptionList.size();i++){
                        allChannelList.get(i).setSubscribed(true);
                    }
            }
        }
    }

    @Override
    public Context getContextSub() {
        return this;
    }

    @Override
    public void onChannelsCall(ArrayList<Channel> list) {
        if(allChannelList!=null){
            if(allChannelList.size()!=0){

            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {

        final Button btnAddMore = (Button) findViewById(R.id.button_AddMore);
        if(btnAddMore.getText().equals("Add More")){

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_channels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);

            RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(this,allChannelList);
            recyclerView.setAdapter(mAdapter);

            btnAddMore.setText("Done");
        }
    }

    public void updateList(ArrayList<Channel> updateList){
        allChannelList=updateList;
    }
}

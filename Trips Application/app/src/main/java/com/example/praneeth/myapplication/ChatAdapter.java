package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter {
    ChatActivity activity;
    List<Chat> chatList;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

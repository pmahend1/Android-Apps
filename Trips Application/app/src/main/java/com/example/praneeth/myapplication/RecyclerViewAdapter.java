package com.example.praneeth.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;


public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        Context context;
        int resource;
        List<Chat> objects;
        ClickHandler clickHandler;

public RecyclerViewAdapter(Context context, int resource, List<Chat> objects, ClickHandler clickHandler) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.clickHandler = clickHandler;
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView messageText, senderName, timeSent;
    ImageView deleteButton, commentButton, thumbnail;
    ListView commentsList;

    public ViewHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.messageDisp120);
        senderName = (TextView) itemView.findViewById(R.id.senderName);
        timeSent = (TextView) itemView.findViewById(R.id.timeSent);
        deleteButton = (ImageView) itemView.findViewById(R.id.deleteMessage);
        commentButton = (ImageView) itemView.findViewById(R.id.commentBtn);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        commentsList = (ListView) itemView.findViewById(R.id.commentsList);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo" , getAdapterPosition()+"");
//                Log.d("demo", )
                clickHandler.deleteMessage(getAdapterPosition());
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHandler.commentMessage(getAdapterPosition());
            }
        });
    }
}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.resource, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(objects.get(position).message != null)
            holder.messageText.setText(objects.get(position).message);
        holder.senderName.setText(objects.get(position).userName);
        if(!objects.get(position).userId.equals(MainActivity.mCurrentUser.getUid()))
            holder.deleteButton.setVisibility(View.GONE);

        CommentsViewAdaper commentLVAdapter = new CommentsViewAdaper(this.context, R.layout.comments_lv, objects.get(position).comments);
        holder.commentsList.setAdapter(commentLVAdapter);

        if(objects.get(position).addedTime != null)
            holder.timeSent.setText(new PrettyTime().format(objects.get(position).addedTime));

        if(objects.get(position).imageUrl != null)
            Picasso.with(context).load(objects.get(position).imageUrl).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

public interface ClickHandler {
    void deleteMessage(int position);
    void commentMessage(int position);
}
}
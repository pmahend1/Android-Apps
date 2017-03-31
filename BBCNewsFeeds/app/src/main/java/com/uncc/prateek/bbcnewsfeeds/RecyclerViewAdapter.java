/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.bbcnewsfeeds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;

/**
 * Created by Prateek on 11-03-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<BBCNews> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView iv;
        public TextView tv_title;
        public TextView tv_date;



        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.imageView);
            tv_title = (TextView) v.findViewById(R.id.textView_title);
            tv_date = (TextView) v.findViewById(R.id.textView_date);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(Context context , ArrayList<BBCNews> radioList) {
        mDataset = radioList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_news_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to News details
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(mDataset.get(position).getImageLink()!=null ){
            try{
                Picasso.with(mContext).load(mDataset.get(position).getImageLink()).into(holder.iv);
            }catch (Exception e){
                Log.d("ex","Exc "+e.getMessage());
            }

        }
        PrettyTime p = new PrettyTime();
        //System.out.println(p.format(new Date()));
        String dateText = p.format(mDataset.get(position).getNewsDate());

        holder.tv_title.setText(mDataset.get(position).getTitle());
        holder.tv_date.setText(dateText);

    }

	
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

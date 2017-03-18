/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.tedradiohour;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Prateek on 11-03-2017.
 */

public class ViewAsGridAdapter extends RecyclerView.Adapter<ViewAsGridAdapter.ViewHolder> {

    private ArrayList<Radio> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView_episodeImage_grid;
        public TextView textView_episodeTitle_grid;
        public ImageView imageButtonPlayGrid;

        public ViewHolder(View v) {
            super(v);
            imageView_episodeImage_grid = (ImageView) v.findViewById(R.id.imageView_episodeImage_grid);
            textView_episodeTitle_grid = (TextView) v.findViewById(R.id.textView_episodeTitle_grid);
            imageButtonPlayGrid = (ImageView) v.findViewById(R.id.imageButton_play_grid);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewAsGridAdapter(Context context , ArrayList<Radio> radioList) {
        mDataset = radioList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewAsGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
   //     TextView v = (TextView) LayoutInflater.from(parent.getContext())
            //    .inflate(R.layout.radio_item_listview, parent, false);

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.radio_item_gridview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(mDataset.get(position).getImageLink()!=null ){
            try{
                Picasso.with(mContext).load(mDataset.get(position).getImageLink()).resize(200,200).into(holder.imageView_episodeImage_grid);
            }catch (Exception e){
                Log.d("ex","Exc "+e.getMessage());
            }

        }
        holder.textView_episodeTitle_grid.setText(mDataset.get(position).getTitle());
        final int pos = position;
        holder.imageButtonPlayGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(mContext,PlayActivity.class);
                Radio radio = mDataset.get(pos);
                detailIntent.putExtra("PLAY_RADIO",radio);
                mContext.startActivity(detailIntent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

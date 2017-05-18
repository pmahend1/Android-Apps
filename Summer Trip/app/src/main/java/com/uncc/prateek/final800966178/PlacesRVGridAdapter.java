/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.final800966178;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Prateek on 11-03-2017.
 */

public class PlacesRVGridAdapter extends RecyclerView.Adapter<PlacesRVGridAdapter.ViewHolder> {

    private ArrayList<Object> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView textView;
        public TextView textView2;


        public ViewHolder(View v) {
            super(v);
           /* imageView = (ImageView) v.findViewById(R.id.imageView);
            textView = (TextView) v.findViewById(R.id.textView);*/
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlacesRVGridAdapter(Context context , ArrayList<Object> radioList) {
        mDataset = radioList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_deal_grid, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

		
        holder.textView.setText("");
        holder.textView2.setText("");
        
    }

	
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

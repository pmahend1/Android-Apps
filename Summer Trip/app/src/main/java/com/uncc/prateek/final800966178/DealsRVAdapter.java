/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.final800966178;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Prateek on 11-03-2017.
 */

public class DealsRVAdapter extends RecyclerView.Adapter<DealsRVAdapter.ViewHolder> {

        private ArrayList<Deal> mDataset;
    private Context mContext;
    dealConntector dealConntectorObj;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView tvTripTitleRV,tvTripDetailsRV,
        tvCostDtls;
                Button btnViewMap;



        public ViewHolder(View v) {
            super(v);
            tvTripTitleRV = (TextView) v.findViewById(R.id.tvTripTitleRV);
            tvTripDetailsRV = (TextView) v.findViewById(R.id.tvTripDetailsRV);
            tvCostDtls = (TextView) v.findViewById(R.id.tvCostDtls);
            btnViewMap = (Button) v.findViewById(R.id.btnViewMap);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DealsRVAdapter(Context context , dealConntector dealIface, ArrayList<Deal> dealsList) {
        mDataset = dealsList;
        mContext = context;
        dealConntectorObj= dealIface;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_deal_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Deal d = mDataset.get(position);
        holder.tvTripTitleRV.setText(d.Place);
        try {
            holder.tvCostDtls.setText(d.Cost);
            holder.tvTripDetailsRV.setText(d.Duration);
        }catch (Exception e){

        }


        holder.btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealConntectorObj.goToMap(d);
            }
        });


    }

	
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static interface dealConntector{
         public void  goToMap(Deal d);
    }
}

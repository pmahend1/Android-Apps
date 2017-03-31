/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.midterm800966178;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Prateek on 11-03-2017.
 */

public class ViewAsListAdapter extends RecyclerView.Adapter<ViewAsListAdapter.ViewHolder> {

    private static ArrayList<Product> mDataset;
    private static Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView iv;
        public TextView tv_title;
        public TextView tv_price;


        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.imageView_imageatCart);
            tv_title = (TextView) v.findViewById(R.id.textView_cartItemTitle);
            tv_price = (TextView) v.findViewById(R.id.textView_cartItemPrice);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int x = getAdapterPosition();
                    if(mDataset.size()>=x){
                        mDataset.remove(x);
                        ((CartActivity)mContext).updateCartinCart(mDataset);
                    }

                    return true;
                }
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewAsListAdapter(Context context , ArrayList<Product> radioList) {
        mDataset = radioList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
   //     TextView v = (TextView) LayoutInflater.from(parent.getContext())
            //    .inflate(R.layout.radio_item_listview, parent, false);

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_listview, parent, false);
        // set the view's size, margins, paddings and layout parameters
/*        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("ada","On long click cancelling");
                return false;
            }
        });*/
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(mDataset.get(position).getImageLink()!=null ){
            try{
                Picasso.with(mContext).load(mDataset.get(position).getImageLink().trim()).resize(100,100).into(holder.iv);
            }catch (Exception e){
                Log.d("ex","Exc "+e.getMessage());
            }

        }
        holder.tv_title.setText(mDataset.get(position).getTitle());

        holder.tv_price.setText("Price: "+mDataset.get(position).getPrice()+"$");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Prateek on 11-03-2017.
 */

public class ViewAsGridAdapter extends RecyclerView.Adapter<ViewAsGridAdapter.ViewHolder> {

    private ArrayList<Product> mDataset;
    private Context mContext;
    private ArrayList<Product> cart=new ArrayList<Product>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView iv;
        public TextView tv_title;
        public TextView tv_price;
        public TextView tv_discount;
        public Button btnAddToCart;

        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.imageView_grid);
            tv_title = (TextView) v.findViewById(R.id.textView_title);
            tv_price = (TextView) v.findViewById(R.id.textView_price);
            tv_discount = (TextView) v.findViewById(R.id.textView_discount);
            btnAddToCart = (Button) v.findViewById(R.id.button_addToCart);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewAsGridAdapter(Context context , ArrayList<Product> productList) {
        mDataset = productList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
   //     TextView v = (TextView) LayoutInflater.from(parent.getContext())
            //    .inflate(R.layout.radio_item_listview, parent, false);

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gridview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(mDataset.get(position).getImageLink()!=null ){
            Log.d("imagelink",mDataset.get(position).getImageLink());
            try{
                Picasso.with(mContext).load(mDataset.get(position).getImageLink().toString()).fit().into(holder.iv);
            }catch (Exception e){
                Log.d("ex","Exc "+e.getMessage());
            }

        }
        holder.tv_title.setText(mDataset.get(position).getTitle());
        holder.tv_discount.setText("Discount "+mDataset.get(position).getDiscount()+"%");
        holder.tv_price.setText("Price "+mDataset.get(position).getPrice()+"$");
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("adapter","add to cart");
                Product cartItem = mDataset.get(position);
                cart.add(cartItem);
                ((MainActivity)mContext).updateCart(cart);
                holder.btnAddToCart.setClickable(false);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

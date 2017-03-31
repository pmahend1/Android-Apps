package com.uncc.prateek.midterm800966178;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Product> cart= new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button btnCheckout=null;
        Button btnCancel=null;

        ArrayList<Product> cart = (ArrayList<Product>) getIntent().getSerializableExtra("CART");

        if(cart!=null){
            if(cart.size()!=0){

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listView_cart);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);

                RecyclerView.Adapter mAdapter = new ViewAsListAdapter(this,cart);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);



                btnCancel = (Button) findViewById(R.id.button_cancel);
                btnCheckout = (Button) findViewById(R.id.button_Checkout);

                btnCancel.setOnClickListener(this);

                btnCheckout.setOnClickListener(this);

            }
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id==R.id.button_cancel){
            Log.d("d","Cancel current screen");
            finish();
        }else if(id==R.id.button_Checkout){
            Log.d("d","Checkout");
            Toast.makeText(this, "Ordering your items", Toast.LENGTH_LONG).show();

            finish();

        }

    }
    public void updateCartinCart(ArrayList<Product> cart){
        this.cart=cart;
    }
}

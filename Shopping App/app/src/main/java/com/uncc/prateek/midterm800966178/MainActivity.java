package com.uncc.prateek.midterm800966178;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AsyncProductGetter.IProductData {
    private static String URL = "http://52.90.79.130:8080/MidTerm/get/products";
    private static String _LOG_TEXT = "main";
    ArrayList<Product> cart= new ArrayList<Product>();
    ArrayList<Product> history= new ArrayList<Product>();

    public void updateCart(ArrayList<Product> cart){
        this.cart=cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AsyncProductGetter(this).execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_item_cart:{
                if(cart.size()==0){
                    Toast.makeText(this, "No items exist in cart!", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    Intent intent = new Intent(this,CartActivity.class);
                    intent.putExtra("CART",cart);
                    startActivity(intent);
                    break;
                }

            }


            case R.id.menu_item_history:{
                if(history.size()==0){
                    Toast.makeText(this, "No history exist in cart!", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    Intent intent = new Intent(this,CartActivity.class);
                    intent.putExtra("CART",cart);
                    startActivity(intent);
                    break;
                }
            }

            default:
                break;
        }
        return true;
    }

    @Override
    public void loadData(ArrayList<Product> result) {
        Log.d(_LOG_TEXT, "Data :" + result.toString());
        if (result != null) {
            GridLayoutManager lLayout;
            lLayout = new GridLayoutManager(MainActivity.this, 3);

            RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerViewProductList);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(lLayout);

            ViewAsGridAdapter rcAdapter = new ViewAsGridAdapter(MainActivity.this, result);
            rView.setAdapter(rcAdapter);
        }

    }

    @Override
    public Context getContext() {
        return null;
    }
}

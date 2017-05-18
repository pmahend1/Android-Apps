package com.uncc.prateek.final800966178;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnGetDeal,btnCreateTrip,btnTripList,btnWishList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnGetDeal = (Button) findViewById(R.id.btnGetDeal);
        btnCreateTrip = (Button) findViewById(R.id.btnCreateTrip);
        btnTripList = (Button) findViewById(R.id.btnTripList);
        btnWishList = (Button) findViewById(R.id.btnWishList);

        btnGetDeal.setOnClickListener(this);
        btnCreateTrip.setOnClickListener(this);
        btnTripList.setOnClickListener(this);
        btnWishList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id =  v.getId();

        switch (id){
            case R.id.btnGetDeal:{
                Intent i1 = new Intent(this,DealsActivity.class);
                startActivity(i1);

                break;
            }
            case R.id.btnCreateTrip:{

                Intent i2 = new Intent(this,CreateTripActivity.class);
                startActivity(i2);
                break;
            }
            case R.id.btnTripList:{

                Intent i3 = new Intent(this,TripListActivity.class);
                startActivity(i3);
                break;
            }
            case R.id.btnWishList:{
                Intent i4 = new Intent(this,WishListActivity.class);
                startActivity(i4);

                break;
            }

        }
    }
}

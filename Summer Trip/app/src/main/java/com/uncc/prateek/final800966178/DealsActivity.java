package com.uncc.prateek.final800966178;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DealsActivity extends AppCompatActivity implements DealsRVAdapter.dealConntector , DealsRVGridAdapter.dealConntector1,CompoundButton.OnCheckedChangeListener {
    ArrayList<Deal> deals = new ArrayList<Deal>();
    private FirebaseAuth firebaseAuthentication;
    private DatabaseReference databaseReference;
    boolean isList=true;

    Switch viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        viewType = (Switch) findViewById(R.id.switch1);
        viewType.setOnCheckedChangeListener(this);

        DatabaseReference dbx = databaseReference.child("Deals");
        dbx.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                          deals = new ArrayList<Deal>();
                                          for (DataSnapshot child : dataSnapshot.getChildren()) {

                                              GenericTypeIndicator<ArrayList<Deal>> t = new GenericTypeIndicator<ArrayList<Deal>>() {
                                              };
                                              Deal tempDeal = child.getValue(Deal.class);
                                              if(tempDeal!=null){
                                                  deals.add(tempDeal);
                                                  Log.d("d","deal is "+tempDeal.toString());
                                              }

                                          }
                                          //temp.add(child.getValue(Trip.class));
                                          if(isList){
                                              RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvDeals);

                                              RecyclerView.LayoutManager mLayoutManager = new
                                                      LinearLayoutManager(DealsActivity.this);
                                              recyclerView.setLayoutManager(mLayoutManager);

                                              RecyclerView.Adapter mAdapter = new DealsRVAdapter(DealsActivity.this,DealsActivity.this,deals);
                                              recyclerView.setAdapter(mAdapter);
                                          }else{
                                              GridLayoutManager lLayout;
                                              lLayout = new GridLayoutManager(DealsActivity.this, 2);

                                              RecyclerView rView = (RecyclerView)findViewById(R.id.rvDeals);
                                              rView.setHasFixedSize(true);
                                              rView.setLayoutManager(lLayout);

                                              RecyclerView.Adapter mAdapter = new DealsRVGridAdapter(DealsActivity.this,DealsActivity.this, deals);
                                              rView.setAdapter(mAdapter);
                                          }

                                      }

                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {

                                      }


                                  }


        );




    }

    @Override
    public void goToMap(Deal d) {
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra("DEAL",d);
        startActivity(i);
    }

    @Override
    public void goToMap1(Deal d) {
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra("DEAL",d);
        startActivity(i);
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isChecked) {
            //do stuff when Switch is ON
            isList= false;
            Toast.makeText(this, "List mode is ON", Toast.LENGTH_SHORT).show();
        } else {
            //do stuff when Switch if OFF
            isList= true;
            Toast.makeText(this, "List mode is ON", Toast.LENGTH_SHORT).show();
        }
    }
}

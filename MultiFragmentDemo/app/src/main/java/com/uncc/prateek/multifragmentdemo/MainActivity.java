package com.uncc.prateek.multifragmentdemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.container,new FirstFragment(),"firstFragment").commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void goToNextFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new SecondFragment(),"secondFragment").addToBackStack(null).commit();
    }
}

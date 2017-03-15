package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : AppDetailHolder.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


public class AppDetailHolder extends RecyclerView.ViewHolder {
    TextView tv1, tv2;
    ImageView imageIcon;
    ToggleButton star;

    public AppDetailHolder(View itemView) {
        super(itemView);
        tv1 = (TextView) itemView.findViewById(R.id.appTitle);
        tv2 = (TextView) itemView.findViewById(R.id.appPrice);
        imageIcon = (ImageView) itemView.findViewById(R.id.imageView);
        star = (ToggleButton) itemView.findViewById(R.id.star);
    }

}



package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class TripHolder extends RecyclerView.ViewHolder {
    TextView tripNameH, locationH;
    CircularImageView trip_picH;
    LinearLayout layout;
    Button btnJoin, btnChat, btnView;

    public TripHolder(View itemView) {
        super(itemView);
        trip_picH = (CircularImageView) itemView.findViewById(R.id.trip_picH);
        tripNameH = (TextView) itemView.findViewById(R.id.tripNameH);
        locationH = (TextView) itemView.findViewById(R.id.locationNameH);
        layout = (LinearLayout) itemView.findViewById(R.id.tripLayout);
        btnJoin = (Button) itemView.findViewById(R.id.btnJoin);
        btnChat = (Button) itemView.findViewById(R.id.btnChat);
        btnView = (Button) itemView.findViewById(R.id.btnSee);

    }
}

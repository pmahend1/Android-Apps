package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class PendingHolder extends RecyclerView.ViewHolder {
    TextView UserNameP,statusP;
    ImageView acceptP,rejectP;
    CircularImageView userPicP;

    public PendingHolder(View itemView) {
        super(itemView);
        userPicP= (CircularImageView) itemView.findViewById(R.id.user_picP);
        UserNameP= (TextView) itemView.findViewById(R.id.userNameP);
        statusP= (TextView) itemView.findViewById(R.id.statusP);
        acceptP = (ImageView) itemView.findViewById(R.id.acceptP);
        rejectP = (ImageView) itemView.findViewById(R.id.rejectP);
    }
}
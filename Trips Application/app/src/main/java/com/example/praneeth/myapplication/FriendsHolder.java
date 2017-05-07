package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class FriendsHolder extends RecyclerView.ViewHolder {
        TextView UserName,status;
        CircularImageView userPic;

public FriendsHolder(View itemView) {
        super(itemView);
        userPic= (CircularImageView) itemView.findViewById(R.id.user_pic);
        UserName= (TextView) itemView.findViewById(R.id.userName);
        status= (TextView) itemView.findViewById(R.id.status);
        }
        }

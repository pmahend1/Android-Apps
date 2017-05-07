package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class RecommendedHolder extends RecyclerView.ViewHolder {
    TextView UserName,status,addFriendText;
    ImageView addFriend;
    CircularImageView userPic;

    public RecommendedHolder(View itemView) {
        super(itemView);
        userPic= (CircularImageView) itemView.findViewById(R.id.user_picR);
        UserName= (TextView) itemView.findViewById(R.id.userNameR);
        addFriendText= (TextView) itemView.findViewById(R.id.addFriend);
        status= (TextView) itemView.findViewById(R.id.statusR);
        addFriend = (ImageView) itemView.findViewById(R.id.imageView_AddFriend);
    }
}

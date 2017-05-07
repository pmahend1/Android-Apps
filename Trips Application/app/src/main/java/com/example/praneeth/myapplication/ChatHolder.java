package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class ChatHolder extends RecyclerView.ViewHolder {
    TextView usernameChat,messageChat;
    ImageView imageViewChat,delete;

    public ChatHolder(View itemView) {
        super(itemView);
        imageViewChat= (ImageView) itemView.findViewById(R.id.imageChat);
        delete= (ImageView) itemView.findViewById(R.id.deleteChat);
        usernameChat= (TextView) itemView.findViewById(R.id.usernamechat);
        messageChat= (TextView) itemView.findViewById(R.id.messagechat);
    }
}

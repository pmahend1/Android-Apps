package com.example.praneeth.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class AddFriendsToChatHolder extends RecyclerView.ViewHolder{
        TextView usernamefriend;
        CheckBox check;

public AddFriendsToChatHolder(View itemView){
        super(itemView);

    usernamefriend=(TextView)itemView.findViewById(R.id.addFriendToChatName);
    check=(CheckBox)itemView.findViewById(R.id.checkBox_Select);
        }
}

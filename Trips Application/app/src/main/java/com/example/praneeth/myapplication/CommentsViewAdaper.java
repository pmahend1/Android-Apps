package com.example.praneeth.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;



public class CommentsViewAdaper extends ArrayAdapter<Chat.Comments> {

    int resource;
    Context context;
    List<Chat.Comments> objects;


    public CommentsViewAdaper(Context context, int resource, List<Chat.Comments> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resource, parent, false);
        }

        Chat.Comments comments = objects.get(position);

/*        ((TextView) convertView.findViewById(R.id.)).setText(comments.comment);
        ((TextView) convertView.findViewById(R.id.)).setText(comments.userName);
        ((TextView) convertView.findViewById(R.id.)).setText(new PrettyTime().format(comments.addedTime));*/

        return convertView;
    }
}
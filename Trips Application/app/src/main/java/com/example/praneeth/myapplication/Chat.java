package com.example.praneeth.myapplication;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class Chat {
    String userId, message, userName, imageUrl;
    Date addedTime;
    ArrayList<Comments> comments = new ArrayList<>();

    public Chat() {
    }

    public Chat(String userId, String message, String userName, Comments comment, String imageUrl, Date addedTime) {
        this.userId = userId;
        this.message = message;
        this.userName = userName;
        this.comments.add(comment);
        this.imageUrl = imageUrl;
        this.addedTime = addedTime;
    }

    public static class Comments {
        String userId, comment, userName;
        Date addedTime;

        public Comments() {
        }

        public Comments(String userId, String comment, String userName, Date addedTime) {
            this.userId = userId;
            this.comment = comment;
            this.userName = userName;
            this.addedTime = addedTime;
        }
    }
}

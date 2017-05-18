package com.example.prateek.myapplication;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by prateek on 4/24/17.
 */

public class NotesList extends RealmObject {
    @PrimaryKey
    private long id;
    private RealmList<Note> items;

    public RealmList<Note> getItems() {
        return items;
    }

    public void setItems(RealmList<Note> items) {
        this.items = items;
    }

    public long getId() {
        return id;
    }
}
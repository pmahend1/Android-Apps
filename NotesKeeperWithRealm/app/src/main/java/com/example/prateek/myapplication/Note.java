package com.example.prateek.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Note  extends RealmObject {
    @PrimaryKey
    String _id;

    @Required
    String note;
    String priority,
    time,status;

    boolean isCompleted=false;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Note(String id, String note, String priority, String time, String status) {
        this._id = id;
        this.note = note;
        this.priority = priority;
        this.time = time;
        this.status = status;

    }

    public Note() {
    }

    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", note='" + note + '\'' +
                ", priority='" + priority + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

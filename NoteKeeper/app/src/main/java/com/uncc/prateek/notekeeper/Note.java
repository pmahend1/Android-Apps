package com.uncc.prateek.notekeeper;

import java.util.Date;

/**
 * Created by Praneeth on 2/27/2017.
 */

public class Note {

    private long _id;
    private String note,priority,status,time;

    public Note() {
        this._id = _id;
        this.note = note;
        this.priority = priority;
        this.status = status;
        this.time = time;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", note='" + note + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
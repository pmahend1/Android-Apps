package com.example.databasenotesdemo;

public class Note {
	private long _id;
	private String subject;
	private String text;
	
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Note [_id=" + _id + ", subject=" + subject + ", text=" + text + "]";
	}
}

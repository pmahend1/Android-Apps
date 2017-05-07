package com.example.praneeth.myapplication;

import java.util.Date;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class Message {
    String sender,messageText,receiverKey,msgKey,msgType,imageUri,senderKey;
    Date date;

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", messageText='" + messageText + '\'' +
                ", receiverKey='" + receiverKey + '\'' +
                ", msgKey='" + msgKey + '\'' +
                ", msgType='" + msgType + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", senderKey='" + senderKey + '\'' +
                ", date=" + date +
                '}';
    }

    public Message(String sender, String messageText, String receiverKey, String msgKey, String msgType, String msgSub, String senderKey, Date date) {
        this.sender = sender;
        this.messageText = messageText;
        this.receiverKey = receiverKey;
        this.msgKey = msgKey;
        this.msgType = msgType;
        this.senderKey = senderKey;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getReceiverKey() {
        return receiverKey;
    }

    public void setReceiverKey(String receiverKey) {
        this.receiverKey = receiverKey;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

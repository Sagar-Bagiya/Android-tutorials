package com.example.mychatapp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessage {
    private String messageId;
    private String text;
    private String messageTime;
    private String senderId;
    private String receiverId;
    private long timestamp;


    // Empty constructor (required for Firebase)
    public ChatMessage() {
    }

    public ChatMessage(String messageId, String text, String messageTime, String senderId, String receiverId, long timestamp) {
        this.messageId = messageId;
        this.text = text;
        this.messageTime = messageTime;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }

    public ChatMessage(String messageId, String text, String senderId, String receiverId, long timestamp) {
        this.messageId = messageId;
        this.text = text;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;

    }

    // Getters and setters
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageTime() {
        return formatTimestamp(timestamp);
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }


    public String formatTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return sdf.format(date);
    }

    public long reverseFormatTimestamp(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            Date date = sdf.parse(timeString);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}


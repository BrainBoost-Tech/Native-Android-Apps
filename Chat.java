package com.zat.lexikey.models.chatModels;

public class Chat {

    private String sender_id;
    private String receiver_id;
    private String message;
    private String message_id;
    private boolean isseen;
    private String time;
    private String media_url;
    private int type;
    private String duration;
    private String corrected;
    private String correctSelected;
    private String translatedBySender;
    private String translatedByReceiver;
    private String translatedWordByReceiver;
    private String translatedWordBySender;

    public Chat(String sender_id, String receiver, String message,String message_id, boolean isseen, String time, String media_url, int type, String duration, String corrected) {
        this.sender_id = sender_id;
        this.receiver_id = receiver;
        this.message_id = message_id;
        this.message = message;
        this.isseen = isseen;
        this.time = time;
        this.media_url = media_url;
        this.type = type;
        this.duration = duration;
        this.corrected = corrected;
    }

    public Chat() {
    }

    public String getCorrected() {
        return corrected;
    }

    public void setCorrected(String corrected) {
        this.corrected = corrected;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getTranslatedBySender() {
        return translatedBySender;
    }

    public void setTranslatedBySender(String translatedBySender) {
        this.translatedBySender = translatedBySender;
    }

    public String getTranslatedByReceiver() {
        return translatedByReceiver;
    }

    public void setTranslatedByReceiver(String translatedByReceiver) {
        this.translatedByReceiver = translatedByReceiver;
    }

    public String getTranslatedWordByReceiver() {
        return translatedWordByReceiver;
    }

    public void setTranslatedWordByReceiver(String translatedWordByReceiver) {
        this.translatedWordByReceiver = translatedWordByReceiver;
    }

    public String getTranslatedWordBySender() {
        return translatedWordBySender;
    }

    public void setTranslatedWordBySender(String translatedWordBySender) {
        this.translatedWordBySender = translatedWordBySender;
    }

    public String getCorrectSelected() {
        return correctSelected;
    }

    public void setCorrectSelected(String correctSelected) {
        this.correctSelected = correctSelected;
    }
}
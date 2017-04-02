package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;

import org.parceler.Parcel;

@Parcel(analyze ={Message.class})
public class Message {

    @SerializedName("id")
    @Expose
    long id;

    @Column
    @SerializedName("text")
    @Expose
    String text;

    @Column
    @SerializedName("created_at")
    @Expose
    String createdAt;

    @Column
    @SerializedName("sender")
    @Expose
    User sender;

    @Column
    @SerializedName("recipient")
    @Expose
    User recipient;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}

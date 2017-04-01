package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;

import org.parceler.Parcel;

import java.util.List;

@Parcel(analyze ={Follow.class})
public class Follow {

    @Column
    @SerializedName("users")
    @Expose
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

@Table(database = TwitterDatabase.class)
@Parcel(analyze ={User.class})
public class User extends BaseModel {

    @PrimaryKey
    @Column
    @SerializedName("id")
    @Expose
    long id;

    @Column
    @SerializedName("name")
    @Expose
    String name;

    @Column
    @SerializedName("profile_image_url")
    @Expose
    String profileImageUrl;

    @Column
    @SerializedName("screen_name")
    @Expose
    String screenName;

    @Column
    @SerializedName("verified")
    @Expose
    boolean verified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

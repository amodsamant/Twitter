package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

@Table(database = TwitterDatabase.class)
@Parcel(analyze ={Tweet.class})
public class Tweet extends BaseModel {

    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    long id;

    @Column
    @SerializedName("text")
    @Expose
    String body;

    @Column
    @SerializedName("created_at")
    @Expose
    String createdAt;

    @SerializedName("entities")
    @Expose
    Entities entities;

    @SerializedName("extended_entities")
    @Expose
    ExtendedEntities extendedEntities;

    @ForeignKey(saveForeignKeyModel = true)
    @Column
    @SerializedName("user")
    @Expose
    User user;

    @Column
    @SerializedName("favorited")
    @Expose
    boolean favorited;

    @Column
    @SerializedName("retweeted")
    @Expose
    boolean retweeted;

    @Column
    @SerializedName("retweet_count")
    @Expose
    int retweetCount;

    @Column
    @SerializedName("favorite_count")
    @Expose
    int favouritesCount;

    @Column
    String imageUrl;

    public Tweet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Entities getEntities() {
        return entities;
    }

    public ExtendedEntities getExtendedEntities() {
        return extendedEntities;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Sizes {

    @SerializedName("medium")
    @Expose
    Size medium;

    @SerializedName("thumb")
    @Expose
    Size thumb;

    @SerializedName("small")
    @Expose
    Size small;

    @SerializedName("large")
    @Expose
    Size large;

    public Size getMedium() {
        return medium;
    }

    public void setMedium(Size medium) {
        this.medium = medium;
    }

    public Size getThumb() {
        return thumb;
    }

    public void setThumb(Size thumb) {
        this.thumb = thumb;
    }

    public Size getSmall() {
        return small;
    }

    public void setSmall(Size small) {
        this.small = small;
    }

    public Size getLarge() {
        return large;
    }

    public void setLarge(Size large) {
        this.large = large;
    }
}

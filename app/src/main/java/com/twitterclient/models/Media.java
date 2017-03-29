package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Media {

    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("id_str")
    @Expose
    String idStr;

    @SerializedName("media_url")
    @Expose
    String mediaUrl;

    @SerializedName("media_url_https")
    @Expose
    String mediaUrlHttps;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("display_url")
    @Expose
    String displayUrl;

    @SerializedName("expanded_url")
    @Expose
    String expandedUrl;

    @SerializedName("sizes")
    @Expose
    Sizes sizes;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("video_info")
    @Expose
    VideoInfo videoInfo;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaUrlHttps() {
        return mediaUrlHttps;
    }

    public void setMediaUrlHttps(String mediaUrlHttps) {
        this.mediaUrlHttps = mediaUrlHttps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

}

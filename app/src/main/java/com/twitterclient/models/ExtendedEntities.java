package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ExtendedEntities {

    @SerializedName("media")
    @Expose
    List<Media> media;

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

//    @SerializedName("id")
//    @Expose
//    long id;
//
//    @SerializedName("id_str")
//    @Expose
//    String idStr;
//
//    @SerializedName("display_url")
//    @Expose
//    String displayUrl;
//
//    @SerializedName("media_url")
//    @Expose
//    String mediaUrl;
//
//    @SerializedName("media_url_https")
//    @Expose
//    String mediaUrlHttps;
//
//    @SerializedName("sizes")
//    @Expose
//    Sizes sizes;
//
//    @SerializedName("type")
//    @Expose
//    String type;
//
//    @SerializedName("url")
//    @Expose
//    String url;
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getIdStr() {
//        return idStr;
//    }
//
//    public void setIdStr(String idStr) {
//        this.idStr = idStr;
//    }
//
//    public String getDisplayUrl() {
//        return displayUrl;
//    }
//
//    public void setDisplayUrl(String displayUrl) {
//        this.displayUrl = displayUrl;
//    }
//
//    public String getMediaUrl() {
//        return mediaUrl;
//    }
//
//    public void setMediaUrl(String mediaUrl) {
//        this.mediaUrl = mediaUrl;
//    }
//
//    public String getMediaUrlHttps() {
//        return mediaUrlHttps;
//    }
//
//    public void setMediaUrlHttps(String mediaUrlHttps) {
//        this.mediaUrlHttps = mediaUrlHttps;
//    }
//
//    public Sizes getSizes() {
//        return sizes;
//    }
//
//    public void setSizes(Sizes sizes) {
//        this.sizes = sizes;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
}

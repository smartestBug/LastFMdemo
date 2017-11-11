package dev.msemyak.lastfmdemo.mvp.model.local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("#text")
    @Expose
    private String imageURL;
    @SerializedName("size")
    @Expose
    private String imageSize;

    public String getImageURL() {
        return imageURL;
    }
}

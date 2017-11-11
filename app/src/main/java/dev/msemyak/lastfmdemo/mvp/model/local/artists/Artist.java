package dev.msemyak.lastfmdemo.mvp.model.local.artists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.msemyak.lastfmdemo.mvp.model.local.Image;

public class Artist {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("streamable")
    @Expose
    private String streamable;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;

    public String getName() {
        return name;
    }

    public String getListeners() {
        return listeners;
    }

    public List<Image> getImage() {
        return image;
    }

}

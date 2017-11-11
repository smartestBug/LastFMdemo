package dev.msemyak.lastfmdemo.mvp.model.local.albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.msemyak.lastfmdemo.mvp.model.local.Image;

public class Album {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("playcount")
    @Expose
    private Integer playcount;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("artist")
    @Expose
    private ArtistShort artist;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;

    public String getName() {
        return name;
    }

    public Integer getPlaycount() {
        return playcount;
    }

    public List<Image> getImage() {
        return image;
    }

}

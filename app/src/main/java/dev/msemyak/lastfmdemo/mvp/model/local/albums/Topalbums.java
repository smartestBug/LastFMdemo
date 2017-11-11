package dev.msemyak.lastfmdemo.mvp.model.local.albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Topalbums {

    @SerializedName("album")
    @Expose
    private List<Album> album = null;
    @SerializedName("@attr")
    @Expose
    private AlbumAttr attr;

    public List<Album> getAlbum() {
        return album;
    }

}

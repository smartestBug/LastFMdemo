package dev.msemyak.lastfmdemo.mvp.model.local.artists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Topartists {

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = null;
    @SerializedName("@attr")
    @Expose
    private ArtistsAttr attr;

    public List<Artist> getArtist() {
        return artist;
    }

}

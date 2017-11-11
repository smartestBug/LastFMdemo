package dev.msemyak.lastfmdemo.mvp.model.local.albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopAlbumsResponse {

    @SerializedName("topalbums")
    @Expose
    private Topalbums topalbums;

    public Topalbums getTopalbums() {
        return topalbums;
    }

}

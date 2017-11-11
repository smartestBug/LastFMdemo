package dev.msemyak.lastfmdemo.mvp.model.local.artists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopArtistsResponse {

    @SerializedName("topartists")
    @Expose
    private Topartists topartists;

    public Topartists getTopartists() {
        return topartists;
    }

}

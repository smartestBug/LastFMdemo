package dev.msemyak.lastfmdemo.mvp.model.local.albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ArtistShort {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;

}


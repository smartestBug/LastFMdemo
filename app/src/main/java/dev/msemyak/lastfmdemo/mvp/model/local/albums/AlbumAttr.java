package dev.msemyak.lastfmdemo.mvp.model.local.albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class AlbumAttr {

    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("perPage")
    @Expose
    private String perPage;
    @SerializedName("totalPages")
    @Expose
    private String totalPages;
    @SerializedName("total")
    @Expose
    private String total;

}

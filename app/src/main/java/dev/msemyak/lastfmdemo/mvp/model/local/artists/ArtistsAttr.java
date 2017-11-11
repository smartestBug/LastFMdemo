package dev.msemyak.lastfmdemo.mvp.model.local.artists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ArtistsAttr {

    @SerializedName("country")
    @Expose
    private String country;
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

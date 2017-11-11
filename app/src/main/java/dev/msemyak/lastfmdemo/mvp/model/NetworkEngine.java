package dev.msemyak.lastfmdemo.mvp.model;

import dev.msemyak.lastfmdemo.mvp.model.local.albums.TopAlbumsResponse;
import dev.msemyak.lastfmdemo.mvp.model.local.artists.TopArtistsResponse;
import dev.msemyak.lastfmdemo.mvp.model.remote.RetrofitClient;
import io.reactivex.Single;

public class NetworkEngine {

    public static Single<TopArtistsResponse> getTopArtists(String country, int page) {
        return RetrofitClient.getLastFMAPI().getTopArtistsByCountry(country, page);
    }

    public static Single<TopAlbumsResponse> getTopAlbums(String artistName) {
        return RetrofitClient.getLastFMAPI().getAlbumsByArtist(artistName);
    }

}

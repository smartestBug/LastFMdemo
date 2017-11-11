package dev.msemyak.lastfmdemo.mvp.model.remote;

import dev.msemyak.lastfmdemo.mvp.model.local.albums.TopAlbumsResponse;
import dev.msemyak.lastfmdemo.mvp.model.local.artists.TopArtistsResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMAPI {

    @GET("?method=geo.gettopartists&format=json&api_key=e81f61890b7ff8633ca024d0faa449e7")
    Single<TopArtistsResponse> getTopArtistsByCountry(@Query("country") String country, @Query("page") int page);

    @GET("?method=artist.gettopalbums&format=json&api_key=e81f61890b7ff8633ca024d0faa449e7")
    Single<TopAlbumsResponse> getAlbumsByArtist(@Query("artist") String artistName);

}

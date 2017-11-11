package dev.msemyak.lastfmdemo.base;

import android.view.View;

import java.util.List;

import dev.msemyak.lastfmdemo.mvp.model.local.albums.Album;
import dev.msemyak.lastfmdemo.mvp.model.local.artists.Artist;

public interface BaseView {

    void showMessage(int stringId);

    void notifyAdapterDataChange();

    void showErrorScreen();

    void showWaitingScreen();

    interface MainView extends BaseView {
        void showArtists(List<Artist> artistsList);

        void showArtistsScreen();

        void notifyLoadingDone();

        void notifyAdapterItemInserted(int position);

        void notifyAdapterItemRemoved(int position);

        void scrollRecyclerViewToPosition(int position);

    }

    interface ArtistAlbumsView extends BaseView {

        void showArtistAlbumsScreen();

        void showAlbums(List<Album> albumsList);


    }

    interface RVItemClickListener extends BaseView {
        void onRVItemClick(View v, String artistName, String ArtistImageUrl);
    }
}

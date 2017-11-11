package dev.msemyak.lastfmdemo.base;

public interface BasePresenter {

    void unsubscribeObservers();

    interface MainActivityPresenter extends BasePresenter{

        void getArtistsAndDisplay(String country);
        void loadMoreArtists();
    }

    interface ArtistAlbumsPresenter extends BasePresenter{
        void loadArtistAlbums(String username);
    }

}
package dev.msemyak.lastfmdemo.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import dev.msemyak.lastfmdemo.base.BasePresenter;
import dev.msemyak.lastfmdemo.base.BasePresenterImpl;
import dev.msemyak.lastfmdemo.base.BaseView;
import dev.msemyak.lastfmdemo.mvp.model.NetworkEngine;
import dev.msemyak.lastfmdemo.mvp.model.local.albums.Album;
import dev.msemyak.lastfmdemo.utils.RxUtil;
import io.reactivex.disposables.CompositeDisposable;

public class ArtistAlbumsPresenter extends BasePresenterImpl<BaseView.ArtistAlbumsView> implements BasePresenter.ArtistAlbumsPresenter {

    private CompositeDisposable subscriptions = new CompositeDisposable();

    private List<Album> albumsList;

    public ArtistAlbumsPresenter(BaseView.ArtistAlbumsView view) {
        super(view);

        albumsList = new ArrayList<>();
    }

    @Override
    public void loadArtistAlbums(String artistName) {

        myView.showWaitingScreen();

        albumsList.clear();

        subscriptions.add(
                NetworkEngine.getTopAlbums(artistName)
                        .compose(RxUtil.applySingleSchedulers())
                        .subscribe(
                                response -> {

                                    albumsList = response.getTopalbums().getAlbum();

                                    myView.showArtistAlbumsScreen();
                                    myView.showAlbums(albumsList);

                                },
                                error -> {
                                    myView.showErrorScreen();
                                    error.printStackTrace();
                                }
                        ));

    }

    @Override
    public void unsubscribeObservers() {
        RxUtil.unsubscribe(subscriptions);
    }
}

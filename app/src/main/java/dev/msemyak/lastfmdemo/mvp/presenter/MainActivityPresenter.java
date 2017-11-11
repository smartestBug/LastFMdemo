package dev.msemyak.lastfmdemo.mvp.presenter;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import dev.msemyak.lastfmdemo.R;
import dev.msemyak.lastfmdemo.base.BasePresenter;
import dev.msemyak.lastfmdemo.base.BasePresenterImpl;
import dev.msemyak.lastfmdemo.base.BaseView;
import dev.msemyak.lastfmdemo.mvp.model.NetworkEngine;
import dev.msemyak.lastfmdemo.mvp.model.local.artists.Artist;
import dev.msemyak.lastfmdemo.utils.RxUtil;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivityPresenter extends BasePresenterImpl<BaseView.MainView> implements BasePresenter.MainActivityPresenter {

    private CompositeDisposable subscriptions = new CompositeDisposable();

    private List<Artist> artistsList;
    private int page;
    private String country;

    public MainActivityPresenter(BaseView.MainView view) {
        super(view);

        artistsList = new ArrayList<>();
        page = 1;
    }

    @Override
    public void getArtistsAndDisplay(String country) {

        myView.showWaitingScreen();

        this.country = country;
        artistsList.clear();
        page = 1;

        subscriptions.add(
                NetworkEngine.getTopArtists(country, page)
                        .compose(RxUtil.applySingleSchedulers())
                        .subscribe(
                                response -> {

                                    artistsList = response.getTopartists().getArtist();

                                    myView.showArtistsScreen();
                                    myView.showArtists(artistsList);

                                },
                                error -> {
                                    myView.showErrorScreen();
                                    error.printStackTrace();
                                }
                        ));

    }

    @Override
    public void loadMoreArtists() {

        int position = artistsList.size();

        Handler handler = new Handler();
        handler.post(() -> {
            artistsList.add(position, null);
            myView.notifyAdapterItemInserted(position);
            myView.scrollRecyclerViewToPosition(position);
        });

        subscriptions.add(
                NetworkEngine.getTopArtists(country, page + 1)
                        .compose(RxUtil.applySingleSchedulers())
                        .subscribe(
                                response -> {
                                    page++;

                                    // remove loading progress animation
                                    artistsList.remove(artistsList.size() - 1);
                                    myView.notifyAdapterItemRemoved(artistsList.size());
                                    myView.notifyLoadingDone();

                                    myView.showArtistsScreen();
                                    artistsList.addAll(response.getTopartists().getArtist());
                                    myView.notifyAdapterDataChange();
                                },
                                error -> {
                                    // remove loading progress animation
                                    artistsList.remove(artistsList.size() - 1);
                                    myView.notifyAdapterItemRemoved(artistsList.size());

                                    myView.showMessage(R.string.error_loading_more_artists);
                                    error.printStackTrace();
                                }
                        ));
    }

    @Override
    public void unsubscribeObservers() {
        RxUtil.unsubscribe(subscriptions);
    }
}

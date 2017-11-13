package dev.msemyak.lastfmdemo.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dev.msemyak.lastfmdemo.AppBoss;
import dev.msemyak.lastfmdemo.R;
import dev.msemyak.lastfmdemo.base.BaseActivity;
import dev.msemyak.lastfmdemo.base.BasePresenter;
import dev.msemyak.lastfmdemo.base.BaseView;
import dev.msemyak.lastfmdemo.mvp.model.local.artists.Artist;
import dev.msemyak.lastfmdemo.mvp.presenter.MainActivityPresenter;
import dev.msemyak.lastfmdemo.mvp.view.adapters.CountryAdapter;
import dev.msemyak.lastfmdemo.mvp.view.adapters.CountryAdapter.CountryInfo;
import dev.msemyak.lastfmdemo.mvp.view.adapters.RVAdapterArtists;
import dev.msemyak.lastfmdemo.utils.NetworkChangeEvent;
import dev.msemyak.lastfmdemo.utils.RxBus;

public class MainActivity extends BaseActivity<BasePresenter.MainActivityPresenter> implements BaseView.MainView, BaseView.RVItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_main)
    RecyclerView recyclerViewMain;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.spinner_country)
    Spinner countrySpinner;
    @BindView(R.id.iv_no_network)
    ImageView ivNetwork;

    private RVAdapterArtists listAdapter = null;
    private boolean loadingUsers;
    private ArrayList<CountryInfo> countries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.app_title_for_toolbar);

        showScreen(R.id.screen_loading_artists);

        setupSpinner();
        setupNetworkStateListener();
        updateNetworkStatus();

        myPresenter.getArtistsAndDisplay(countries.get(0).getQueryName());

    }

    private void setupNetworkStateListener() {
        RxBus.getInstance().listen(NetworkChangeEvent.class).subscribe((next)-> {
            updateNetworkStatus();
        });
    }

    private void setupSpinner() {
        countries = new ArrayList<>();
        countries.add(new CountryInfo(getString(R.string.country_ukraine), "ukraine", R.drawable.ukr_flag_round));
        countries.add(new CountryInfo(getString(R.string.country_thailand), "thailand", R.drawable.tai_flag_round));
        countries.add(new CountryInfo(getString(R.string.country_mexico), "mexico", R.drawable.mex_flag_round));

        CountryAdapter myAdapter = new CountryAdapter(this, android.R.layout.simple_spinner_item, countries);
        countrySpinner.setAdapter(myAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myPresenter.getArtistsAndDisplay(countries.get(position).getQueryName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void showArtists(List<Artist> artistsList) {

        if (listAdapter == null) {
            recyclerViewMain.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewMain.setLayoutManager(layoutManager);
            listAdapter = new RVAdapterArtists(artistsList, this, this);
            recyclerViewMain.setAdapter(listAdapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewMain.getContext(), layoutManager.getOrientation());
            recyclerViewMain.addItemDecoration(dividerItemDecoration);

            recyclerViewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (!recyclerView.canScrollVertically(1) && !loadingUsers) {
                        loadingUsers = true;
                        myPresenter.loadMoreArtists();
                    }
                    //avoid calling loadMoreUsers if scroller is at the bottom and error has happened
                    else if (recyclerView.canScrollVertically(1) && loadingUsers) {
                        loadingUsers = false;
                    }
                }
            });

        } else {
            listAdapter.setNewData(artistsList);
            notifyAdapterDataChange();
            recyclerViewMain.scrollToPosition(0);
        }
    }

    private void updateNetworkStatus() {
        if (AppBoss.isNetworkAvailable()) ivNetwork.setVisibility(View.GONE);
        else ivNetwork.setVisibility(View.VISIBLE);
    }

    private void showScreen(int screen_id) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(viewFlipper.findViewById(screen_id)));
    }

    @Override
    public void showWaitingScreen() {
        showScreen(R.id.screen_loading_artists);
    }

    @Override
    public void showArtistsScreen() {
        showScreen(R.id.screen_artists_list);
    }

    @Override
    public void showErrorScreen() {
        showScreen(R.id.screen_loading_error);
    }

    @Override
    public void notifyLoadingDone() {
        loadingUsers = false;
    }

    @Override
    public void notifyAdapterDataChange() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyAdapterItemInserted(int position) {
        listAdapter.notifyItemInserted(position);
    }

    @Override
    public void notifyAdapterItemRemoved(int position) {
        listAdapter.notifyItemRemoved(position);
    }

    @Override
    public void scrollRecyclerViewToPosition(int position) {
        recyclerViewMain.scrollToPosition(position);
    }

    @Override
    public void onRVItemClick(View v, String artistName, String artistImageUrl) {
        Intent userDetailsIntent = new Intent(getApplicationContext(), ArtistAlbumsActivity.class);
        userDetailsIntent.putExtra("artist_name", artistName);
        userDetailsIntent.putExtra("artist_url", artistImageUrl);
        startActivity(userDetailsIntent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter.MainActivityPresenter getPresenter() {
        return new MainActivityPresenter(this);
    }

}

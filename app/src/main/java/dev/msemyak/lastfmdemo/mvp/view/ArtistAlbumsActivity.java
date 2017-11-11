package dev.msemyak.lastfmdemo.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.List;

import butterknife.BindView;
import dev.msemyak.lastfmdemo.R;
import dev.msemyak.lastfmdemo.base.BaseActivity;
import dev.msemyak.lastfmdemo.base.BasePresenter;
import dev.msemyak.lastfmdemo.base.BaseView;
import dev.msemyak.lastfmdemo.mvp.model.local.albums.Album;
import dev.msemyak.lastfmdemo.mvp.presenter.ArtistAlbumsPresenter;
import dev.msemyak.lastfmdemo.mvp.view.adapters.RVAdapterAlbums;
import dev.msemyak.lastfmdemo.utils.GlideApp;


public class ArtistAlbumsActivity extends BaseActivity<BasePresenter.ArtistAlbumsPresenter> implements BaseView.ArtistAlbumsView, BaseView.RVItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.rv_albums_list)
    RecyclerView recyclerViewAlbums;
    @BindView(R.id.view_flipper_albums)
    ViewFlipper viewFlipper;

    String artistName;
    String artistUrl;

    private RVAdapterAlbums listAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        artistName = getIntent().getStringExtra("artist_name");
        artistUrl = getIntent().getStringExtra("artist_url");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(artistName);

        GlideApp.with(this)
                .load(artistUrl)
                .centerCrop()
                .into(toolbarImage);

        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_to_up));

        myPresenter.loadArtistAlbums(artistName);

    }

    @Override
    public void showAlbums(List<Album> albumsList) {

        if (listAdapter == null) {
            recyclerViewAlbums.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewAlbums.setLayoutManager(layoutManager);
            listAdapter = new RVAdapterAlbums(albumsList, this, this);
            recyclerViewAlbums.setAdapter(listAdapter);
            recyclerViewAlbums.setNestedScrollingEnabled(false);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAlbums.getContext(), layoutManager.getOrientation());
            recyclerViewAlbums.addItemDecoration(dividerItemDecoration);

        } else {
            listAdapter.setNewData(albumsList);
            notifyAdapterDataChange();
            recyclerViewAlbums.scrollToPosition(0);
        }

    }

    @Override
    public void showWaitingScreen() {
        showScreen(R.id.loading_albums);
    }

    @Override
    public void showArtistAlbumsScreen() {
        showScreen(R.id.artist_albums_list);
    }

    @Override
    public void showErrorScreen() {
        showScreen(R.id.loading_albums_error);
    }

    void showScreen(int screen_id) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(viewFlipper.findViewById(screen_id)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        return true;
    }

    @Override
    public void notifyAdapterDataChange() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRVItemClick(View v, String albumName, String AlbumImageUrl) {
        showMessage(albumName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_artist_albums;
    }

    @Override
    protected BasePresenter.ArtistAlbumsPresenter getPresenter() {
        return new ArtistAlbumsPresenter(this);
    }
}

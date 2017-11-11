package dev.msemyak.lastfmdemo.mvp.view.adapters;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import dev.msemyak.lastfmdemo.R;
import dev.msemyak.lastfmdemo.base.BaseView;
import dev.msemyak.lastfmdemo.mvp.model.local.artists.Artist;
import dev.msemyak.lastfmdemo.utils.GlideApp;

public class RVAdapterArtists extends RecyclerView.Adapter<RVAdapterArtists.BaseViewHolder> {

    private List<Artist> artistsList;
    private Context context;
    private BaseView.RVItemClickListener clickListener;

    private static final int ITEM_USER = 1;
    private static final int ITEM_WAIT = 0;

    @IntDef({ITEM_USER, ITEM_WAIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface itemType {
    }

    public RVAdapterArtists(List<Artist> artistsList, Context context, BaseView.RVItemClickListener clickListener) {
        this.artistsList = artistsList;
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setNewData(List<Artist> artistsList) {
        this.artistsList = artistsList;
    }

    @Override
    public int getItemViewType(int position) {
        return artistsList.get(position) == null ? ITEM_WAIT : ITEM_USER;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, @itemType int viewType) {

        BaseViewHolder vh;

        if (viewType == ITEM_USER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_artist_info_item, parent, false);
            vh = new myViewHolder(v, viewType, clickListener);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_loading_spinner, parent, false);
            vh = new myProgressHolder(v, viewType);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder someHolder, int position) {

        if (someHolder.getItemType() == ITEM_USER) {

            myViewHolder holder = (myViewHolder) someHolder;

            Artist artistForDataBind = artistsList.get(position);

            // image size 0=small, 1=medium, 2=large, 3=extralarge, 4=mega
            int imageSize = 2;

            GlideApp.with(context)
                    .load(artistForDataBind.getImage().get(imageSize).getImageURL())
                    .into(holder.ivArtistImage);

            holder.tvArtistName.setText(artistForDataBind.getName());
            holder.tvArtistListeners.setText(context.getString(R.string.listeners) + artistForDataBind.getListeners());
            holder.artistName = artistForDataBind.getName();
            holder.artistUrl = artistForDataBind.getImage().get(imageSize+1).getImageURL();

        } else {
            myProgressHolder holder = (myProgressHolder) someHolder;
            holder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return artistsList.size();
    }

    // --- inner classes

    static class BaseViewHolder extends RecyclerView.ViewHolder {

        @itemType
        private int itemType;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }

    static class myViewHolder extends BaseViewHolder implements View.OnClickListener {

        ImageView ivArtistImage;
        TextView tvArtistName;
        TextView tvArtistListeners;
        String artistName;
        String artistUrl;

        BaseView.RVItemClickListener clickListener;

        myViewHolder(View v, @itemType int itemType, BaseView.RVItemClickListener clickListener) {
            super(v);
            this.setItemType(itemType);
            ivArtistImage = v.findViewById(R.id.iv_artist_image);
            tvArtistName = v.findViewById(R.id.tv_artist_name);
            tvArtistListeners = v.findViewById(R.id.tv_artist_listeners);
            this.clickListener = clickListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onRVItemClick(view, artistName, artistUrl);
        }
    }

    static class myProgressHolder extends BaseViewHolder {

        ProgressBar progressBar;

        myProgressHolder(View v, @itemType int itemType) {
            super(v);
            this.setItemType(itemType);
            progressBar = v.findViewById(R.id.rv_progress_bar);
        }
    }

}



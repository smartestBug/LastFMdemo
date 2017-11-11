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
import dev.msemyak.lastfmdemo.mvp.model.local.albums.Album;
import dev.msemyak.lastfmdemo.utils.GlideApp;

public class RVAdapterAlbums extends RecyclerView.Adapter<RVAdapterAlbums.BaseViewHolder> {

    private List<Album> albumsList;
    private Context context;
    private BaseView.RVItemClickListener clickListener;

    private static final int ITEM_USER = 1;
    private static final int ITEM_WAIT = 0;

    @IntDef({ITEM_USER, ITEM_WAIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface itemType {
    }

    public RVAdapterAlbums(List<Album> albumsList, Context context, BaseView.RVItemClickListener clickListener) {
        this.albumsList = albumsList;
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setNewData(List<Album> albumsList) {
        this.albumsList = albumsList;
    }

    @Override
    public int getItemViewType(int position) {
        return albumsList.get(position) == null ? ITEM_WAIT : ITEM_USER;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, @itemType int viewType) {

        BaseViewHolder vh;

        if (viewType == ITEM_USER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_album_info_item, parent, false);
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

            Album albumForDataBind = albumsList.get(position);

            // image size 0=small, 1=medium, 2=large, 3=extralarge
            int imageSize = 2;

            GlideApp.with(context)
                    .load(albumForDataBind.getImage().get(imageSize).getImageURL())
                    .into(holder.ivAlbumImage);

            holder.tvAlbumName.setText(albumForDataBind.getName());
            holder.tvAlbumPlaycount.setText(context.getString(R.string.playcount) + albumForDataBind.getPlaycount());
            holder.albumName = albumForDataBind.getName();
            holder.albumUrl = albumForDataBind.getImage().get(imageSize).getImageURL();

        } else {
            myProgressHolder holder = (myProgressHolder) someHolder;
            holder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return albumsList.size();
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

        ImageView ivAlbumImage;
        TextView tvAlbumName;
        TextView tvAlbumPlaycount;
        String albumName;
        String albumUrl;

        BaseView.RVItemClickListener clickListener;

        myViewHolder(View v, @itemType int itemType, BaseView.RVItemClickListener clickListener) {
            super(v);
            this.setItemType(itemType);
            ivAlbumImage = v.findViewById(R.id.iv_album_image);
            tvAlbumName = v.findViewById(R.id.tv_album_name);
            tvAlbumPlaycount = v.findViewById(R.id.tv_album_playcount);
            this.clickListener = clickListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onRVItemClick(view, albumName, albumUrl);
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



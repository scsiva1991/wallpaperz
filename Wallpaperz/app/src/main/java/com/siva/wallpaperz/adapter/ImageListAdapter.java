package com.siva.wallpaperz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.siva.wallpaperz.GlideUtil;
import com.siva.wallpaperz.Model.Image;

import java.util.List;

import com.siva.wallpaperz.R;

/**
 * Created by sivakumar on 10-01-2017.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<Image> imageList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailView;

        public ViewHolder(View view) {
            super(view);
            thumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public ImageListAdapter(List<Image> imageList) {
        this.imageList = imageList;
    }

    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = imageList.get(position);
        System.out.print("========= image ========"+image.getThumb());
        GlideUtil.loadImage(image.getThumb(), holder.thumbnailView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void refillAdapter(Image image) {
        imageList.add(image);
        notifyItemInserted(getItemCount() - 1);
    }

    public void cleanUp() {
        imageList.clear();
    }

}

package com.siva.wallpaperz.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.siva.wallpaperz.FullImageActivity;
import com.siva.wallpaperz.GlideUtil;
import com.siva.wallpaperz.Model.Image;
import com.siva.wallpaperz.OnLoadMoreListener;
import com.siva.wallpaperz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 13/1/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>  {

    private List<Image> imageList;
    private LayoutInflater inflater;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context context;

    public ImageAdapter(Context context, List<Image> imageList, RecyclerView recyclerView) {
        this.imageList = imageList;
        this.context = context;

        if( recyclerView.getLayoutManager() instanceof LinearLayoutManager ) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + 2)) {

                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }

                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imageList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageAdapter.ImageViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.image_list_item, parent, false);

        vh = new ImageViewHolder (v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = imageList.get(position);
        GlideUtil.loadImage(image.getListUrl(), ((ImageViewHolder) holder).thumbnail, image.getTimeStamp(), image.getColor());
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;


        public ImageViewHolder(View v) {
            super(v);

            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),
                            "OnClick :",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, FullImageActivity.class);
                    int position = getLayoutPosition();
                    System.out.println("=== pos ==="+position);
                    Image image = imageList.get(position);

                    System.out.println("###image"+ imageList);

                    Bundle extras = new Bundle();
                    extras.putString("imageUrl", image.getListUrl());
                    extras.putString("time", image.getTimeStamp());
                    extras.putString("color", image.getColor());
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });
        }
    }



}

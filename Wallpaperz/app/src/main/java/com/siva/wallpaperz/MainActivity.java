package com.siva.wallpaperz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.siva.wallpaperz.Model.Image;
import com.siva.wallpaperz.adapter.ImageAdapter;
import com.siva.wallpaperz.adapter.ImageListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Image> imageList;
    private ImageAdapter mAdapter;
    private List<String> urlList;
    private List<String> colorList;
    private LinearLayoutManager mLayoutManager;
    protected Handler handler;
    private LinearLayout progressLayout;
    private int lastVisibleItem, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    private void initializeViews() {
        imageList = new ArrayList<>();
        urlList = new ArrayList<>();
        colorList = new ArrayList<>();
        handler = new Handler();
        urlList.add("https://source.unsplash.com/category/nature");
        urlList.add("https://source.unsplash.com/category/buildings");
        urlList.add("https://source.unsplash.com/category/food");
        urlList.add("https://source.unsplash.com/category/people");
        urlList.add("https://source.unsplash.com/category/technology");
        urlList.add("https://source.unsplash.com/category/objects");

        colorList.add("c1");
        colorList.add("c2");
        colorList.add("c3");
        colorList.add("c4");
        colorList.add("c5");
        colorList.add("c6");
        loadData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressLayout = (LinearLayout) findViewById(R.id.progress);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new ScrollControlLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ImageAdapter(this, imageList, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressLayout.setVisibility(View.GONE);
            }
        }, 3000);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //imageList.add(null);
               // mAdapter.notifyItemInserted(imageList.size() - 1);
                showAndHideProgress();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        imageList.remove(imageList.size() - 1);
                        mAdapter.notifyItemRemoved(imageList.size());
                        for (int i = 0; i < 6; i++) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String string  = dateFormat.format(new Date());
                            imageList.add(new Image(urlList.get(i), string, colorList.get(i))); 
                        }
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                    }
                }, 2000);
            }
        });

    }

    public void showAndHideProgress() {
        progressLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressLayout.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void loadData() {
        for (int i = 0; i < 6; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String string  = dateFormat.format(new Date());
            imageList.add(new Image(urlList.get(i), string, colorList.get(i)));
        }
    }
}

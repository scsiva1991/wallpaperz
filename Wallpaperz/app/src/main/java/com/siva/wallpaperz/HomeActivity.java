package com.siva.wallpaperz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.siva.wallpaperz.Model.Image;
import com.siva.wallpaperz.adapter.ImageListAdapter;

import java.util.ArrayList;
import java.util.List;

import com.siva.wallpaperz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Image> imageList;
    private ChildEventListener imageListener;
    private DatabaseReference imageRef;
    private ImageListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
    }

    private void initializeViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.image_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        mRecyclerView.setHasFixedSize(true);

        imageRef = FirebaseDatabase.getInstance().getReference().child("images");
        imageList = new ArrayList<>();

        mAdapter = new ImageListAdapter(imageList);
        mRecyclerView.setAdapter(mAdapter);

        imageListener = imageRef.limitToFirst(50).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if( dataSnapshot != null && dataSnapshot.getValue() != null) {
                    try {
                        Image image = dataSnapshot.getValue(Image.class);
                        System.out.println("==== image @@@"+ image.getThumb());
                        mAdapter.refillAdapter(image);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(imageListener != null) {
            imageRef.removeEventListener(imageListener);
        }
        mAdapter.cleanUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(imageListener != null) {
            imageRef.removeEventListener(imageListener);
        }
        mAdapter.cleanUp();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

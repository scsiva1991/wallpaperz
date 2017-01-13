package com.siva.wallpaperz;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FullImageActivity extends AppCompatActivity {

    private ImageView fullImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        fullImageView = (ImageView) findViewById(R.id.full_image);
        Bundle extras = getIntent().getExtras();
        GlideUtil.loadImage( extras.getString("imageUrl"), fullImageView, extras.getString("time"), extras.getString("color"));
    }
}

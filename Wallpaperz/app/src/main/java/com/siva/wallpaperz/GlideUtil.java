package com.siva.wallpaperz;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.siva.wallpaperz.R;

/**
 * Created by user on 10/1/17.
 */

public class GlideUtil {
    public static void loadImage(String url, ImageView imageView, String timeStamp, String color) {
        Context context = imageView.getContext();
        ColorDrawable cd;
        switch (color) {
            case "c1":
                cd = new ColorDrawable(ContextCompat.getColor(context, R.color.c1));
                break;
            case "c2":
                cd = new ColorDrawable(ContextCompat.getColor(context, R.color.c2));
                break;
            case "c3":
                cd = new ColorDrawable(ContextCompat.getColor(context, R.color.c3));
                break;
            case "c4":
                cd = new ColorDrawable(ContextCompat.getColor(context, R.color.c4));
                break;
            case "c5":
                cd = new ColorDrawable(ContextCompat.getColor(context, R.color.c5));
                break;
            case "c6":
                cd = new ColorDrawable(ContextCompat.getColor(context, R.color.c6));
                break;
            default:
                cd = new ColorDrawable(ContextCompat.getColor(context, R.color.cardview_dark_background));

        }
        Glide.with(context)
                .load(url)
                .placeholder(cd)
                .crossFade()
                .signature(new StringSignature(timeStamp))
                .centerCrop()
                .into(imageView);
    }
}

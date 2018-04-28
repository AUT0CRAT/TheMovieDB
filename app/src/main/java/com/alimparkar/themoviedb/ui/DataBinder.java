package com.alimparkar.themoviedb.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alimparkar.themoviedb.R;

/**
 * Created by alimparkar on 25/04/18.
 */

public class DataBinder {

    private DataBinder() {}

    @BindingAdapter("imageUrl")
    public static void setImageViewUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideApp.with(context).load(url).error(R.drawable.ic_broken_image).fitCenter().into(imageView);

    }
}

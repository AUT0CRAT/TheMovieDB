package com.alimparkar.themoviedb.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.alimparkar.themoviedb.R;

/**
 * Binding Adapters for data binding
 */
public class DataBinder {

    private DataBinder() {
    }

    @BindingAdapter("imageUrl")
    public static void setImageViewUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideApp.with(context)
            .load(url)
            .error(R.drawable.ic_broken_image)
            .fitCenter()
            .into(imageView);
    }
}

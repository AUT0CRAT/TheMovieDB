package com.alimparkar.themoviedb.ui.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import com.alimparkar.themoviedb.BR;

/**
 * ViewHolder that supports dataBinding. Make sure your databinding variables are named as
 * holderData and clickListener
 */
public class BindingViewHolder<T> extends RecyclerView.ViewHolder {

    private final ViewDataBinding dataBinding;
    private final OnItemClickListener<T> clickListener;

    BindingViewHolder(ViewDataBinding dataBinding, OnItemClickListener<T> listener) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
        this.clickListener = listener;
    }

    void bind(T data) {
        dataBinding.setVariable(BR.holderData, data);
        dataBinding.setVariable(BR.clickListener, clickListener);
        dataBinding.executePendingBindings();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }
}

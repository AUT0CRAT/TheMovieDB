package com.alimparkar.themoviedb.ui.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.alimparkar.themoviedb.BR;

/**
 * Created by alimparkar on 23/04/18.
 */
public class BindingViewHolder<T> extends RecyclerView.ViewHolder {

    private ViewDataBinding dataBinding;
    private OnItemClickListener<T> clickListener;

    public BindingViewHolder(ViewDataBinding dataBinding, OnItemClickListener<T> listener) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
        this.clickListener = listener;
    }

    public void bind(T data) {
        dataBinding.setVariable(BR.holderData, data);
        dataBinding.setVariable(BR.clickListener, clickListener);
        dataBinding.executePendingBindings();
    }


    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }
}

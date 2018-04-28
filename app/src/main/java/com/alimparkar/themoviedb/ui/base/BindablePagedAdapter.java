package com.alimparkar.themoviedb.ui.base;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by alimparkar on 23/04/18.
 */

public abstract class BindablePagedAdapter<MODEL>
    extends PagedListAdapter<MODEL, BindingViewHolder<MODEL>> {

    private BindingViewHolder.OnItemClickListener<MODEL> listener;

    protected BindablePagedAdapter(@NonNull DiffUtil.ItemCallback<MODEL> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BindingViewHolder<MODEL> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding =
            DataBindingUtil.inflate(inflater, getLayoutId(viewType), parent, false);

        return new BindingViewHolder<>(viewDataBinding, item -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<MODEL> holder, int position) {
        MODEL currentObject = getItem(position);
        if (currentObject != null) {
            holder.bind(currentObject);
        }
    }

    abstract public int getLayoutId(int viewType);

    public void setListener(BindingViewHolder.OnItemClickListener<MODEL> listener) {
        this.listener = listener;
    }
}

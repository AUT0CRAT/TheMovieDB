package com.alimparkar.themoviedb.ui.base;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Base PagedListAdapter with support for DataBinding
 *
 * @param <MODEL> the data type that would be used as the backed data for the adapter
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

    abstract protected int getLayoutId(int viewType);

    public void setListener(BindingViewHolder.OnItemClickListener<MODEL> listener) {
        this.listener = listener;
    }
}

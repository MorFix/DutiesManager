package com.morfix.dutiesmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class ListAdapter<T> extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<T> data = Collections.emptyList();
    private int itemLayoutResource;
    private ItemBindListener<RecyclerView.ViewHolder> itemBindListener;

    public ListAdapter(List<T> items, int itemLayoutResource) {
        getData().addAll(items);
        this.itemLayoutResource = itemLayoutResource;
    }

    public List<T> getData() {
        return data;
    }

    public void setItems(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private ItemBindListener<RecyclerView.ViewHolder> getItemBindListener() {
        return itemBindListener;
    }

    public void setItemBindListener(ItemBindListener<RecyclerView.ViewHolder> itemBindListener) {
        this.itemBindListener = itemBindListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutResource, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemBindListener() != null) {
            getItemBindListener().onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public View getView() {
            return view;
        }

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}

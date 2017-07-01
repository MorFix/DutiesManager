package com.morfix.dutiesmanager;

import android.support.v7.widget.RecyclerView;

public interface ItemBindListener<T extends RecyclerView.ViewHolder> {
    void onBindViewHolder(T holder, int position);
}

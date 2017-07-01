package com.morfix.dutiesmanager.dataAccess.interfaces;

import android.arch.lifecycle.LiveData;

import java.util.List;

public interface BaseDao<T> {
    LiveData<List<T>> getAll();
    LiveData<T> getById(Long id);
    void insert(T entity);
    void update(T entity);
    void delete(T entity);
}

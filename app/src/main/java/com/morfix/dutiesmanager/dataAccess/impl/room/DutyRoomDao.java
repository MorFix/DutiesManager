package com.morfix.dutiesmanager.dataAccess.impl.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.morfix.dutiesmanager.dataAccess.interfaces.DutyDao;
import com.morfix.dutiesmanager.models.Duty;

import java.util.List;

@Dao
interface DutyRoomDao extends DutyDao {
    @Ignore
    String tableName = "Duty";

    @Override
    @Query("SELECT * FROM " + tableName)
    LiveData<List<Duty>> getAll();

    @Override
    @Query("SELECT * FROM " + tableName + " WHERE Id = :id")
    LiveData<Duty> getById(Long id);

    @Override
    @Insert
    void insert(Duty duty);

    @Override
    @Update
    void update(Duty duty);

    @Override
    @Delete
    void delete(Duty duty);
}

package com.morfix.dutiesmanager.dataAccess.impl.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.morfix.dutiesmanager.models.Duty;

@Database(entities = {Duty.class}, version = 2)
public abstract class DutiesDatabase extends RoomDatabase {
    public abstract DutyRoomDao dutyRoomDao();

    private static final String dbName = "duties";
    private static DutiesDatabase INSTANCE;

    public static DutiesDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DutiesDatabase.class, dbName)
                            .build();
        }

        return INSTANCE;
    }
}
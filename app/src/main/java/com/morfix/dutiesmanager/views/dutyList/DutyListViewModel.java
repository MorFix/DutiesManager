package com.morfix.dutiesmanager.views.dutyList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.morfix.dutiesmanager.dataAccess.impl.room.DutiesDatabase;
import com.morfix.dutiesmanager.dataAccess.interfaces.DutyDao;
import com.morfix.dutiesmanager.models.Duty;

import java.util.List;


@SuppressWarnings("WeakerAccess")
public class DutyListViewModel extends AndroidViewModel {
    private LiveData<List<Duty>> dutyList;
    private DutyDao dutyDao;

    public DutyListViewModel(Application application) {
        super(application);

        dutyDao = DutiesDatabase.getDatabase(this.getApplication()).dutyRoomDao();
    }

    LiveData<List<Duty>> getDutyList() {
        if (dutyList == null) {
            dutyList = dutyDao.getAll();
        }

        return dutyList;
    }

    void delete(final Duty duty) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dutyDao.delete(duty);

                return null;
            }
        }.execute();
    }

    void insert(final Duty duty) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dutyDao.insert(duty);

                return null;
            }
        }.execute();
    }
}

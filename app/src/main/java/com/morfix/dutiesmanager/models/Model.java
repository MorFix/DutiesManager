package com.morfix.dutiesmanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public abstract class Model {
    @PrimaryKey
    public Long Id;
}

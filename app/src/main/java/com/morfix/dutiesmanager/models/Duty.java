package com.morfix.dutiesmanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

@Entity
public class Duty extends Model {
    public String name;

    public Duty() {}

    @Ignore
    public Duty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package com.example.eventreminder.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.eventreminder.dao.EventDao;
import com.example.eventreminder.model.Event;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
}
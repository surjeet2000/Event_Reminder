package com.example.eventreminder.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.eventreminder.model.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Event... contents);

    @Delete
    void delete(Event content);
}

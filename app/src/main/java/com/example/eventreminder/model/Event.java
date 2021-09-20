package com.example.eventreminder.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.eventreminder.R;

@Entity
public class Event {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public enum Severity {
        COLD("COLD", R.color.colorCold),
        WARM("WARM", R.color.colorWarm),
        HOT("HOT", R.color.colorHot);

        public String getLevel() {
            return level;
        }

        public int getColor() {
            return color;
        }

        private String level;
        private int color;

        Severity(String level, int color) {
            this.level = level;
            this.color = color;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey private int id;
    @ColumnInfo private String title;
    @ColumnInfo private String description;
    @ColumnInfo private String eventTime;
    @ColumnInfo private Severity severity = Severity.COLD;

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
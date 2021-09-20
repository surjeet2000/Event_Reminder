package com.example.eventreminder.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    public static void updateEventIdCount(Activity activity){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("available-id", getAvailableId(activity)+1);
        editor.apply();
    }

    public static int getAvailableId(Activity activity){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        int availableId = sharedPref.getInt("available-id", 0);
        return availableId;
    }
}

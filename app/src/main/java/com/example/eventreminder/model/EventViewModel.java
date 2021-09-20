package com.example.eventreminder.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class EventViewModel extends ViewModel {

    private MutableLiveData<List<Event>> currentContentTextList;

    public MutableLiveData<List<Event>> getCurrentContent() {
        if (currentContentTextList == null) {
            currentContentTextList = new MutableLiveData<List<Event>>();
        }
        return currentContentTextList;
    }
}
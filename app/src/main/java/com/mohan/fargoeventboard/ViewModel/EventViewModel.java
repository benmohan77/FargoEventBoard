package com.mohan.fargoeventboard.ViewModel;

import com.mohan.fargoeventboard.data.AppRepository;
import com.mohan.fargoeventboard.data.Event;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EventViewModel extends ViewModel {
    private AppRepository appRepository;

    @Inject
    public EventViewModel(AppRepository appRepository){
        this.appRepository = appRepository;
    }

    public LiveData<Event> getEvent(int eventId) { return appRepository.getEvent(eventId); }
}
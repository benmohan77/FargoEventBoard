package com.mohan.fargoeventboard.ViewModel;

import com.mohan.fargoeventboard.data.AppRepository;
import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.data.Speaker;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel that handles data transfer between the AppRepository and the Event page.
 */
public class EventViewModel extends ViewModel {
    private AppRepository appRepository;

    @Inject
    public EventViewModel(AppRepository appRepository){
        this.appRepository = appRepository;
    }

    public LiveData<Event> getEvent(int eventId) { return appRepository.getEvent(eventId); }

    public LiveData<List<Speaker>> getSpeakers(int eventId){
        return appRepository.getSpeakers(eventId);
    }
}
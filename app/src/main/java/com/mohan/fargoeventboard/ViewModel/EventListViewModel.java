package com.mohan.fargoeventboard.ViewModel;

import com.mohan.fargoeventboard.data.AppRepository;
import com.mohan.fargoeventboard.data.Event;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel that handles data transfer between the AppRepository and the EventList page.
 */
public class EventListViewModel extends ViewModel {

    private AppRepository appRepository;

    @Inject
    public EventListViewModel(AppRepository appRepository){
        this.appRepository = appRepository;
    }


    public LiveData<List<Event>> getEvents(){
        return appRepository.getAllEvents();
    }

}

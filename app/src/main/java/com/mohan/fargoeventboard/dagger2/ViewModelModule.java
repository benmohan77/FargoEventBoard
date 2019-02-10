package com.mohan.fargoeventboard.dagger2;

import com.mohan.fargoeventboard.ViewModel.EventListViewModel;
import com.mohan.fargoeventboard.ViewModel.EventViewModel;
import com.mohan.fargoeventboard.ViewModel.FactoryViewModel;
import com.mohan.fargoeventboard.ViewModel.LoginViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Provides ViewModels for the application.
 */
@Module
public abstract class ViewModelModule {


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EventViewModel.class)
    abstract ViewModel bindEventViewModel(EventViewModel eventViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EventListViewModel.class)
    abstract ViewModel bindEventListViewModel(EventListViewModel eventListViewModel);

}

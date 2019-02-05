package com.mohan.fargoeventboard.ViewModel;

import com.mohan.fargoeventboard.data.AppRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private AppRepository appRepository;

    @Inject
    public LoginViewModel(AppRepository appRepository){
        this.appRepository = appRepository;
    }

    public boolean handleLogin(String username, String password){
        appRepository.login(username, password);
        return true;
    }
}

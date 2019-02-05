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

    public void handleLogin(String username, String password, AppRepository.LoginCallback loginCallback){
        appRepository.login(username, password, loginCallback);
    }

    public boolean getLoginStatus(){
        return appRepository.getLoginStatus();
    }
}

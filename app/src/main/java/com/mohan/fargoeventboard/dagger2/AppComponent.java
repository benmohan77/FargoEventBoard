package com.mohan.fargoeventboard.dagger2;


import android.app.Application;

import com.mohan.fargoeventboard.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Component that bridges modules to App.
 */
@Singleton
@Component(modules = {ActivityModule.class, FragmentModule.class, AppModule.class, AndroidInjectionModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}

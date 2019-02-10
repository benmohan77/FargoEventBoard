package com.mohan.fargoeventboard.dagger2;

import com.mohan.fargoeventboard.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Provider for the MainActivity.
 */
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
}

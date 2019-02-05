package com.mohan.fargoeventboard.dagger2;

import com.mohan.fargoeventboard.fragments.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();
}

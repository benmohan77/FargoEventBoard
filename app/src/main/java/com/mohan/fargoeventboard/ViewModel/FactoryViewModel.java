package com.mohan.fargoeventboard.ViewModel;



import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Class that handles the creation of ViewModels for Dagger2
 */
@Singleton
public class FactoryViewModel implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public FactoryViewModel( Map<Class<? extends ViewModel>, Provider<ViewModel>> creators){
        this.creators = creators;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create( Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators.get(modelClass);

        if(creator == null){
            throw new IllegalArgumentException("model class " + modelClass + " not found");
        }
        return (T) creator.get();
    }
}

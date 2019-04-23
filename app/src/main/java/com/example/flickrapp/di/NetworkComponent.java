package com.example.flickrapp.di;


import com.example.flickrapp.data.remote.FlickrModule;
import com.example.flickrapp.data.remote.NetworkModule;
import com.example.flickrapp.viewmodel.ImagesViewModel;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, FlickrModule.class})
public interface NetworkComponent {
    void inject(ImagesViewModel imagesViewModel);
}

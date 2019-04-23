package com.example.flickrapp.data.remote;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FlickrModule {

    @Provides
    public FlickrService provideFlickrService(Retrofit retrofit) {
        return retrofit.create(FlickrService.class);
    }

}

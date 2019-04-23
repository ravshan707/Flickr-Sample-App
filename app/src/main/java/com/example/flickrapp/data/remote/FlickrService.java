package com.example.flickrapp.data.remote;

import com.example.flickrapp.data.remote.responses.FlickrResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {

    @GET("rest/?\n" +
            "method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1")
    Single<FlickrResponse> fetchImages(@Query("text") String query);
}

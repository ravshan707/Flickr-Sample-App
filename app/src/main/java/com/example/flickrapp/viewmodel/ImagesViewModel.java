package com.example.flickrapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.flickrapp.data.models.Image;
import com.example.flickrapp.data.remote.FlickrService;
import com.example.flickrapp.data.remote.responses.FlickrResponse;
import com.example.flickrapp.data.remote.responses.Photo;
import com.example.flickrapp.di.DaggerNetworkComponent;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;


import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ImagesViewModel extends ViewModel {
    @Inject
    FlickrService mFlickrApi;

    private MutableLiveData<List<Image>> images = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ImagesViewModel() {
        DaggerNetworkComponent.create().inject(this);
    }

    public void fetchImages(String query) {

        mFlickrApi.fetchImages(query)
                .map(new Function<FlickrResponse, List<Image>>() {
                    @Override
                    public List<Image> apply(FlickrResponse response) throws Exception {
                        ArrayList<Image> images = new ArrayList<>();
                        List<Photo> photos = response.getPhotos().getPhoto();

                        if (photos != null && !photos.isEmpty()) {
                            for (Photo p : photos) {
                                Image image = new Image();

                                String url = String.format("http://farm%s.static.flickr.com/%s/%s_%s.jpg",
                                        String.valueOf(p.getFarm()), p.getServer(), p.getId(), p.getSecret());
                                image.setUrl(url);
                                images.add(image);
                            }
                        }

                        return images;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Image> images) {
                        update(images);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public MutableLiveData<List<Image>> getImages() {
        return images;
    }

    private void update(List<Image> newRepos) {
        images.postValue(newRepos);

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}

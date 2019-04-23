package com.example.flickrapp.data.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

//        client.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                HttpUrl originalUrl = original.url();
//
//                HttpUrl url = originalUrl.newBuilder()
//                        .addQueryParameter("api_key", "3e7cc266ae2b0e0d78e279ce8e361736")
//                        .build();
//
//                Request request = original.newBuilder().url(url).build();
//
//                return chain.proceed(request);
//            }
//        });
        return client.build();
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public static Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}

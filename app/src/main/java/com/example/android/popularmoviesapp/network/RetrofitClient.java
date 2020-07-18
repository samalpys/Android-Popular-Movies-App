package com.example.android.popularmoviesapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final static String BASE_IRL = "http://api.themoviedb.org/3/";

    private static MovieApiService sInstance;

    public static MovieApiService getInstance() {
        if (sInstance == null) {
            sInstance = getRetrofitInstance().create(MovieApiService.class);
        }
        return sInstance;
    }

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_IRL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

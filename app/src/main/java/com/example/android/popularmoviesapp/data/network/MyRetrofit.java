package com.example.android.popularmoviesapp.data.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private final static String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit sInstance;

    public static Retrofit getInstance() {
        if (sInstance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .addInterceptor(new AuthInterceptor())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            sInstance = retrofit;
        }
        return sInstance;
    }

    static class AuthInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("api_key", "9321c4fc5f95b92bce700096da663cde")
                    .build();

            request = request.newBuilder().url(url).build();

            return chain.proceed(request);
        }
    }
}

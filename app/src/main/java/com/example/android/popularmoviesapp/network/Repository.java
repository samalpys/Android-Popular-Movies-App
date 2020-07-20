package com.example.android.popularmoviesapp.network;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.model.MovieResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Repository {

    private final static String BASE_URL = "http://api.themoviedb.org/3/";
    private RetrofitService retrofitService;
    private static Repository repositoryInstance;

    public Repository() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(new AuthInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        retrofitService = retrofit.create(RetrofitService.class);
    }

    public synchronized static Repository getInstance() {
        if (repositoryInstance == null) {
            repositoryInstance = new Repository();
        }
        return repositoryInstance;
    }

    public LiveData<MovieResponse> getPopularMovies(int page) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        retrofitService.getPopularMovies(page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) { }
        });

        return data;
    }

    public LiveData<MovieResponse> getTopRatedMovies(int page) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        retrofitService.getTopRatedMovies(page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) { }
        });

        return data;
    }

    public LiveData<Movie> getMovieDetails(long id) {
        final MutableLiveData<Movie> data = new MutableLiveData<>();

        retrofitService.getMovieDetails(id).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) { }
        });

        return data;
    }


    class AuthInterceptor implements Interceptor {

        @NotNull
        @Override
        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("api_key", "9321c4fc5f95b92bce700096da663cde")
                    .build();

            request = request.newBuilder().url(url).build();

            return chain.proceed(request);
        }
    }

}

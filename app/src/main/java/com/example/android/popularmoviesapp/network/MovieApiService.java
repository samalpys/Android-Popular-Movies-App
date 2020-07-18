package com.example.android.popularmoviesapp.network;

import com.example.android.popularmoviesapp.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("discover/movie")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortBy,
            @Query("page") Integer page
    );
}

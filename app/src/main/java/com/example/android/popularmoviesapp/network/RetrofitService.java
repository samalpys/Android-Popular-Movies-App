package com.example.android.popularmoviesapp.network;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("discover/movie")
    Call<MovieResponse> getMovies(
            @Query("sort_by") String sortBy,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(
        @Path("movie_id") long id
    );
}

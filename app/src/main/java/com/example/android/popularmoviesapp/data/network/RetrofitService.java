package com.example.android.popularmoviesapp.data.network;

import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.data.models.MovieResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

//    @GET("discover/movie")
//    Call<MovieResponse> getAllMovies(@Query("sort_by") String sortBy, @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResponse> getAllMoviesWithRx(@Query("sort_by") String sortBy, @Query("page") int page);

//    @GET("movie/{movie_id}")
//    Call<Movie> getMovieDetails(@Path("movie_id") long id);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetailsWithRx(@Path("movie_id") long id);
}

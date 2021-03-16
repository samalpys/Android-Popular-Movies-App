package com.example.android.popularmoviesapp.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.popularmoviesapp.data.models.Movie;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MovieDao {

    @Insert
    void insertFavouriteMovie(Movie movie);

    @Query("DELETE FROM movie_table WHERE id = :movieId")
    void deleteFavouriteMovieById(int movieId);

    @Query("SELECT * FROM movie_table")
    Flowable<List<Movie>> observeFavouriteMovies();

    @Query("DELETE FROM movie_table")
    void deleteAllFavouriteMovies();

    @Query("SELECT * FROM movie_table WHERE id ==:id")
    Movie getFavouriteMovieById(long id);
}
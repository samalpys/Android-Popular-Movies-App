package com.example.android.popularmoviesapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.popularmoviesapp.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insertFavouriteMovie(Movie movie);

    @Query("DELETE FROM movie_table WHERE id = :movieId")
    void deleteFavouriteMovieById(int movieId);

    @Query("SELECT * FROM movie_table ORDER BY popularity DESC")
    LiveData<List<Movie>> getAllFavouriteMovies();

    @Query("DELETE FROM movie_table")
    void deleteAllFavouriteMovies();
}
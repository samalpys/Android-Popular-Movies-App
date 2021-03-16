package com.example.android.popularmoviesapp.data;

import android.app.Application;

import com.example.android.popularmoviesapp.data.database.MovieDao;
import com.example.android.popularmoviesapp.data.database.MovieDatabase;
import com.example.android.popularmoviesapp.data.network.MyRetrofit;
import com.example.android.popularmoviesapp.data.network.RetrofitService;
import com.example.android.popularmoviesapp.data.models.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class Repository {

    private static Repository repositoryInstance;
    private final RetrofitService retrofitService;
    private final MovieDao mMovieDao;


    private Repository(Application application) {
        retrofitService = MyRetrofit.getInstance().create(RetrofitService.class);
        mMovieDao = MovieDatabase.getInstance(application).movieDao();
    }

    public synchronized static Repository getInstance(Application application) {
        if (repositoryInstance == null) {
            repositoryInstance = new Repository(application);
        }
        return repositoryInstance;
    }

    public Single<List<Movie>> observeMovies(String sortBy, int page) {
        return retrofitService.observeMovies(sortBy, page)
                .subscribeOn(Schedulers.io())
                .flatMap(it -> Observable.fromIterable(it.getMovies()))
                .toList();
    }

    public Observable<Movie> observeMovieDetails(long id) {
        return retrofitService.observeMovieDetails(id)
                .subscribeOn(Schedulers.io());
    }

    // for Room
    public Observable<List<Movie>> observeFavouriteMovies() {
        return mMovieDao.observeFavouriteMovies()
                .subscribeOn(Schedulers.computation())
                .toObservable();
    }

    public Completable observeInsertFavouriteMovie(Movie movie) {
        return Completable.fromAction(() -> mMovieDao.insertFavouriteMovie(movie))
                .subscribeOn(Schedulers.io());
    }

    public Completable observeDeleteFavouriteMovieById(int id) {
        return Completable.fromAction(() -> mMovieDao.deleteFavouriteMovieById(id))
                .subscribeOn(Schedulers.io());
    }

    public Completable observeDeleteAllFavouriteMovies() {
        return Completable.fromAction(() -> mMovieDao.deleteAllFavouriteMovies())
                .subscribeOn(Schedulers.io());
    }

}

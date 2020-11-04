package com.example.android.popularmoviesapp.data;

import android.app.Application;

import com.example.android.popularmoviesapp.data.database.MovieDao;
import com.example.android.popularmoviesapp.data.database.MovieDatabase;
import com.example.android.popularmoviesapp.data.network.MyRetrofit;
import com.example.android.popularmoviesapp.data.network.RetrofitService;
import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.data.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Repository {

    private static Repository repositoryInstance;
    private RetrofitService retrofitService; // for Retrofit
    private MovieDao mMovieDao; // for Room

    private CompositeDisposable disposable = new CompositeDisposable();


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

    public MutableLiveData<List<Movie>> getMoviesWithRx(String sortBy, int page) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        ArrayList<Movie> movies = new ArrayList<>();
        MutableLiveData<List<Movie>> moviesLiveData=new MutableLiveData<>();

        disposable.add(
                retrofitService.getAllMoviesWithRx(sortBy, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(movieResponse -> Observable.fromArray(movieResponse.getMovies().toArray(new Movie[0])))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Movie>() {
                            @Override
                            public void onNext(@NonNull Movie movie) {
                                movies.add(movie);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                moviesLiveData.postValue(movies);
                            }
                        })
        );

        return moviesLiveData;
    }

    public LiveData<Movie> getMovieDetailsWithRx(long id) {
        final MutableLiveData<Movie> data = new MutableLiveData<>();

        disposable.add(
                retrofitService.getMovieDetailsWithRx(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Movie>() {
                            @Override
                            public void onNext(@NonNull Movie movie) {
                                data.setValue(movie);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );

        return data;
    }


    // for Room
    public LiveData<List<Movie>> getAllFavouriteMovies() {
        MutableLiveData<List<Movie>> favouriteMoviesLiveData = new MutableLiveData<>();

        disposable.add(
                mMovieDao.getAllFavouriteMoviesRx()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Movie>>() {
                            @Override
                            public void accept(List<Movie> movies) {
                                favouriteMoviesLiveData.postValue(movies);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {

                            }
                        })
        );

        return favouriteMoviesLiveData;
    }

    public void clearCompositeDisposable() {
        disposable.clear();
    }

    public void insertFavouriteMovie(Movie movie) {

        disposable.add(
                Completable.fromAction(() -> mMovieDao.insertFavouriteMovie(movie))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() { }

                            @Override
                            public void onError(@NonNull Throwable e) { }
                        })
        );
    }

    public void deleteFavouriteMovieById(int id) {
        disposable.add(
                Completable.fromAction(() -> mMovieDao.deleteFavouriteMovieById(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        })
        );

    }

    public void deleteAllFavouriteMovies() {
        disposable.add(
                Completable.fromAction(() -> mMovieDao.deleteAllFavouriteMovies())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        })
        );
    }


}

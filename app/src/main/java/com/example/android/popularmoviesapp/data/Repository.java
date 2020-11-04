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
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    // for Retrofit
//    public LiveData<MovieResponse> getMovies(String sortBy, int page) {
//        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();
//
//        retrofitService.getAllMovies(sortBy, page).enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                data.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) { }
//        });
//
//        return data;
//    }

    public MutableLiveData<List<Movie>> getMoviesWithRx(String sortBy, int page) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        ArrayList<Movie> movies = new ArrayList<>();
        MutableLiveData<List<Movie>> moviesLiveData=new MutableLiveData<>();

        disposable.add(
                retrofitService.getAllMoviesWithRx(sortBy, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Function<MovieResponse, Observable<Movie>>() {
                            @Override
                            public Observable<Movie> apply(@NonNull MovieResponse movieResponse) {
                                return Observable.fromArray(movieResponse.getMovies().toArray(new Movie[0]));
                            }
                        })
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

//    public LiveData<Movie> getMovieDetails(long id) {
//        final MutableLiveData<Movie> data = new MutableLiveData<>();
//
//        retrofitService.getMovieDetails(id).enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                data.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) { }
//        });
//
//        return data;
//    }

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
//        return mMovieDao.getAllFavouriteMovies();

        MutableLiveData<List<Movie>> favouriteMoviesLiveData = new MutableLiveData<>();

        disposable.add(
                mMovieDao.getAllFavouriteMoviesRx()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Movie>>() {
                            @Override
                            public void accept(List<Movie> movies) {
                                System.out.println("HERE: " + movies);
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
//        new InsertFavouriteMovieAsyncTask(mMovieDao).execute(movie);

        disposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() {
                        mMovieDao.insertFavouriteMovie(movie);
                    }
                })
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

//    private static class InsertFavouriteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
//
//        private MovieDao movieDao; // we need this to make db operations
//
//        public InsertFavouriteMovieAsyncTask(MovieDao movieDao) {
//            this.movieDao = movieDao;
//        }
//
//        @Override
//        protected Void doInBackground(Movie... movies) {
//            Movie movieToInsert = movies[0];
//            movieDao.insertFavouriteMovie(movieToInsert);
//            return null;
//        }
//    }

    public void deleteFavouriteMovieById(int id) {
//        new DeleteFavouriteMovieByIdAsyncTask(mMovieDao).execute(id);
        disposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() {
                        mMovieDao.deleteFavouriteMovieById(id);
                    }
                })
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

//    private static class DeleteFavouriteMovieByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
//
//        private MovieDao movieDao; // we need this to make db operations
//
//        public DeleteFavouriteMovieByIdAsyncTask(MovieDao movieDao) {
//            this.movieDao = movieDao;
//        }
//
//        @Override
//        protected Void doInBackground(Integer... integers) {
//            Integer idToDelete = integers[0];
//            movieDao.deleteFavouriteMovieById(idToDelete);
//            return null;
//        }
//    }

    public void deleteAllFavouriteMovies() {
//        new DeleteAllFavouriteMoviesAsyncTask(mMovieDao).execute();
        disposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        mMovieDao.deleteAllFavouriteMovies();
                    }
                })
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

//    private static class DeleteAllFavouriteMoviesAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private MovieDao movieDao; // we need this to make db operations
//
//        public DeleteAllFavouriteMoviesAsyncTask(MovieDao movieDao) {
//            this.movieDao = movieDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            movieDao.deleteAllFavouriteMovies();
//            return null;
//        }
//    }

}

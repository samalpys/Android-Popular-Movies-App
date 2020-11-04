package com.example.android.popularmoviesapp.viewmodels;

import android.app.Application;

import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;


public class DiscoverMoviesViewModel extends AndroidViewModel {

    private Repository repository;
    private String sortBy;

    public DiscoverMoviesViewModel(Application application, String sortBy) {
        super(application);
        repository = Repository.getInstance(application);
        this.sortBy = sortBy;
    }

//    public LiveData<MovieResponse> getMovieResponse() {
//        return repository.getMovies(sortBy, 1);
//    }

    public MutableLiveData<List<Movie>> getMoviesLiveData() {
        return repository.getMoviesWithRx(sortBy, 1);
    }

    public void clearCompositeDisposable() {
        repository.clearCompositeDisposable();
    }

    public static class MovieResponseViewModelFactory implements ViewModelProvider.Factory {
        private Application application;
        private String sortBy;

        public MovieResponseViewModelFactory(Application application, String sortBy) {
            this.application = application;
            this.sortBy = sortBy;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DiscoverMoviesViewModel(application, sortBy);
        }
    }
}

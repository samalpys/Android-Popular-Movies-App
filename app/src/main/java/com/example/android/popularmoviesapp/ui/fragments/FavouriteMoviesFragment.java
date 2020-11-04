package com.example.android.popularmoviesapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.databinding.FragmentMoviesListBinding;
import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.ui.activites.DetailsActivity;
import com.example.android.popularmoviesapp.ui.adapters.MovieAdapter;
import com.example.android.popularmoviesapp.viewmodel.FavouriteMovieViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;


public class FavouriteMoviesFragment extends Fragment implements MovieAdapter.OnMovieClickListener {

    private static final int NUMBER_OF_COLUMNS = 3;
    public static final String INTENT_EXTRA_MOVIE_ID = "MOVIE_ID";

    private FragmentMoviesListBinding binding;
    private MovieAdapter mFavourtieMovieAdapter;

    private FavouriteMovieViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.recyclerViewMovies.setLayoutManager(new GridLayoutManager(getActivity(), NUMBER_OF_COLUMNS));
        binding.recyclerViewMovies.setHasFixedSize(true);

        mFavourtieMovieAdapter = new MovieAdapter(getActivity(), null, this);
        binding.recyclerViewMovies.setAdapter(mFavourtieMovieAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // this way ViewModel will be destroyed when Fragment is finished
        viewModel = ViewModelProviders.of(this).get(FavouriteMovieViewModel.class);
        viewModel.getAllFavouriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null && movies.size() != 0) {
                    mFavourtieMovieAdapter.swapData(movies);
                }
            }
        });
    }

    // for MovieAdapter.OnMovieClickListener
    @Override
    public void onClick(long movieId) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.clearCompositeDisposable();
    }
}

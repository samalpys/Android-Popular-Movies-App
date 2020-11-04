package com.example.android.popularmoviesapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.databinding.FragmentMoviesListBinding;
import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.ui.activites.DetailsActivity;
import com.example.android.popularmoviesapp.ui.adapters.MovieAdapter;
import com.example.android.popularmoviesapp.viewmodels.DiscoverMoviesViewModel;
import com.example.android.popularmoviesapp.viewmodels.DiscoverMoviesViewModel.MovieResponseViewModelFactory;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;


public class DiscoverMoviesFragment extends Fragment implements MovieAdapter.OnMovieClickListener {

    FragmentMoviesListBinding binding;

    private static final int NUMBER_OF_COLUMNS = 3;
    public static final String INTENT_EXTRA_MOVIE_ID = "MOVIE_ID";

    private String sortBy;
    private MovieAdapter mMovieAdapter;

    private DiscoverMoviesViewModel viewModel;


    public static DiscoverMoviesFragment newInstance(String sortBy) {
        DiscoverMoviesFragment fragment = new DiscoverMoviesFragment();
        Bundle args = new Bundle();
        args.putString("sortBy", sortBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sortBy = getArguments() != null ?
                getArguments().getString("sortBy", "") : "popularity.desc";
    }

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

        mMovieAdapter = new MovieAdapter(getActivity(),null, this);
        binding.recyclerViewMovies.setAdapter(mMovieAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this, new MovieResponseViewModelFactory(this.getActivity().getApplication(), sortBy)).get(DiscoverMoviesViewModel.class);

        if (sortBy != null) {
            viewModel.getMoviesLiveData().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {
                    if (movies != null && movies.size() != 0) {
                        mMovieAdapter.swapData(movies);
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMovieAdapter != null) {
            mMovieAdapter.setOnMovieClickListenerToNull();
            mMovieAdapter = null;
        }
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
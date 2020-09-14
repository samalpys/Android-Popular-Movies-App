package com.example.android.popularmoviesapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.databinding.FragmentFavouriteMoviesListBinding;
import com.example.android.popularmoviesapp.databinding.FragmentMoviesListBinding;
import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.ui.adapters.FavouriteMovieAdapter;
import com.example.android.popularmoviesapp.viewmodel.FavouriteMovieViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class FavouriteMoviesFragment extends Fragment {

    private static final int NUMBER_OF_COLUMNS = 3;

    FragmentFavouriteMoviesListBinding binding;
    private FavouriteMovieAdapter mFavourtieMovieAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_movies_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(getActivity(), NUMBER_OF_COLUMNS));
        binding.recyclerViewFavouriteMovies.setHasFixedSize(true);

        mFavourtieMovieAdapter = new FavouriteMovieAdapter(getActivity(), null);
        binding.recyclerViewFavouriteMovies.setAdapter(mFavourtieMovieAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // this way ViewModel will be destroyed when Fragment is finished
        final FavouriteMovieViewModel viewModel = ViewModelProviders.of(this).get(FavouriteMovieViewModel.class);
        viewModel.getAllFavouriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null && movies.size() != 0) {
                    System.out.println("HERE: movies = " + movies);
                    mFavourtieMovieAdapter.swapData(movies);
                    System.out.println("HERE: mFavourtieMovieAdapter.getItemCount() = " +mFavourtieMovieAdapter.getItemCount());
                }
            }
        });
    }

}

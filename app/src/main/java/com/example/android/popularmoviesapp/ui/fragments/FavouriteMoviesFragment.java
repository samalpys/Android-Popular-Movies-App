package com.example.android.popularmoviesapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.databinding.FragmentMoviesListBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

public class FavouriteMoviesFragment extends Fragment {

    FragmentMoviesListBinding binding;

    private static final int NUMBER_OF_COLUMNS = 3;


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
    }

}

package com.example.android.popularmoviesapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.data.Repository;
import com.example.android.popularmoviesapp.databinding.FragmentMoviesListBinding;
import com.example.android.popularmoviesapp.ui.activites.DetailsActivity;
import com.example.android.popularmoviesapp.ui.adapters.MovieAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class FavouriteMoviesFragment extends Fragment implements MovieAdapter.OnMovieClickListener {

    private static final int NUMBER_OF_COLUMNS = 3;
    public static final String INTENT_EXTRA_MOVIE_ID = "MOVIE_ID";

    private FragmentMoviesListBinding binding;
    private MovieAdapter mFavourtieMovieAdapter;

    private CompositeDisposable disposable = new CompositeDisposable();


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

        Repository repository = Repository.getInstance(getActivity().getApplication());
        disposable.add(
            repository.observeFavouriteMovies()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                        System.out.println("HERE: movies="+movies);
                        if (movies != null && movies.size() != 0) {
                            mFavourtieMovieAdapter.swapData(movies);
                        }
                    })
        );
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
        disposable.clear();
    }
}

package com.example.android.popularmoviesapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.models.MovieResponse;
import com.example.android.popularmoviesapp.network.RetrofitClient;
import com.example.android.popularmoviesapp.models.Movie;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.ui.activites.DetailsActivity;
import com.example.android.popularmoviesapp.ui.adapters.MovieAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverMoviesFragment extends Fragment
        implements MovieAdapter.OnMovieClickListener {

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    private String sortBy;
    private MovieAdapter mMovieAdapter;
    private static final int NUMBER_OF_COLUMNS = 3;

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
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUMBER_OF_COLUMNS));
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(null, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        Call<MovieResponse> call = RetrofitClient.getInstance().getPopularMovies(
                "9321c4fc5f95b92bce700096da663cde",
                sortBy,
                1
        );

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                MovieResponse movieResponse = response.body();
                if (movieResponse != null) {
                    List<Movie> movies = movieResponse.getMovies();
                    mMovieAdapter.swapData(movies);
                }
            }

            @Override
            public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) { }
        });

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
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("MOVIE", movie);
        startActivity(intent);
    }
}
package com.example.android.popularmoviesapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.model.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopRatedMoviesFragment extends Fragment implements
        MovieAdapter.OnListItemClickListener,
        MoviesQueryTask.OnTaskCompleted {

    @BindView(R.id.rv_top_rated_movies)
    RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;
    private static final int NUMBER_OF_COLUMNS = 2;


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

        MoviesQueryTask topRatedMoviesQueryTask = new MoviesQueryTask(this);
        topRatedMoviesQueryTask.execute("vote_average.desc");
    }

    // for MovieAdapter.OnListItemClickListener callback interface
    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("MOVIE", movie); // made movie parcelable to pass hte object of type Movie using intent
        startActivity(intent);
    }

    // for MoviesQueryTask.OnTaskCompleted callback interface
    @Override
    public void onFetchData(ArrayList<Movie> movies) {
        mMovieAdapter.swapData(movies);
    }
}

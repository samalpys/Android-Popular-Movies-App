package com.example.android.popularmoviesapp.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.ui.activites.DetailsActivity;
import com.example.android.popularmoviesapp.ui.adapters.MovieAdapter;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.model.Movie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteMoviesFragment extends Fragment
        implements MovieAdapter.OnListItemClickListener
    {

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;
    private static final int NUMBER_OF_COLUMNS = 3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUMBER_OF_COLUMNS));
//        mRecyclerView.setHasFixedSize(true);

//        Cursor cursor = queryFavouriteMovies();
//        mMovieAdapter = new MovieAdapter(cursor, true, this);
//        mRecyclerView.setAdapter(mMovieAdapter);
//        mMovieAdapter.swapCursor(queryFavouriteMovies());
//        mMovieAdapter.notifyDataSetChanged();
    }

//    private Cursor queryFavouriteMovies() {
//        String[] projection = {
//                MovieEntry.COLUMN_POSTER_PATH
//        };
//
//        Cursor cursor = getContext().getContentResolver().query(
//                MovieEntry.CONTENT_URI,
//                projection,
//                null,
//                null,
//                null
//        );
//        return cursor;
//    }

    // for MovieAdapter.OnListItemClickListener callback interface
    @Override
    public void onClick(Movie movie) {
        // don't need this here
    }

    @Override
    public void onClick(long id) {
//        Intent favouriteMovieDetailsIntent = new Intent(getActivity(), DetailsActivity.class);
//        Uri uriForMovieClicked = MovieEntry.buildUriWithId(id);
//        favouriteMovieDetailsIntent.setData(uriForMovieClicked);
//        startActivity(favouriteMovieDetailsIntent);
    }
}

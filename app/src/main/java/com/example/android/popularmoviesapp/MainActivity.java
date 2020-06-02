package com.example.android.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MovieAdapter.OnListItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private static int NUMBER_OF_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(null, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        new MovieQueryTask().execute();
    }

    // comes from MovieAdapter.OnListItemClickListener
    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("MOVIE", movie); // made movie parcelable to pass hte object of type Movie using intent
        startActivity(intent);
    }

    private class MovieQueryTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            URL movieUrl = NetworkUtils.buildUrl();

            try {
                String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(movieUrl);
                ArrayList<Movie> moviesData = NetworkUtils.getMoviesFromJson(jsonMoviesResponse);
                return moviesData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> moviesData) {
            super.onPostExecute(moviesData);
            if (moviesData != null) {
                mMovieAdapter.swapData(moviesData);
            }
        }
    }



}

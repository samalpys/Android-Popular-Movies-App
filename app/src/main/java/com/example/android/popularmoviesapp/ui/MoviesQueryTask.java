package com.example.android.popularmoviesapp.ui;

import android.os.AsyncTask;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.network.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MoviesQueryTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private OnTaskCompleted mOnTaskCompletedCallback;

    public interface OnTaskCompleted {
        void onFetchData(ArrayList<Movie> movies);
    }

    public MoviesQueryTask(OnTaskCompleted callback) {
        super();
        this.mOnTaskCompletedCallback = callback;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {
        String sortParam = strings[0];
        URL movieUrl = NetworkUtils.buildUrl(sortParam);

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
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        if (movies != null) {
            // do the callback to update the adapter inside the Fragments
            mOnTaskCompletedCallback.onFetchData(movies);
        }
    }
}

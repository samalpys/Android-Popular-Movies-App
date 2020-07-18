package com.example.android.popularmoviesapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.RetrofitMovieViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener mListener;

    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.mListener = listener;
    }

    public interface OnMovieClickListener {
        public void onClick(Movie movie);
    }

    public void setOnMovieClickListenerToNull() {
        mListener = null;
    }

    @NonNull
    @Override
    public MovieAdapter.RetrofitMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MovieAdapter.RetrofitMovieViewHolder viewHolder = new MovieAdapter.RetrofitMovieViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.RetrofitMovieViewHolder holder, int position) {
        String posterPath = "";

        Movie movie = movies.get(position);

        posterPath = movie.getPosterPath();

        Picasso.get()
                .load(posterPath)
                .resize(486,612)
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return movies!=null ? movies.size() : 0;
    }

    public void swapData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    class RetrofitMovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final ImageView mPoster;

        public RetrofitMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            mPoster = itemView.findViewById(R.id.iv_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onClick(movies.get(position));
        }
    }
}


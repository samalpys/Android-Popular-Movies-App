package com.example.android.popularmoviesapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movies;
    private OnMovieClickListener mListener;

    public MovieAdapter(Context context, List<Movie> movies, OnMovieClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.mListener = listener;
    }

    public interface OnMovieClickListener {
        public void onClick(long movieId);
    }

    public void setOnMovieClickListenerToNull() {
        mListener = null;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        Glide.with(context).load(movie.getPosterPath()).into(holder.mPoster);
        holder.mOriginalTitle.setText(movie.getOriginalTitle());
        holder.mVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return movies!=null ? movies.size() : 0;
    }

    public void swapData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mPoster;
        private final TextView mOriginalTitle;
        private final TextView mVoteAverage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.iv_thumbnail);
            mOriginalTitle = itemView.findViewById(R.id.tv_original_title);
            mVoteAverage = itemView.findViewById(R.id.tv_vote_average);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            long movieId = movies.get(position).getId();
            mListener.onClick(movieId);
        }
    }
}


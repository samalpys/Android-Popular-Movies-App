package com.example.android.popularmoviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoiveViewHolder> {

    private ArrayList<Movie> movies;
    private OnListItemClickListener mListener;

    public interface OnListItemClickListener {
        void onClick(Movie movie);
    }


    public MovieAdapter(ArrayList<Movie> movies, OnListItemClickListener listener) {
        this.movies = movies;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MoiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MoiveViewHolder viewHolder = new MoiveViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoiveViewHolder holder, int position) {
        Movie movie = movies.get(position);

        String posterPath = movie.getPosterPath();
        Picasso.get()
                .load(posterPath)
                .resize(486,612)
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.size();
        }
        return 0;
    }

    public void swapData(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class MoiveViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mPoster;

        public MoiveViewHolder(@NonNull View itemView) {
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

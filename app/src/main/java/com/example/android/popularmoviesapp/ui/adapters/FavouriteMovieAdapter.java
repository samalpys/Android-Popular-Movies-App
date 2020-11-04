package com.example.android.popularmoviesapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.data.models.Movie;

import java.util.List;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieViewHolder> {

    private Context context;
    private List<Movie> favouriteMovies;

    public FavouriteMovieAdapter(Context context, List<Movie> favouriteMovies) {
        this.context = context;
        this.favouriteMovies = favouriteMovies;
    }

    @NonNull
    @Override
    public FavouriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        FavouriteMovieViewHolder viewHolder = new FavouriteMovieViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMovieViewHolder holder, int position) {
        Movie movie = favouriteMovies.get(position);

        Glide.with(context).load(movie.getPosterPath()).into(holder.mPoster);
        holder.mOriginalTitle.setText(movie.getOriginalTitle());
        holder.mVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return favouriteMovies!=null ? favouriteMovies.size() : 0;
    }

    public void swapData(List<Movie> favouriteMovies) {
        this.favouriteMovies = favouriteMovies;
        notifyDataSetChanged();
    }


    class FavouriteMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mPoster;
        private final TextView mOriginalTitle;
        private final TextView mVoteAverage;

        public FavouriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            mPoster = itemView.findViewById(R.id.iv_thumbnail);
            mOriginalTitle = itemView.findViewById(R.id.tv_original_title);
            mVoteAverage = itemView.findViewById(R.id.tv_vote_average);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

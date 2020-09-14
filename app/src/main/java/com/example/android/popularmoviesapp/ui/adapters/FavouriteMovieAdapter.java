package com.example.android.popularmoviesapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.model.Movie;

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
                .inflate(R.layout.favourite_list_item, parent, false);
        FavouriteMovieViewHolder viewHolder = new FavouriteMovieViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMovieViewHolder holder, int position) {
        String posterPath = "";
        Movie movie = favouriteMovies.get(position);
        posterPath = movie.getPosterPath();
//        Picasso.get()
//                .load(posterPath)
//                .resize(486,612)
//                .into(holder.mPoster);
        Glide.with(context).load(posterPath).into(holder.mPoster);
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

        public FavouriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            mPoster = itemView.findViewById(R.id.iv_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

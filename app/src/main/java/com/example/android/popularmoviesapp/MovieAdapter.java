package com.example.android.popularmoviesapp;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoiveViewHolder> {

    private ArrayList<Movie> movies;
    private OnListItemClickListener mListener;
    private Cursor mCursor;
    private boolean isCursorData;

    public interface OnListItemClickListener {
        void onClick(Movie movie);
    }


    public MovieAdapter(ArrayList<Movie> movies, OnListItemClickListener listener) {
        this.movies = movies;
        this.mListener = listener;
    }

    public MovieAdapter(Cursor cursor, boolean isCursorData, OnListItemClickListener listener) {
        this.isCursorData = true;
        this.mCursor = cursor;
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
        long id = -1;
        String posterPath = "";

        if (!isCursorData) {
            Movie movie = movies.get(position);
            posterPath = movie.getPosterPath();
            id = movie.getId();
        } else {
            if (mCursor.moveToPosition(position)) {
                posterPath = mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH));
                id = mCursor.getLong(mCursor.getColumnIndex(MovieEntry.COLUMN_ID));
            }
        }
        if (id != -1) holder.mItem.setId((int) id);
        Picasso.get()
                .load(posterPath)
                .resize(486,612)
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        if (!isCursorData) {
            if (movies != null) return movies.size();
        } else {
            if (mCursor != null) return mCursor.getCount();
        }
        return 0;
    }

    public void swapData(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        this.mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class MoiveViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mPoster;
        private final CardView mItem;

        public MoiveViewHolder(@NonNull View itemView) {
            super(itemView);

            mItem = itemView.findViewById(R.id.item);
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

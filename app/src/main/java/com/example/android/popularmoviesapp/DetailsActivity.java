package com.example.android.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesapp.data.MovieContract.MovieEntry;
import com.example.android.popularmoviesapp.model.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    // data binding using ButterKnife
    @BindView(R.id.iv_poster)
    ImageView mPoster;
    @BindView(R.id.iv_header_poster)
    ImageView mHeaderPoster;
    @BindView(R.id.tv_original_title_content)
    TextView mOriginalTitle;
    @BindView(R.id.tv_release_date_content)
    TextView mReleaseDate;
    @BindView(R.id.tv_vote_average_content)
    TextView mVoteAverage;
    @BindView(R.id.tv_overview_content)
    TextView mOverview;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final Movie movie = intent.getParcelableExtra("MOVIE");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(movie.getOriginalTitle());
        }

        Picasso.get().load(movie.getBackdropPath()).into(mHeaderPoster);
        Picasso.get().load(movie.getPosterPath()).resize(278,350).into(mPoster);
        mOriginalTitle.setText(movie.getOriginalTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mVoteAverage.setText(movie.getVoteAverage() + "/10");
        mOverview.setText(movie.getOverview());

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MovieEntry.COLUMN_ID, movie.getId());
                values.put(MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
                values.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                values.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
                values.put(MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
                values.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                values.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());

                getContentResolver().insert(MovieEntry.CONTENT_URI, values);
            }
        });
    }
}

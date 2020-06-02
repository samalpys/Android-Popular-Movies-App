package com.example.android.popularmoviesapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        Movie movie = intent.getParcelableExtra("MOVIE");

        Picasso.get().load(movie.getPosterPath()).resize(278,350).into(mPoster);
        Picasso.get().load(movie.getPosterPath()).resize(278,350).into(mHeaderPoster);
        mOriginalTitle.setText(movie.getOriginalTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mVoteAverage.setText(movie.getVoteAverage() + "/10");
        mOverview.setText(movie.getOverview());
    }
}

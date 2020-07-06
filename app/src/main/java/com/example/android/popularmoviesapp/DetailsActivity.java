package com.example.android.popularmoviesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.data.MovieContract.MovieEntry;
import com.example.android.popularmoviesapp.model.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

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
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.hasExtra("MOVIE")) {

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

                    Uri uri = getContentResolver().insert(MovieEntry.CONTENT_URI, values);
                    if (uri != null) {
                        Toast.makeText(DetailsActivity.this, movie.getOriginalTitle() + " was added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            mUri = intent.getData();
            if (mUri == null) throw new NullPointerException("URI for DetailActivity cannot be null");
            getSupportLoaderManager().initLoader(1, null, this);

        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                MovieEntry.COLUMN_ID,
                MovieEntry.COLUMN_ORIGINAL_TITLE,
                MovieEntry.COLUMN_OVERVIEW,
                MovieEntry.COLUMN_POSTER_PATH,
                MovieEntry.COLUMN_BACKDROP_PATH,
                MovieEntry.COLUMN_RELEASE_DATE,
                MovieEntry.COLUMN_VOTE_AVERAGE
        };

        return new CursorLoader(
                this,
                mUri,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {

            String backdropPath = cursor.getColumnName(cursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP_PATH));
            String posterPath = cursor.getColumnName(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH));
            String originalTitle = cursor.getColumnName(cursor.getColumnIndex(MovieEntry.COLUMN_ORIGINAL_TITLE));
            String releaseDate = cursor.getColumnName(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE));
            String voteAverage = cursor.getColumnName(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE));
            String overview = cursor.getColumnName(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW));

            Picasso.get().load(backdropPath).into(mHeaderPoster);
            Picasso.get().load(posterPath).resize(278,350).into(mPoster);
            mOriginalTitle.setText(originalTitle);
            mReleaseDate.setText(releaseDate);
            mVoteAverage.setText(voteAverage + "/10");
            mOverview.setText(overview);
        }
     }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) { }
}

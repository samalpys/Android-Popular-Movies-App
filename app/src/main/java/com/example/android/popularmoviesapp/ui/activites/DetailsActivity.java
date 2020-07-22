package com.example.android.popularmoviesapp.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.viewmodel.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmoviesapp.ui.fragments.DiscoverMoviesFragment.INTENT_EXTRA_MOVIE_ID;

public class DetailsActivity extends AppCompatActivity {

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
        if (intent.hasExtra(INTENT_EXTRA_MOVIE_ID)) {

            long movieId = intent.getLongExtra(INTENT_EXTRA_MOVIE_ID, -1);
            if (movieId == -1) {
                Toast.makeText(this, "Sorry, error has occurred!", Toast.LENGTH_SHORT).show();
                this.onBackPressed();
            }

            final MovieViewModel viewModel = ViewModelProviders.of(this, new MovieViewModel.MovieViewModelFactory(this.getApplication(), movieId)).get(MovieViewModel.class);
            viewModel.getMovieDetailsObservable().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(Movie movie) {

                    if (movie != null) {

                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setTitle(movie.getTitle());
                        }

                        Picasso.get().load(movie.getBackdropPath()).into(mHeaderPoster);
                        Picasso.get().load(movie.getPosterPath()).resize(278, 350).into(mPoster);
                        mOriginalTitle.setText(movie.getTitle());
                        mReleaseDate.setText(movie.getReleaseDate());
                        mVoteAverage.setText(movie.getVoteAverage() + "/10");
                        mOverview.setText(movie.getOverview());

                        mFab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(DetailsActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        System.out.println("HERE: movie is null");
                    }
                }
            });
        }
    }
}

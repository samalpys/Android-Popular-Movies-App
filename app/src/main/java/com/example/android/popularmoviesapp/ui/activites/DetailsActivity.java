package com.example.android.popularmoviesapp.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.databinding.ActivityDetailsBinding;
import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.viewmodels.FavouriteMoviesViewModel;
import com.example.android.popularmoviesapp.viewmodels.MovieDetailsViewModel;


import static com.example.android.popularmoviesapp.ui.fragments.DiscoverMoviesFragment.INTENT_EXTRA_MOVIE_ID;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    private long movieId;

    private MovieDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_EXTRA_MOVIE_ID)) {

            this.movieId = intent.getLongExtra(INTENT_EXTRA_MOVIE_ID, -1);
            if (movieId == -1) {
                Toast.makeText(this, "Sorry, error has occurred!", Toast.LENGTH_SHORT).show();
                this.onBackPressed();
            }

            viewModel = ViewModelProviders.of(this, new MovieDetailsViewModel.MovieViewModelFactory(this.getApplication(), movieId)).get(MovieDetailsViewModel.class);
            viewModel.getMovieDetailsWithRx().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(Movie movie) {
                    if (movie != null) {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setTitle(movie.getTitle());
                        }

                        Glide.with(DetailsActivity.this).load(movie.getBackdropPath()).into(binding.ivHeaderPoster);
                        Glide.with(DetailsActivity.this).load(movie.getPosterPath()).into(binding.extraDetails.ivPoster);
                        binding.extraDetails.tvOriginalTitle.setText(movie.getTitle());
                        binding.extraDetails.tvReleaseDate.setText(movie.getReleaseDate());
                        binding.extraDetails.tvVoteAverage.setText(movie.getVoteAverage() + "/10");
                        binding.extraDetails.tvOverview.setText(movie.getOverview());
                        binding.extraDetails.tvLanguage.setText(movie.getOriginalLanguage());
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                viewModel.getMovieDetailsWithRx().observe(this, new Observer<Movie>() {
                    @Override
                    public void onChanged(Movie movie) {
                        if (movie != null) {
                            final FavouriteMoviesViewModel favouriteViewModel = ViewModelProviders.of(DetailsActivity.this).get(FavouriteMoviesViewModel.class);
                            favouriteViewModel.insertFavouriteMovie(movie);
                        }
                    }
                });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

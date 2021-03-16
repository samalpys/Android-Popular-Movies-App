package com.example.android.popularmoviesapp.ui.activites;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.data.Repository;
import com.example.android.popularmoviesapp.databinding.ActivityDetailsBinding;
import com.example.android.popularmoviesapp.data.models.Movie;
import com.example.android.popularmoviesapp.utils.Constants;


import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static com.example.android.popularmoviesapp.ui.fragments.DiscoverMoviesFragment.INTENT_EXTRA_MOVIE_ID;

public class DetailsActivity extends DaggerAppCompatActivity {

    ActivityDetailsBinding binding;
    private Movie movie;

    private Repository repository = Repository.getInstance(getApplication());
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_EXTRA_MOVIE_ID)) {

            long movieId = intent.getLongExtra(INTENT_EXTRA_MOVIE_ID, -1);
            if (movieId == -1) {
                Toast.makeText(this, "Sorry, error has occurred!", Toast.LENGTH_SHORT).show();
                this.onBackPressed();
            }

            disposable.add(
                repository.observeMovieDetails(movieId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(movie -> {
                            if (movie != null) {
                                this.movie = movie;
                                updateUI();
                            }
                        })
            );
        }
    }

    private void updateUI() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(movie.getTitle());
        }

        Glide.with(DetailsActivity.this).load(Constants.BACKDROP_URL + movie.getBackdropPath()).into(binding.ivHeaderPoster);
        Glide.with(DetailsActivity.this).load(Constants.IMAGE_URL + movie.getPosterPath()).into(binding.extraDetails.ivPoster);
        binding.extraDetails.tvOriginalTitle.setText(movie.getTitle());
        binding.extraDetails.tvReleaseDate.setText(movie.getReleaseDate());
        binding.extraDetails.tvVoteAverage.setText(movie.getVoteAverage() + "/10");
        binding.extraDetails.tvOverview.setText(movie.getOverview());
        binding.extraDetails.tvLanguage.setText(movie.getOriginalLanguage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            Repository repository = Repository.getInstance(getApplication());
            disposable.add(
                    repository.observeInsertFavouriteMovie(movie)
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe()
            );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

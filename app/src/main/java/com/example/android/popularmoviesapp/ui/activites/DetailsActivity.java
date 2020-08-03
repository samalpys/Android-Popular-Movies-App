package com.example.android.popularmoviesapp.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.databinding.ActivityDetailsBinding;
import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmoviesapp.ui.fragments.DiscoverMoviesFragment.INTENT_EXTRA_MOVIE_ID;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

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

            final MovieViewModel viewModel = ViewModelProviders.of(this, new MovieViewModel.MovieViewModelFactory(this.getApplication(), movieId)).get(MovieViewModel.class);
            viewModel.getMovieDetailsObservable().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(Movie movie) {

                    if (movie != null) {

                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setTitle(movie.getTitle());
                        }

                        Picasso.get().load(movie.getBackdropPath()).into(binding.ivHeaderPoster);
                        Picasso.get().load(movie.getPosterPath()).resize(278, 350).into(binding.extraDetails.ivPoster);
                        binding.extraDetails.tvOriginalTitleContent.setText(movie.getTitle());
                        binding.extraDetails.tvReleaseDateContent.setText(movie.getReleaseDate());
                        binding.extraDetails.tvVoteAverageContent.setText(movie.getVoteAverage() + "/10");
                        binding.extraDetails.tvOverviewContent.setText(movie.getOverview());
                        binding.fab.setOnClickListener(new View.OnClickListener() {
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

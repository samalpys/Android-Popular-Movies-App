package com.example.android.popularmoviesapp.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.databinding.ActivityMainBinding;
import com.example.android.popularmoviesapp.ui.fragments.DiscoverMoviesFragment;
import com.example.android.popularmoviesapp.ui.fragments.FavouriteMoviesFragment;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        replaceByDiscoverMoviesFragment("popularity.desc");

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.action_trending:
                    replaceByDiscoverMoviesFragment("popularity.desc");
                    return true;
                case R.id.action_favourite:
                    replaceByFavouriteMoviesFragment();
                    return true;
                default:
                    return false;
            }
        });
    }

    private void replaceByDiscoverMoviesFragment(String sortBy) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, DiscoverMoviesFragment.newInstance(sortBy))
                .commit();
    }

    private void replaceByFavouriteMoviesFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, new FavouriteMoviesFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_popular_movies:
                replaceByDiscoverMoviesFragment("popularity.desc");
                return true;
            case R.id.action_top_rated:
                replaceByDiscoverMoviesFragment("vote_average.desc");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

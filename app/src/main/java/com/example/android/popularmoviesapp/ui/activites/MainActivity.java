package com.example.android.popularmoviesapp.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.ui.fragments.FavouriteMoviesFragment;
import com.example.android.popularmoviesapp.ui.fragments.PopularMoviesFragment;
import com.example.android.popularmoviesapp.ui.fragments.TopRatedMoviesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new PopularMoviesFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.action_trending:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new PopularMoviesFragment())
                            .commit();
                    return true;
                case R.id.action_favourite:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new FavouriteMoviesFragment())
                            .commit();
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_popular_movies:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PopularMoviesFragment())
                        .commit();
                return true;
            case R.id.action_top_rated:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TopRatedMoviesFragment())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

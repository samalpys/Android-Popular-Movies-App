package com.example.android.popularmoviesapp;

import com.example.android.popularmoviesapp.fragments.FavouriteMoviesFragment;
import com.example.android.popularmoviesapp.fragments.PopularMoviesFragment;
import com.example.android.popularmoviesapp.fragments.TopRatedMoviesFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MoviePagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 3;

    public MoviePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularMoviesFragment();
        } else if (position == 1) {
            return new TopRatedMoviesFragment();
        } else {
            return new FavouriteMoviesFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Popular";
        } else if (position == 1) {
            return "Top Rated";
        } else {
            return "Favourite";
        }
    }
}

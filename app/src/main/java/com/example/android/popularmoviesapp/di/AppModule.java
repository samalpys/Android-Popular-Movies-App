package com.example.android.popularmoviesapp.di;

import dagger.Module;
import dagger.Provides;

// Application level dependencies for the project

@Module
public class AppModule {

    @Provides
    static String someString() {
        return "this is a test String";
    }
}

package com.example.android.popularmoviesapp.network;

import android.app.Application;
import android.os.AsyncTask;

import com.example.android.popularmoviesapp.database.MovieDao;
import com.example.android.popularmoviesapp.database.MovieDatabase;
import com.example.android.popularmoviesapp.model.Movie;
import com.example.android.popularmoviesapp.model.MovieResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Repository {

    // Retrofit
    private final static String BASE_URL = "https://api.themoviedb.org/3/";
    private RetrofitService retrofitService;
    private static Repository repositoryInstance;

    // Room
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllFavouriteMovies;

    private Repository(Application application) {

        // for Retrofit
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(new AuthInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        retrofitService = retrofit.create(RetrofitService.class);

        // for Room
        MovieDatabase db = MovieDatabase.getInstance(application);
        mMovieDao = db.movieDao();
        mAllFavouriteMovies = mMovieDao.getAllFavouriteMovies();
    }

    public synchronized static Repository getInstance(Application application) {
        if (repositoryInstance == null) {
            repositoryInstance = new Repository(application);
        }
        return repositoryInstance;
    }

    // for Retrofit

    public LiveData<MovieResponse> getMovies(String sortBy, int page) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        retrofitService.getMovies(sortBy, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) { }
        });

        return data;
    }

    public LiveData<Movie> getMovieDetails(long id) {
        final MutableLiveData<Movie> data = new MutableLiveData<>();

        retrofitService.getMovieDetails(id).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) { }
        });

        return data;
    }


    class AuthInterceptor implements Interceptor {

        @NotNull
        @Override
        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("api_key", "9321c4fc5f95b92bce700096da663cde")
                    .build();

            request = request.newBuilder().url(url).build();

            return chain.proceed(request);
        }
    }


    // for Room

    public LiveData<List<Movie>> getAllFavouriteMovies() {
        return mAllFavouriteMovies;
    }

    public void insertFavouriteMovie(Movie movie) {
        new InsertFavouriteMovieAsyncTask(mMovieDao).execute(movie);
    }

    private static class InsertFavouriteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao; // we need this to make db operations

        public InsertFavouriteMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            Movie movieToInsert = movies[0];
            movieDao.insertFavouriteMovie(movieToInsert);
            return null;
        }
    }

    public void deleteFavouriteMovieById(int id) {
        new DeleteFavouriteMovieByIdAsyncTask(mMovieDao).execute(id);
    }

    private static class DeleteFavouriteMovieByIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private MovieDao movieDao; // we need this to make db operations

        public DeleteFavouriteMovieByIdAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            Integer idToDelete = integers[0];
            movieDao.deleteFavouriteMovieById(idToDelete);
            return null;
        }
    }

    public void deleteAllFavouriteMovies() {
        new DeleteAllFavouriteMoviesAsyncTask(mMovieDao).execute();
    }

    private static class DeleteAllFavouriteMoviesAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDao movieDao; // we need this to make db operations

        public DeleteAllFavouriteMoviesAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllFavouriteMovies();
            return null;
        }
    }

}

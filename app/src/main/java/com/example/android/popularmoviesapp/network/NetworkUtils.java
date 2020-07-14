package com.example.android.popularmoviesapp.network;

import android.net.Uri;

import com.example.android.popularmoviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class NetworkUtils {

    private static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";

    private static final String PARAM_SORT = "sort_by";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_PAGE = "page";

    private static final String API_KEY = "9321c4fc5f95b92bce700096da663cde";
    private static final String PAGE = "1";

    private static final String TAG_RESULTS = "results";
    private static final String TAG_ID = "id";
    private static final String TAG_ORIGINAL_TITLE = "original_title";
    private static final String TAG_POSTER_PATH = "poster_path";
    private static final String TAG_BACKDROP_PATH = "backdrop_path";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_VOTE_AVERAGE = "vote_average";
    private static final String TAG_RELEASE_DATE = "release_date";


    public static URL buildUrl(String sortParam) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SORT, sortParam)
                .appendQueryParameter(PARAM_PAGE, PAGE)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        InputStream in = null;
        HttpURLConnection urlConnection = null;
        String jsonResponse = "";

        try {
            // 1. Declare a URL Connection
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");

            // 2. Open InputStream to connection
            urlConnection.connect(); // Open communications link (network traffic occurs here).

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = urlConnection.getInputStream(); // Retrieve the response body as an InputStream.

                if (in != null) {
                    // 3. Download and decode the string response using builder
                    StringBuilder output = new StringBuilder();
                    InputStreamReader inReader = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(inReader);

                    String line = reader.readLine();
                    while (line != null) {
                        output.append(line);
                        line = reader.readLine();
                    }
                    jsonResponse = output.toString();
                }
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    public static ArrayList<Movie> getMoviesFromJson(String jsonData) throws JSONException {

        JSONObject moviesJsonObject = new JSONObject(jsonData);
        JSONArray resultsArray = moviesJsonObject.getJSONArray(TAG_RESULTS);

        ArrayList<Movie> movies = new ArrayList<>();

        for (int i=0; i<resultsArray.length(); i++) {
            JSONObject movieInfoObject = resultsArray.getJSONObject(i);

            long id = movieInfoObject.getLong(TAG_ID);
            String originalTitle = movieInfoObject.getString(TAG_ORIGINAL_TITLE);
            String overview = movieInfoObject.getString(TAG_OVERVIEW);
            String posterPath = movieInfoObject.getString(TAG_POSTER_PATH);
            String backdropPath = movieInfoObject.getString(TAG_BACKDROP_PATH);
            String releaseDate = movieInfoObject.getString(TAG_RELEASE_DATE);
            double voteAverage = movieInfoObject.getDouble(TAG_VOTE_AVERAGE);

            movies.add(new Movie(id, originalTitle, overview, posterPath, backdropPath, releaseDate, voteAverage));
        }

        return movies;
    }

}

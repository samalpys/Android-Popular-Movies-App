package com.example.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String originalTitle;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;

    private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

    public Movie() { }

    public Movie(String originalTitle, String overview, String posterPath, String releaseDate, double voteAverage) {
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return IMAGE_BASE_URL + posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }


    /* --- Methods for Parcelable ---  */

    protected Movie(Parcel in) {
        originalTitle = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

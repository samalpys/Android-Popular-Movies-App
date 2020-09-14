package com.example.android.popularmoviesapp.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie_table")
public class Movie {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE = "w342";
    public static final String BACKDROP_SIZE = "w780";

//    @PrimaryKey(autoGenerate = true)
//    private int databaseId;

    @PrimaryKey(autoGenerate = false)
    private Integer id;

    private Double popularity;

    @SerializedName("vote_count")
    private Double voteCount;

    @SerializedName("video")
    private Boolean isVideo;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("adult")
    private Boolean isAdult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

//    @SerializedName("genre_ids")
//    private List<Integer> genreIds;

    private String title;

    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie() {
    }

    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie(Integer id, String posterPath, String backdropPath, String originalLanguage, String title, Double voteAverage, String overview, String releaseDate) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Double getVoteCount() {
        return voteCount;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public String getPosterPath() {
        return IMAGE_BASE_URL + POSTER_SIZE + "/" + posterPath;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public String getBackdropPath() {
        return IMAGE_BASE_URL + BACKDROP_SIZE + "/" + backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

//    public List<Integer> getGenreIds() {
//        return genreIds;
//    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdult(Boolean adult) {
        isAdult = adult;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setVoteCount(Double voteCount) {
        this.voteCount = voteCount;
    }

    public void setVideo(Boolean video) {
        isVideo = video;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

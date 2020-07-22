package com.example.android.popularmoviesapp.model;


import com.google.gson.annotations.SerializedName;

public class Movie {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

    private Double popularity;

    @SerializedName("vote_count")
    private Double voteCount;

    @SerializedName("video")
    private Boolean isVideo;

    @SerializedName("poster_path")
    private String posterPath;

    private Integer id;

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

    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

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
        return IMAGE_BASE_URL + "/" + posterPath;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public String getBackdropPath() {
        return IMAGE_BASE_URL + "/" + backdropPath;
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
}

package com.example.android.popularmoviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {

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


    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readDouble();
        }
        byte tmpIsVideo = in.readByte();
        isVideo = tmpIsVideo == 0 ? null : tmpIsVideo == 1;
        posterPath = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpIsAdult = in.readByte();
        isAdult = tmpIsAdult == 0 ? null : tmpIsAdult == 1;
        backdropPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        overview = in.readString();
        releaseDate = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(popularity);
        }
        if (voteCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteCount);
        }
        dest.writeByte((byte) (isVideo == null ? 0 : isVideo ? 1 : 2));
        dest.writeString(posterPath);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeByte((byte) (isAdult == null ? 0 : isAdult ? 1 : 2));
        dest.writeString(backdropPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(title);
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverage);
        }
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }
}

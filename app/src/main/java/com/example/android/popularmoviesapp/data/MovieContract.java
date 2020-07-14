//package com.example.android.popularmoviesapp.data;
//
//import android.content.ContentResolver;
//import android.net.Uri;
//import android.provider.BaseColumns;
//
//public class MovieContract {
//
//    private MovieContract() { }
//
//    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesapp";
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
//    public static final String PATH_MOVIES = "movies";
//
//
//    public static class MovieEntry implements BaseColumns {
//
//        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);
//
//        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
//        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
//
//        public static final String TABLE_NAME = "favourite_movies";
//        public static final String COLUMN_ID = "id";
//        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
//        public static final String COLUMN_OVERVIEW = "overview";
//        public static final String COLUMN_POSTER_PATH = "poster_path";
//        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
//        public static final String COLUMN_RELEASE_DATE = "release_date";
//        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
//
//        public static Uri buildUriWithId(long id) {
//            return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
//        }
//
//    }
//}

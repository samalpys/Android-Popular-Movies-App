//package com.example.android.popularmoviesapp.data;
//
//import android.content.ContentProvider;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.util.Log;
//
//import com.example.android.popularmoviesapp.data.MovieContract.MovieEntry;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//public class MovieProvider extends ContentProvider {
//
//    private static final String LOG_TAG = MovieProvider.class.getSimpleName();
//
//    private static final int MOVIES = 100;
//    private static final int MOVIE_ID = 101;
//
//    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//    static {
//        // content://com.example.android.popularmoviesapp/movies
//        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
//
//        // content://com.example.android.popularmoviesapp/movies/#
//        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_ID);
//    }
//
//    private MovieDbHelper mDbHelper;
//
//    @Override
//    public boolean onCreate() {
//        mDbHelper = new MovieDbHelper(getContext());
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//        Cursor cursor;
//
//        switch(sUriMatcher.match(uri)) {
//            case MOVIES:
//                cursor = db.query(
//                        MovieEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder
//                );
//                break;
//            case MOVIE_ID:
//                long id = ContentUris.parseId(uri);
//                selection = MovieEntry._ID + "=?";
//                selectionArgs = new String[] { String.valueOf(id) };
//                cursor = db.query(
//                        MovieEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder
//                );
//                break;
//            default:
//                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
//        }
//        cursor.setNotificationUri(getContext().getContentResolver(), uri);
//        return cursor;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        switch (sUriMatcher.match(uri)) {
//            case MOVIES:
//                return MovieEntry.CONTENT_LIST_TYPE;
//            case MOVIE_ID:
//                return MovieEntry.CONTENT_ITEM_TYPE;
//            default:
//                throw new IllegalStateException("Unknown URI " + uri + " with match " + sUriMatcher.match(uri));
//        }
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//        long id;
//
//        switch (sUriMatcher.match(uri)) {
//            case MOVIES:
//                // TODO: perform the check of values before inserting them into db
//                id = db.insert(
//                        MovieEntry.TABLE_NAME,
//                        null,
//                        values
//                );
//                if (id == -1) {
//                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
//                    return null;
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("Insertion is not supported for " + uri);
//        }
//
//        getContext().getContentResolver().notifyChange(uri, null);
//        return ContentUris.withAppendedId(uri, id);
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//        int rowsDeleted = 0;
//
//        switch (sUriMatcher.match(uri)) {
//            case MOVIES:
//                // delete all rows
//                rowsDeleted = db.delete(
//                        MovieEntry.TABLE_NAME,
//                        selection,
//                        selectionArgs
//                );
//                break;
//            case MOVIE_ID:
//                long id = ContentUris.parseId(uri);
//                selection = MovieEntry._ID + "=?";
//                selectionArgs = new String[] { String.valueOf(id) };
//                rowsDeleted = db.delete(
//                        MovieEntry.TABLE_NAME,
//                        selection,
//                        selectionArgs
//                );
//                break;
//            default:
//                throw new IllegalArgumentException("Deletion is not supported for " + uri);
//        }
//
//        if (rowsDeleted != 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//
//        return rowsDeleted;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//        int rowsUpdated = 0;
//        if (values.size() == 0) return 0;
//
//        switch (sUriMatcher.match(uri)) {
//            case MOVIES:
//                // TODO: perform the check of values before inserting them into db
//                rowsUpdated = db.update(
//                        MovieEntry.TABLE_NAME,
//                        values,
//                        selection,
//                        selectionArgs
//                );
//                break;
//            case MOVIE_ID:
//                // TODO: perform the check of values before inserting them into db
//                long id = ContentUris.parseId(uri);
//                selection = MovieEntry._ID + "=?";
//                selectionArgs = new String[] { String.valueOf(id) };
//                rowsUpdated = db.update(
//                        MovieEntry.TABLE_NAME,
//                        values,
//                        selection,
//                        selectionArgs
//                );
//                break;
//            default:
//                throw new IllegalArgumentException("Update is not supported for " + uri);
//        }
//        if (rowsUpdated != 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//        return rowsUpdated;
//    }
//}

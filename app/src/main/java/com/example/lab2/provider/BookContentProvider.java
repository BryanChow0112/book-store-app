package com.example.lab2.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

// Content Providers makes your application a provider of data
// that is served by Room Database or any other interfaces
public class BookContentProvider extends ContentProvider {

    // URL authority is a unique name that identifies the content provider
    public static final String CONTENT_AUTHORITY = "fit2081.app.bryan";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final int MULTIPLE_ROWS_TASKS = 1;
    private static final int SINGLE_ROW_TASKS = 2;
    private static final int MULTIPLE_ROWS_USERS = 3;
    private static final int SINGLE_ROW_USERS = 4;
    BookDatabase db;

    private static final UriMatcher sUriMatcher = createUriMatcher();


    public BookContentProvider() {
    }

    private static UriMatcher createUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        //sUriMatcher will return code 1 if uri like authority/tasks
        uriMatcher.addURI(authority, Book.TABLE_NAME, MULTIPLE_ROWS_TASKS);

        //sUriMatcher will return code 2 if uri like e.g. authority/tasks/7 (where 7 is id of row in tasks table)
        uriMatcher.addURI(authority, Book.TABLE_NAME + "/#", SINGLE_ROW_TASKS);

        //sUriMatcher will return code 1 if uri like authority/users
        uriMatcher.addURI(authority, "users", MULTIPLE_ROWS_USERS);

        //sUriMatcher will return code 2 if uri like e.g. authority/users/7 (where 7 is id of row in users table)
        uriMatcher.addURI(authority, "users" + "/#", SINGLE_ROW_USERS);

        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        int deletionCount;

        deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(Book.TABLE_NAME, selection, selectionArgs);

        return deletionCount;
    }

    @Override
    public String getType(Uri uri) {
        // Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Implement this to handle requests to insert a new row.

        long rowId = db
                .getOpenHelper() // OpenHelper library for Android to access database
                .getWritableDatabase() // for writing purpose
                .insert(Book.TABLE_NAME, 0, values);

        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public boolean onCreate() {
        // Initialize the provider

        db = BookDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Implement this to handle query requests from clients.
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Book.TABLE_NAME);
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        final Cursor cursor = db
                .getOpenHelper()
                .getReadableDatabase() // for read purposes
                .query(query);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // Implement this to handle requests to update one or more rows.

        int updateCount;
        updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(Book.TABLE_NAME, 0, values, selection, selectionArgs);

        return updateCount;
    }
}
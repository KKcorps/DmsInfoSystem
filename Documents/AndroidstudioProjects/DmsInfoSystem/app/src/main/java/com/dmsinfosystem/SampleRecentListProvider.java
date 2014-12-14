package com.dmsinfosystem;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by root on 11/12/14.
 */

public class SampleRecentListProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = SampleRecentListProvider.class.getName();
    public static final int MODE = DATABASE_MODE_QUERIES;
    public SampleRecentListProvider() {
        //setupList(AUTHORITY, MODE);
        setupSuggestions(AUTHORITY, MODE);
    }

    /*public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (selectionArgs != null && selectionArgs.length > 0 && selectionArgs[0].length() > 0) {
        // the entered text can be found in selectionArgs[0]
        // return a cursor with applicationropriate data
        } else {
        // user hasn't entered anything
        // thus return a default cursor
            }
        }*/
    }

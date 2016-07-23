package com.bobyk.channels;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by bobyk on 23/07/16.
 */
public class ChannelProvider extends ContentProvider {

    private static final int CHANNEL = 100;
    private static final int CHANNEL_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private ChannelDBHelper channelDBHelper;

    @Override
    public boolean onCreate() {
        channelDBHelper = new ChannelDBHelper(getContext());
        return true;
    }

    public static UriMatcher buildUriMatcher(){
        String content = ChannelContract.CONTENT_AUTHORITY;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, ChannelContract.PATH_CHANNEL, CHANNEL);
        matcher.addURI(content, ChannelContract.PATH_CHANNEL + "/#", CHANNEL_ID);

        return matcher;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase database = channelDBHelper.getWritableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case CHANNEL:
                cursor = database.query(
                        ChannelContract.ChannelEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CHANNEL_ID:
                long _id = ContentUris.parseId(uri);
                cursor = database.query(
                        ChannelContract.ChannelEntry.TABLE_NAME,
                        projection,
                        ChannelContract.ChannelEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder

                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case CHANNEL:
                return ChannelContract.ChannelEntry.CONTENT_TYPE;
            case CHANNEL_ID:
                return ChannelContract.ChannelEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase database = channelDBHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch (uriMatcher.match(uri)){
            case CHANNEL:
                _id = database.insert(ChannelContract.ChannelEntry.TABLE_NAME, null, contentValues);
                if (_id > 0){
                    returnUri = ChannelContract.ChannelEntry.buildChannelUri(_id);
                }
                else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = channelDBHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)){
            case CHANNEL:
                rows = database.delete(ChannelContract.ChannelEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = channelDBHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)){
            case CHANNEL:
                rows = database.update(ChannelContract.ChannelEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }
}

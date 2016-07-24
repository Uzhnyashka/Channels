package com.bobyk.channels;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bobyk on 23/07/16.
 */
public class ChannelDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Channels.db";
    private static ChannelDBHelper channelDBHelper;

    public ChannelDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ChannelDBHelper getInstance(Context context) {
        if (channelDBHelper == null) {
            channelDBHelper = new ChannelDBHelper(context);
        }
        return channelDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        addChannelTable(sqLiteDatabase);
        addCategoryTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChannelContract.CategoryEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChannelContract.ChannelEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private void addChannelTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ChannelContract.ChannelEntry.TABLE_NAME + " (" +
                        ChannelContract.ChannelEntry._ID + " INTEGER PRIMARY KEY, " +
                        ChannelContract.ChannelEntry.COLUMN_ID_NAME + " TEXT, " +
                        ChannelContract.ChannelEntry.COLUMN_NAME + " TEXT, " +
                        ChannelContract.ChannelEntry.COLUMN_TV_URL + " TEXT, " +
                        ChannelContract.ChannelEntry.COLUMN_CATEGORY + " TEXT);"
        );
    }

    private void addCategoryTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ChannelContract.CategoryEntry.TABLE_NAME + " (" +
                        ChannelContract.CategoryEntry._ID + " INTEGER PRIMARY KEY, " +
                        ChannelContract.CategoryEntry.COLUMN_CATEGORY + " TEXT);"
        );
    }
}

package com.bobyk.channels;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by bobyk on 23/07/16.
 */
public class ChannelContract {

    public static final String CONTENT_AUTHORITY = "com.bobyk.channels";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CHANNEL = "channel";
    public static final String PATH_CATEGORY = "category";

    public static final class ChannelEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHANNEL).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" +
                CONTENT_URI  + "/" + PATH_CHANNEL;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" +
                CONTENT_URI + "/" + PATH_CHANNEL;


        public static final String TABLE_NAME = "channelTable";
        public static final String COLUMN_ID_NAME = "id";
        public static final String COLUMN_NAME = "channelName";
        public static final String COLUMN_TV_URL = "tvURL";
        public static final String COLUMN_CATEGORY = "category";

        public static Uri buildChannelUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class CategoryEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" +
                CONTENT_URI  + "/" + PATH_CATEGORY;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" +
                CONTENT_URI + "/" + PATH_CATEGORY;

        public static final String TABLE_NAME = "categoryTable";
        public static final String COLUMN_CATEGORY = "category";

        public static Uri buildCategoryUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}

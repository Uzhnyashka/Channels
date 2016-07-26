package com.bobyk.channels.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.R;

/**
 * Created by bobyk on 26/07/16.
 */
public class FavoriteAdapter extends CursorAdapter {

    LayoutInflater layoutInflater;


    public FavoriteAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.favorite_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvFavoriteName = (TextView) view.findViewById(R.id.favorite_id_name);;
        viewHolder.tvFavoriteURL =  (TextView) view.findViewById(R.id.favorite_url);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String favoriteName = cursor.getString(cursor.getColumnIndex(ChannelContract.FavoriteEntry.COLUMN_FAVORITE_NAME));
        String favoriteURL = cursor.getString(cursor.getColumnIndex(ChannelContract.FavoriteEntry.COLUMN_FAVORITE_URL));
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder != null){
            viewHolder.tvFavoriteName.setText(favoriteName);
            viewHolder.tvFavoriteURL.setText(favoriteURL);
            viewHolder.favoriteID = id;
        }
    }

    public static class ViewHolder{
        public TextView tvFavoriteName;
        public TextView tvFavoriteURL;
        public long favoriteID;
    }
}

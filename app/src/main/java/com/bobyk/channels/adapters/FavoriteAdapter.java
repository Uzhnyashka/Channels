package com.bobyk.channels.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bobyk.channels.ChannelContract;
import com.bobyk.channels.R;

import org.w3c.dom.Text;

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
        TextView tvFavoriteIdName = (TextView) view.findViewById(R.id.favorite_id_name);
        viewHolder.tvFavoriteIdName = tvFavoriteIdName;
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String favoriteIdName = cursor.getString(cursor.getColumnIndex(ChannelContract.FavoriteEntry.COLUMN_ID_FAVORITE));
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder != null){
            viewHolder.tvFavoriteIdName.setText(favoriteIdName);
            viewHolder.favoriteID = id;
        }
    }

    public static class ViewHolder{
        public TextView tvFavoriteIdName;
        public long favoriteID;
    }
}

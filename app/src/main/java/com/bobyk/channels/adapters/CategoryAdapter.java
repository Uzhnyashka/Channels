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
 * Created by bobyk on 24/07/16.
 */
public class CategoryAdapter extends CursorAdapter{

    private LayoutInflater layoutInflater;

    public CategoryAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.category_item, viewGroup, false);
        ViewHolder holder = new ViewHolder();
        TextView textView = (TextView) view.findViewById(R.id.category_name);
        holder.textView = textView;
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String category = cursor.getString(cursor.getColumnIndex(ChannelContract.CategoryEntry.COLUMN_CATEGORY));
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder != null){
            holder.textView.setText(category);
            holder.categoryID = id;
        }
    }

    public static class ViewHolder{
        public TextView textView;
        public long categoryID;
    }
}

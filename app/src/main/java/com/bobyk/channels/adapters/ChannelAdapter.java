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
public class ChannelAdapter extends CursorAdapter{
    LayoutInflater layoutInflater;
    Context context;
    Cursor cursor;

    public ChannelAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
        cursor = c;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, final Cursor cursor, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.channel_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        TextView tvChannelName = (TextView) view.findViewById(R.id.channel_name);
        TextView tvTvURL = (TextView) view.findViewById(R.id.channel_tvURL);
        viewHolder.tvChannelName = tvChannelName;
        viewHolder.tvTvURL = tvTvURL;
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String channelName = cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_NAME));
        String channelTvURL = cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_TV_URL));
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder != null){
            viewHolder.tvChannelName.setText(channelName);
            viewHolder.tvTvURL.setText(channelTvURL);
            viewHolder.channelID = id;
        }
    }

    public static class ViewHolder{
        public TextView tvChannelName;
        public TextView tvTvURL;
        public long channelID;
    }
}

package com.bobyk.channels.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bobyk.channels.ChannelContract;
import com.bobyk.channels.R;
import com.bobyk.channels.models.ChannelModel;
import com.bobyk.channels.models.ProgramModel;

import org.w3c.dom.Text;

/**
 * Created by bobyk on 24/07/16.
 */
public class ChannelAdapter extends CursorAdapter{
    LayoutInflater layoutInflater;
    Context context;

    public ChannelAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, final Cursor cursor, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.channel_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        TextView tvChannelName = (TextView) view.findViewById(R.id.channel_name);
        TextView tvTvURL = (TextView) view.findViewById(R.id.channel_tvURL);
        Button btnFavorite = (Button) view.findViewById(R.id.btnFavorite);
        viewHolder.tvChannelName = tvChannelName;
        viewHolder.tvTvURL = tvTvURL;
        viewHolder.btnFavorite = btnFavorite;
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
            viewHolder.btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChannelModel channelModel = new ChannelModel();
                    channelModel.setId(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_ID_NAME)));
                    saveFavoritesToDb(channelModel);
                }
            });
            viewHolder.channelID = id;
        }
    }

    public void saveFavoritesToDb(ChannelModel channel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChannelContract.FavoriteEntry.COLUMN_ID_FAVORITE, channel.getId());
        Toast.makeText(context, channel.getId(), Toast.LENGTH_SHORT).show();
        context.getContentResolver().insert(ChannelContract.FavoriteEntry.CONTENT_URI, contentValues);
    }


    public static class ViewHolder{
        public TextView tvChannelName;
        public TextView tvTvURL;
        public Button btnFavorite;
        public long channelID;
    }
}

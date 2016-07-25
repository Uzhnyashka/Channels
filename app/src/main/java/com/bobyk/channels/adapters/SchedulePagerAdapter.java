package com.bobyk.channels.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobyk.channels.ChannelContract;
import com.bobyk.channels.R;

/**
 * Created by bobyk on 25/07/16.
 */
public class SchedulePagerAdapter extends PagerAdapter {

    private Cursor cursor;
    private LayoutInflater layoutInflater;

    public SchedulePagerAdapter(Context context, Cursor cursor){
        this.cursor = cursor;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (cursor == null)
            return 0;
        else return cursor.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void swapCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % cursor.getCount();

        cursor.moveToPosition(position);

        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.pager_item, null);

        TextView tvChannel = (TextView) layout.findViewById(R.id.channel_tv);
        tvChannel.setText(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_NAME)));

        ((ViewPager)container).addView(layout);
        return layout;
    }
}

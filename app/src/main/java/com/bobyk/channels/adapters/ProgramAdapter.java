package com.bobyk.channels.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;

import com.bobyk.channels.MainActivity;
import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.fragments.PageFragment;
import com.bobyk.channels.fragments.ScheduleFragment;

/**
 * Created by bobyk on 25/07/16.
 */
public class ProgramAdapter extends FragmentPagerAdapter {

    private Cursor cursor;

    public ProgramAdapter(Context context, FragmentManager fm, Cursor cursor) {
        super(fm);
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        if (cursor == null)
            return 0;
        else return cursor.getCount();
    }

    public void swapCursor(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        cursor.moveToPosition(position);
        return ScheduleFragment.newInstance(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_ID_NAME)));
    }


    @Override
    public CharSequence getPageTitle(int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_NAME));
    }
}

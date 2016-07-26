package com.bobyk.channels.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;

import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.fragments.ScheduleFragment;

/**
 * Created by bobyk on 25/07/16.
 */
public class ProgramAdapter extends FragmentPagerAdapter {

    private Cursor cursor;
    private LayoutInflater layoutInflater;

    public ProgramAdapter(Context context, FragmentManager fm, Cursor cursor) {
        super(fm);
        this.cursor = cursor;
    }

 /*   public ProgramAdapter(Context context, Cursor cursor){
        super(context, cursor);
        this.cursor = cursor;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    @Override
    public int getCount() {
        if (cursor == null)
            return 0;
        else return cursor.getCount();
    }

    /*@Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }*/

    public void swapCursor(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

  /*  @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        cursor.moveToPosition(position);
        (container).removeView((View) object);
    }*/

    @Override
    public Fragment getItem(int position) {
        cursor.moveToPosition(position);
        Log.d("bla", "Bla " + position);
        Log.d("bla", "Id_name: " + cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_ID_NAME)));
        return ScheduleFragment.newInstance(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_ID_NAME)));
       // return PageFragment.newInstance(position);
    }

  /*  @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % cursor.getCount();

        cursor.moveToPosition(position);

        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.pager_item, null);

        TextView tvChannel = (TextView) layout.findViewById(R.id.channel_tv);
        tvChannel.setText(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_NAME)));


        (container).addView(layout);
        return layout;
    }*/

    @Override
    public CharSequence getPageTitle(int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_NAME));
    }
}

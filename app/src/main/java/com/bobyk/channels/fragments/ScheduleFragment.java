package com.bobyk.channels.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobyk.channels.ChannelAdapter;
import com.bobyk.channels.ChannelContract;
import com.bobyk.channels.ChannelDBHelper;
import com.bobyk.channels.MainActivity;
import com.bobyk.channels.R;
import com.bobyk.channels.adapters.SchedulePagerAdapter;

/**
 * Created by bobyk on 24/07/16.
 */
public class ScheduleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ViewPager channelPager;
    private SchedulePagerAdapter schedulePagerAdapter;
    private ChannelDBHelper channelDbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channel_schedule, null);
        channelPager = (ViewPager) view.findViewById(R.id.viewPager);
        channelPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("bla","Page " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channelDbHelper = ChannelDBHelper.getInstance(getActivity());
        schedulePagerAdapter = new SchedulePagerAdapter(getActivity(), getFragmentManager(), null);
        channelPager.setAdapter(schedulePagerAdapter);
    }

    public static ScheduleFragment newInstance() {
        Bundle args = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ChannelContract.ChannelEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        schedulePagerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        schedulePagerAdapter.swapCursor(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (channelDbHelper != null){
            channelDbHelper.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (channelDbHelper == null){
            channelDbHelper = ChannelDBHelper.getInstance(getActivity());
        }
        getLoaderManager().initLoader(-1, null, this);
    }
}

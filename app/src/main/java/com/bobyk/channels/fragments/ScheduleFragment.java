package com.bobyk.channels.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.dbUtils.ChannelDBHelper;
import com.bobyk.channels.MainActivity;
import com.bobyk.channels.R;
import com.bobyk.channels.adapters.ScheduleAdapter;

import java.util.Random;

/**
 * Created by bobyk on 25/07/16.
 */
public class ScheduleFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static String channel;
    private ChannelDBHelper channelDbHelper;
    private ScheduleAdapter scheduleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int backColor;

    public static ScheduleFragment newInstance(String chnl) {

        Bundle args = new Bundle();
        channel = chnl;
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channelDbHelper = ChannelDBHelper.getInstance(getActivity());
        scheduleAdapter = new ScheduleAdapter(getActivity(), null, 0);
        setListAdapter(scheduleAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeScheduleContainer);
        swipeRefreshLayout.setBackgroundColor(backColor);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case MainActivity.ID_PROGRAMS:
                if (channel.equals("")) return new CursorLoader(getActivity(), ChannelContract.ProgramEntry.CONTENT_URI, null, null, null, null);
                else return new CursorLoader(getActivity(), ChannelContract.ProgramEntry.CONTENT_URI, null, ChannelContract.ProgramEntry.COLUMN_SHOW_ID + " = ?", new String[]{channel}, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case MainActivity.ID_PROGRAMS:
                scheduleAdapter.swapCursor(data);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()){
            case MainActivity.ID_PROGRAMS:
                scheduleAdapter.swapCursor(null);
                break;
            default:
                break;
        }
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
        getLoaderManager().initLoader(MainActivity.ID_PROGRAMS, null, this);
    }
}

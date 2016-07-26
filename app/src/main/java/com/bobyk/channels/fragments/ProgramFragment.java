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

import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.dbUtils.ChannelDBHelper;
import com.bobyk.channels.R;
import com.bobyk.channels.adapters.ProgramAdapter;

/**
 * Created by bobyk on 24/07/16.
 */
public class ProgramFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ViewPager channelPager;
    private ProgramAdapter programAdapter;
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
        programAdapter = new ProgramAdapter(getActivity(), getFragmentManager(), null);
        channelPager.setAdapter(programAdapter);
    }

    public static ProgramFragment newInstance() {
        Bundle args = new Bundle();
        ProgramFragment fragment = new ProgramFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ChannelContract.ChannelEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        programAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        programAdapter.swapCursor(null);
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

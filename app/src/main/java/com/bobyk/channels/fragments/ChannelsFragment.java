package com.bobyk.channels.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobyk.channels.adapters.ChannelAdapter;
import com.bobyk.channels.ChannelContract;
import com.bobyk.channels.ChannelDBHelper;
import com.bobyk.channels.MainActivity;
import com.bobyk.channels.R;

/**
 * Created by bobyk on 24/07/16.
 */
public class ChannelsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static String category = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChannelAdapter channelAdapter;
    private ChannelDBHelper channelDbHelper;

    public static ChannelsFragment newInstance(String ctgr) {
        Bundle args = new Bundle();
        category = ctgr;
        ChannelsFragment fragment = new ChannelsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeChannelContainer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channelDbHelper = ChannelDBHelper.getInstance(getActivity());
        channelAdapter = new ChannelAdapter(getActivity(), null, 0);
        setListAdapter(channelAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case MainActivity.ID_CHANNELS:
                if (category.equals("")) return new CursorLoader(getActivity(), ChannelContract.ChannelEntry.CONTENT_URI, null, null, null, null);
                else return new CursorLoader(getActivity(), ChannelContract.ChannelEntry.CONTENT_URI, null, ChannelContract.ChannelEntry.COLUMN_CATEGORY + " = ?", new String[]{category}, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case MainActivity.ID_CHANNELS:
                channelAdapter.swapCursor(data);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()){
            case MainActivity.ID_CHANNELS:
                channelAdapter.swapCursor(null);
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
        getLoaderManager().initLoader(MainActivity.ID_CHANNELS, null, this);
    }
}

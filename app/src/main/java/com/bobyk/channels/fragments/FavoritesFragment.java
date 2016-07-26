package com.bobyk.channels.fragments;

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
import android.widget.LinearLayout;

import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.dbUtils.ChannelDBHelper;
import com.bobyk.channels.MainActivity;
import com.bobyk.channels.R;
import com.bobyk.channels.adapters.FavoriteAdapter;

/**
 * Created by bobyk on 25/07/16.
 */
public class FavoritesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private LinearLayout linearLayout;
    private ChannelDBHelper channelDBHelper;
    private FavoriteAdapter favoriteAdapter;

    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);
        linearLayout = (LinearLayout) view.findViewById(R.id.favoriteContainer);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channelDBHelper = ChannelDBHelper.getInstance(getActivity());
        favoriteAdapter = new FavoriteAdapter(getActivity(), null, 0);
        setListAdapter(favoriteAdapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ChannelContract.FavoriteEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favoriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoriteAdapter.swapCursor(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (channelDBHelper != null){
            channelDBHelper.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (channelDBHelper == null){
            channelDBHelper = ChannelDBHelper.getInstance(getActivity());
        }
        getLoaderManager().initLoader(MainActivity.ID_FAVORITE, null, this);
    }
}

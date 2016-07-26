package com.bobyk.channels.fragments;

import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bobyk.channels.adapters.ChannelAdapter;
import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.dbUtils.ChannelDBHelper;
import com.bobyk.channels.MainActivity;
import com.bobyk.channels.R;
import com.bobyk.channels.models.ChannelModel;

import java.util.Random;

/**
 * Created by bobyk on 24/07/16.
 */
public class ChannelsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static String category = null;
    private LinearLayout linearLayout;
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
        linearLayout = (LinearLayout) view.findViewById(R.id.swipeChannelContainer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channelDbHelper = ChannelDBHelper.getInstance(getActivity());
        channelAdapter = new ChannelAdapter(getActivity(), null, 0);
        setListAdapter(channelAdapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChannelAdapter.ViewHolder holder = (ChannelAdapter.ViewHolder) view.getTag();
                if (holder != null) {
                    Uri uri = ChannelContract.ChannelEntry.buildChannelUri(holder.channelID);
                    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        long id = cursor.getLong(cursor.getColumnIndex(ChannelContract.ChannelEntry._ID));
                        ChannelModel channelModel = new ChannelModel();
                        channelModel.setId(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_ID_NAME)));
                        channelModel.setName(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_NAME)));
                        channelModel.setTvURL(cursor.getString(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_TV_URL)));
                        boolean ok = cursor.getInt(cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_FAVORITE)) > 0;
                        if (!ok) saveFavoritesToDb(channelModel, id);
                        else deleteFromFavorites(channelModel, id);
                      //  Toast.makeText(getActivity(), holder.channelID + " : " + id, Toast.LENGTH_SHORT).show();
                    }
                  /*  System.out.println("COLORRR");
                    View v = holder.view;
                    int backColor;
                    Random rnd = new Random();
                    if (!ok) backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    else backColor = Color.argb(0,  0, 0, 0);
                    v.setBackgroundColor(backColor);*/
                } else {
                    return true;
                }

                return true;
            }
        });
    }

    public void saveFavoritesToDb(ChannelModel channel, long id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChannelContract.FavoriteEntry.COLUMN_FAVORITE_ID, channel.getId());
        contentValues.put(ChannelContract.FavoriteEntry.COLUMN_FAVORITE_NAME, channel.getName());
        contentValues.put(ChannelContract.FavoriteEntry.COLUMN_FAVORITE_URL, channel.getTvURL());
        getActivity().getContentResolver().insert(ChannelContract.FavoriteEntry.CONTENT_URI, contentValues);

        ContentValues contentUpdateValues = new ContentValues();
        contentUpdateValues.put(ChannelContract.ChannelEntry.COLUMN_FAVORITE, true);
        getActivity().getContentResolver().update(ChannelContract.ChannelEntry.CONTENT_URI, contentUpdateValues,
                ChannelContract.ChannelEntry._ID + " = ?", new String[]{String.valueOf(id)});

        Toast.makeText(getActivity(), "Add " + id, Toast.LENGTH_SHORT).show();
    }

    public void deleteFromFavorites(ChannelModel channel, long id){
        getActivity().getContentResolver().delete(ChannelContract.FavoriteEntry.CONTENT_URI, ChannelContract.FavoriteEntry.COLUMN_FAVORITE_ID + " = ?", new String[]{channel.getId()});

        ContentValues contentUpdateValues = new ContentValues();
        contentUpdateValues.put(ChannelContract.ChannelEntry.COLUMN_FAVORITE, false);
        getActivity().getContentResolver().update(ChannelContract.ChannelEntry.CONTENT_URI, contentUpdateValues,
                ChannelContract.ChannelEntry._ID + " = ?", new String[]{String.valueOf(id)});

        Toast.makeText(getActivity(), "delete " + id, Toast.LENGTH_SHORT).show();
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

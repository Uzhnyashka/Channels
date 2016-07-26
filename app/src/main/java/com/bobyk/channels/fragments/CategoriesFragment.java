package com.bobyk.channels.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bobyk.channels.dbUtils.ChannelContract;
import com.bobyk.channels.dbUtils.ChannelDBHelper;
import com.bobyk.channels.MainActivity;
import com.bobyk.channels.R;
import com.bobyk.channels.adapters.CategoryAdapter;

/**
 * Created by bobyk on 24/07/16.
 */
public class CategoriesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private LinearLayout linearContainer;
    private CategoryAdapter categoryAdapter;
    private ChannelDBHelper channelDbHelper;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channelDbHelper = ChannelDBHelper.getInstance(getActivity());
        categoryAdapter = new CategoryAdapter(getActivity(), null, 0);
        setListAdapter(categoryAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CategoryAdapter.ViewHolder viewHolder = (CategoryAdapter.ViewHolder) view.getTag();
                if (viewHolder != null){
                    String category = viewHolder.textView.getText().toString();
                    Toast.makeText(getActivity(), category, Toast.LENGTH_SHORT).show();
                    loadChannelsFragment(category);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);
        linearContainer = (LinearLayout) view.findViewById(R.id.categoryContainer);
        return view;
    }

    private void loadChannelsFragment(String category){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, ChannelsFragment.newInstance(category));
        ft.addToBackStack(null);
        ft.commit();
    }

    public static CategoriesFragment newInstance() {
        Bundle args = new Bundle();
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case MainActivity.ID_CATEGORIES:
                return new CursorLoader(getActivity(), ChannelContract.CategoryEntry.CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case MainActivity.ID_CATEGORIES:
                categoryAdapter.swapCursor(data);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()){
            case MainActivity.ID_CATEGORIES:
                categoryAdapter.swapCursor(null);
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
        getLoaderManager().initLoader(MainActivity.ID_CATEGORIES, null, this);
    }
}

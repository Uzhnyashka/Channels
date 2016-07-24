package com.bobyk.channels.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobyk.channels.R;

/**
 * Created by bobyk on 22/07/16.
 */
public class ChannelFragment extends Fragment {
    private String title;
    private int page;
    public static final String KEY_TITLE = "KEY_TITLE";
    private TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, container, false);
        tvTitle = (TextView) view.findViewById(R.id.swipeChannelContainer);
        tvTitle.setText(title);
        return view;
    }

    public static ChannelFragment newInstance(int page, String title) {
        ChannelFragment fragmentFirst = new ChannelFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }
}
